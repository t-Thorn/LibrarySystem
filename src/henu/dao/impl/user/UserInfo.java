package henu.dao.impl.user;

import henu.bean.Borrow;
import henu.bean.Reader;
import henu.dao.Pages;
import henu.dao.impl.book.bookSearch;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.List;

public class UserInfo {
    public static Reader getConcreteReader(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        Pages pages = Pages.getpage(request, 2);
        System.out.println("PAGE" + pages.getFlag());
        int index = pages.getFlag() * 8 + id;
        System.out.println("page2" + pages.getFlag());
        Reader[] readers = bookSearch.searchUser(request);//获得当前的书列表
        Reader target = readers[index];//获得目标书籍信息
        return target;
    }

    public static void userDelete(HttpServletRequest request) throws Exception {
        Reader reader = UserInfo.getConcreteReader(request);
        System.out.println("name:" + reader.getName());
        Connection con = DbcpPool.getConnection();
        con.setAutoCommit(false);
        QueryRunner qr = new QueryRunner();
        try {
            String str = "delete  from Borrow where readUsername=?";
            String str1 = "delete  from Reader where username=?";
            qr.update(con, str, reader.getUsername());
            qr.update(con, str1, reader.getUsername());
        } catch (SQLException e) {
            DbUtils.rollback(con);
            System.out.println(e.getMessage());
            throw new Exception();
        } finally {
            DbUtils.commitAndCloseQuietly(con);
        }
    }

    public static void updateUserInfo(Reader userInfo) {
        Connection con = DbcpPool.getConnection();
        String str = "UPDATE Reader SET password=?,name=?,gender=?,age=?," +
                "tel=?,ID=?,birthday=?,money=? WHERE username=?";
        try {
            PreparedStatement pstm = con.prepareStatement(str);
            pstm.setString(1, userInfo.getPassword());
            pstm.setString(2, userInfo.getName());
            pstm.setString(3, userInfo.getGender());
            pstm.setInt(4, userInfo.getAge());
            pstm.setString(5, userInfo.getTel());
            pstm.setString(6, userInfo.getID());
            pstm.setDate(7, Date.valueOf(userInfo.getBirthday()));
            pstm.setFloat(8, userInfo.getMoney());
            pstm.setString(9, userInfo.getUsername());
            int x = pstm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void updateUserPwd(String username, String pwd, int flag) {
        Connection con = DbcpPool.getConnection();
        String str;
        if (flag == 1) {
            str = "UPDATE Operator SET password=? WHERE username=?";
        } else {
            str = "UPDATE Reader SET password=? WHERE username=?";
        }
        try {
            PreparedStatement pstm = con.prepareStatement(str);
            pstm.setString(1, pwd);
            pstm.setString(2, username);
            int x = pstm.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void userCreate(Reader reader) throws SQLException {
        Connection con = DbcpPool.getConnection();
        String str = "insert into Reader  (username,password,name,gender," +
                "age,tel,ID,birthday,money) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstm = con.prepareStatement(str);
        pstm.setString(1, reader.getUsername());
        pstm.setString(2, reader.getPassword());
        pstm.setString(3, reader.getName());
        pstm.setString(4, reader.getGender());
        pstm.setInt(5, reader.getAge());
        pstm.setString(6, reader.getTel());
        pstm.setDate(8, Date.valueOf(reader.getBirthday()));
        pstm.setString(7, reader.getID());
        pstm.setFloat(9, reader.getMoney());
        int x = pstm.executeUpdate();
        con.close();
    }

    public static boolean userIsExist(String username) {
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        try {
            String str1 = "select count(*) from Reader where username='" + username + "'";
            Integer exist = qr.query(con, str1, new ScalarHandler<Integer>());
            System.out.println("存在？：" + exist);
            return exist != 0 ? true : false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            DbUtils.commitAndCloseQuietly(con);
        }
        return true;
    }

    public static Borrow[] getTheAllBorrowOfUser(String username) {
        Connection con = DbcpPool.getConnection();
        QueryRunner qr = new QueryRunner();
        try {
            String str = "select * from Borrow where readusername='" + username + "'";
            List<Borrow> borrows = qr.query(con, str, new BeanListHandler<Borrow>(Borrow.class));
            Borrow[] result = new Borrow[borrows.size()];
            int i = 0;
            for (Borrow s : borrows) {
                result[i++] = s;
            }
            DbUtils.closeQuietly(con);
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String oldPwdConfirmOfReader(String username,String password) {
        String str_Reader = "select * from Reader where username='" + username +
                "'";
        Connection con = henu.util.DbcpPool.getConnection();
        try {
            PreparedStatement pstm = con.prepareStatement(str_Reader);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String x = rs.getString(2);
                if (!x.equals(password)) {
                    return "两次密码不一样";
                }
                else
                    return "";
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return "ok";
    }

    public static String oldPwdConfirmOfOperator(String username,String password) {
        String str_Operator = "select * from Operator where username='" + username +
                "'";
        Connection con = henu.util.DbcpPool.getConnection();
        try {
            PreparedStatement pstm = con.prepareStatement(str_Operator);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String x = rs.getString(2);
                System.out.println("密码："+x);
                if (!x.equals(password)) {
                    System.out.println("两次密码不一样：");
                    return "旧密码错误";
                }
                else
                {
                    System.out.println("旧密码正确：");
                    return "";
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }
        return "ok";
    }
  /*  public static void 初始化(){
        String pageType=(String) request.getSession().getAttribute("pageType");
        if(pageType==null ||!pageType.equals("book")){
            request.getSession().setAttribute("pageType","book");
            request.getSession().setAttribute("page",null);
            request.getSession().setAttribute("method","");
            request.getSession().setAttribute("context","");
        }
        request.setCharacterEncoding("UTF-8");
        Reader reader = (Reader) request.getSession().getAttribute("Reader");
        Operator operator = (Operator) request.getSession().getAttribute("Operator");
        BookInfo[] books = bookSearch.search(request);
        //页面

        Pages page_now=Pages.getpage(request,1);
        int tt = page_now.getFlag() * 8 ;
        if(books.length<=tt &&books.length>0)
        {
            page_now.setFlag(0);

            request.getSession().setAttribute("page",page_now);
        }
        int bound = page_now.getFlag() * 8 + 8;
    }*/
}
