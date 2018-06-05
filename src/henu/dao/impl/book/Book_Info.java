package henu.dao.impl.book;

import henu.bean.BookInfo;
import henu.bean.Operator;
import henu.bean.Reader;
import henu.dao.Pages;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class Book_Info {
    public static BookInfo getBookInfobyISBN(int ISBN) {
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        //String str="SELECT * FROM borrow where readUsername='"+reader.getUsername()+"' and " +
        //      "valid=1";
        String str = "SELECT * FROM bookinfo where ISBN='" + ISBN + "'";
        try {
            BookInfo result = qr.query(con, str, new BeanHandler<BookInfo>(BookInfo.class));
            DbUtils.closeQuietly(con);
            return result;
        } catch (SQLException e) {
            ;
        }
        return null;
    }

    public static String getBookNamebyISBN(int ISBN) throws SQLException {
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        //String str="SELECT * FROM borrow where readUsername='"+reader.getUsername()+"' and " +
        //      "valid=1";
        String str = "SELECT * FROM bookinfo where ISBN='" + ISBN + "'";
        BookInfo bookInfos = qr.query(con, str, new BeanHandler<BookInfo>(BookInfo.class));
        DbUtils.closeQuietly(con);
        return bookInfos.getBookName();
    }

    public static void updateBookInfo(BookInfo book) {
        Connection con = DbcpPool.getConnection();
        String str = "UPDATE Bookinfo SET bookName=?,typeName=?,writer=?,translator=?," +
                "publisher=?,publishDate=?,price=?,remain=? WHERE ISBN=?";
        try {
            PreparedStatement pstm = con.prepareStatement(str);
            pstm.setString(1, book.getBookName());
            pstm.setString(2, book.getTypeName());
            pstm.setString(3, book.getWriter());
            pstm.setString(4, book.getTranslator());
            pstm.setString(5, book.getPublisher());
            pstm.setDate(6, Date.valueOf(book.getPublishDate()));
            pstm.setFloat(7, book.getPrice());
            pstm.setFloat(8, book.getRemain());
            pstm.setInt(9, book.getISBN());
            int x = pstm.executeUpdate();
            System.out.println(x);
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static BookInfo getConcreteBook(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        Pages pages = Pages.getpage(request, 1);
        int index = pages.getFlag() * 8 + id;
        BookInfo[] books = bookSearch.search(request);//获得当前的书列表
        BookInfo target = books[index];//获得目标书籍信息
        return target;
    }

    public static void bookDelete(HttpServletRequest request) throws Exception {
        BookInfo book = Book_Info.getConcreteBook(request);
        Connection con = DbcpPool.getConnection();
        con.setAutoCommit(false);
        QueryRunner qr = new QueryRunner();
        try {
            String str = "delete  from Borrow where ISBN=?";
            String str1 = "delete  from Subscribe where ISBN=?";
            String str2 = "delete  from Bookinfo where ISBN=?";
            qr.update(con, str, book.getISBN());
            qr.update(con, str1, book.getISBN());
            qr.update(con, str2, book.getISBN());
        } catch (SQLException e) {
            DbUtils.rollback(con);
            System.out.println(e.getMessage());
            throw new Exception();
        } finally {
            DbUtils.commitAndCloseQuietly(con);
        }
    }

    public static void bookSubscribe(HttpServletRequest request) throws Exception {
        int ISBN = Integer.parseInt(request.getParameter("ISBN"));
        int number = Integer.parseInt(request.getParameter("number"));
        float discount = Float.parseFloat(request.getParameter("discount"));
        Operator operator = (Operator) request.getSession().getAttribute("Operator");
         System.out.println(discount);
        Connection con = DbcpPool.getConnection();
        con.setAutoCommit(false);
        QueryRunner qr = new QueryRunner();

        try {
            String str = "insert  into Subscribe (ISBN,orderTime,number,operator,discount)values" +
                    "(?,?,?,?,?)";
            String str1 = "select remain from Bookinfo where ISBN=?";
            String str2 = "UPDATE Bookinfo SET remain=? WHERE ISBN=?";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            qr.update(con, str, ISBN, sdf.format(new java.util.Date()), number, operator.getUsername(), discount);
            Integer remain = qr.query(con, str1, new ScalarHandler<Integer>(), ISBN);
            remain += number;
            qr.update(con, str2, remain, ISBN);
        } catch (SQLException e) {
            DbUtils.rollback(con);
            System.out.println(e.getMessage());
            throw new Exception();
        } finally {
            DbUtils.commitAndCloseQuietly(con);
        }
    }

    public static int getISBN() throws Exception {
        //返回一个ISBN=现有最大ISBN+1
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        try {
            String str1 = "select MAX(isbn) from Bookinfo";
            Integer ISBN = qr.query(con, str1, new ScalarHandler<Integer>());
            return ISBN + 1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new Exception();
        } finally {
            DbUtils.commitAndCloseQuietly(con);
        }
    }

    public static void bookCreate(BookInfo book) {
        Connection con = DbcpPool.getConnection();
        String str = "insert into Bookinfo  (bookName,typeName,writer,translator," +
                "publisher,publishDate,price,remain,ISBN) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = con.prepareStatement(str);
            pstm.setString(1, book.getBookName());
            pstm.setString(2, book.getTypeName());
            pstm.setString(3, book.getWriter());
            pstm.setString(4, book.getTranslator());
            pstm.setString(5, book.getPublisher());
            pstm.setDate(6, Date.valueOf(book.getPublishDate()));
            pstm.setFloat(7, book.getPrice());
            pstm.setFloat(8, book.getRemain());
            pstm.setInt(9, book.getISBN());
            int x = pstm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
