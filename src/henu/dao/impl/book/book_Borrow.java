package henu.dao.impl.book;

import henu.bean.BookInfo;
import henu.bean.Borrow;
import henu.bean.Reader;
import henu.bean.Subscribe;
import henu.dao.impl.book.pages.book;
import henu.dao.impl.user.UserDaoImpl;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class book_Borrow {

    //判断是否overtime并更新余额信息
    public static float[] updateBorrowByReader(Reader reader) {
        //更新指定读者的借阅信息
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        try {
            Borrow[] borrows = getTheBorrowedBook(reader);
            float[] margin = new float[borrows.length];
            for (int index = 0; index < borrows.length; index++) {
                Calendar now = Calendar.getInstance();
                Calendar pass = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                //获取过期天数
                int days = getBetweenDay(sdf.parse(borrows[index].getBorrowDate()), sdf.parse(sdf.format(new Date())));
                if (days >= 60)//需要更新
                {
                    days -= 60;
                    margin[index] = (float) (days * 0.1);
                    String str = "UPDATE Borrow SET overTime=? WHERE ISBN=? and readUsername=?";
                    qr.update(con, str, true, borrows[index].getISBN(), reader.getUsername());
                } else
                    margin[index] = 0;
            }
            DbUtils.closeQuietly(con);
            return margin;
        } catch (ParseException e) {
            e.getMessage();
        } catch (SQLException e) {
            e.getMessage();
        }

        return null;
    }

    //还书
    public static void updateBorrowByReaderofback(Reader reader, int index) throws Exception {
        //还书
        Connection con = DbcpPool.getConnection();
        con.setAutoCommit(false);
        try {
            QueryRunner qr = new QueryRunner();
            Borrow[] borrows = getTheBorrowedBook(reader);
            String str = "UPDATE Borrow SET valid=? where BID=?";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            qr.update(con, str, 0, borrows[index].getBID());
            String str1 = "UPDATE Borrow SET backDate=? where BID=?";
            qr.update(con, str1, sdf.format(new Date()), borrows[index].getBID());
            String str2 = "select remain from Bookinfo where ISBN=?";
            String str3 = "UPDATE Bookinfo SET remain=? WHERE ISBN=?";
            Integer remain = qr.query(con, str2, new ScalarHandler<Integer>(), borrows[index].getISBN());
            remain++;
            qr.update(con, str3, remain, borrows[index].getISBN());


            //余额更新
            Calendar now = Calendar.getInstance();
            Calendar pass = Calendar.getInstance();
            //获取过期天数

            int days = getBetweenDay(sdf.parse(borrows[index].getBorrowDate()), sdf.parse(sdf.format(new Date())));
            if (days >= 60)//需要更新
            {
                days -= 60;
                Double x = days * 0.1;
                Float margin = Float.parseFloat(x.toString());
                UserDaoImpl.updateReaderMoney(reader, margin);
            }
        } catch (SQLException e) {
            DbUtils.rollback(con);
            System.out.println(e.getMessage());
            throw new Exception();
        } finally {
            DbUtils.commitAndCloseQuietly(con);
        }
    }

    public static Borrow[] getTheBorrowedBook(Reader reader) throws SQLException {
        //查询借阅的书籍，返回书籍信息
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        String str = "SELECT * FROM borrow where readUsername='" + reader.getUsername() + "' and " + "valid=1";
        //String str="SELECT * FROM borrow";
        List<Borrow> borrows = qr.query(con, str, new BeanListHandler<Borrow>(Borrow.class));
        Borrow[] result = new Borrow[borrows.size()];
        int i = 0;
        for (Borrow s : borrows) {
            result[i++] = s;
        }
        DbUtils.closeQuietly(con);
        return result;
    }

    public static void borrow(HttpServletRequest request, int index) throws Exception {
        //借书
        Connection con = DbcpPool.getConnection();
        con.setAutoCommit(false);
        try {
            BookInfo[] books = bookSearch.search(request);//获得当前的书列表
            BookInfo target = books[index];//获得目标书籍信息
            Reader reader = (Reader) request.getSession().getAttribute("Reader");
            //流程：图书表remain-1
            QueryRunner qr = new QueryRunner();
            String str = "select remain from Bookinfo where ISBN=?";
            String str1 = "UPDATE Bookinfo SET remain=? WHERE ISBN=?";
            int remain = qr.query(con, str, new ScalarHandler<Integer>(), target.getISBN());
            remain--;
            qr.update(con, str1, remain, target.getISBN());

            //流程：插入借阅表
            String str2 = "insert into borrow (ISBN,readUsername,valid,overTime) values(?,?,?,?)";
            qr.update(con, str2, target.getISBN(), reader.getUsername(), 1, 0);
            con.commit();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            DbUtils.rollback(con);
            throw new Exception();
        } finally {
            DbUtils.closeQuietly(con);
        }
    }

    protected static int getBetweenDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            // System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2 - day1;
        }
    }

    public static Borrow[] getTheRecord(int ISBN) {
        Connection con = DbcpPool.getConnection();
        String str = "SELECT * FROM borrow where ISBN=" + String.valueOf(ISBN);
        try {
            PreparedStatement pstm = con.prepareStatement(str,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs=pstm.executeQuery();
            int i = 0;
            rs.last(); //移到最后一行
            int rowCount = rs.getRow(); //得到当前行号，也就是记录数
            rs.beforeFirst(); //如果还要用结果集，就把指针再移到初始化的位置
            Borrow []borrows=new Borrow[rowCount];
          while (rs.next())
          {
              System.out.println("index:"+i);
              borrows[i]=new Borrow();
              borrows[i].setISBN(rs.getInt(2));
              borrows[i].setReaderUsername(rs.getString(3));
              borrows[i].setBackDate(rs.getString(6));
              borrows[i].setBorrowDate(rs.getString(5).toString());
              borrows[i].setOverTime(rs.getBoolean(7));
              i++;
          }
            rs.close();
            pstm.close();
            con.close();
            return borrows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static Subscribe[] getTheRecordOfSub(int ISBN) {
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        String str = "SELECT * FROM Subscribe where ISBN='" + ISBN + "' ";
        try {
            List<Subscribe> subscribes = qr.query(con, str, new BeanListHandler<Subscribe>(Subscribe.class));
            Subscribe[] result = new Subscribe[subscribes.size()];
            int i = 0;
            for (Subscribe s : subscribes) {
                result[i++] = s;
            }
            DbUtils.closeQuietly(con);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
