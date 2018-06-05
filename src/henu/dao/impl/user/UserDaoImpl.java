package henu.dao.impl.user;

import henu.bean.BookInfo;
import henu.bean.Borrow;
import henu.bean.Reader;
import henu.dao.UserLogin;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//操作实现类
public class UserDaoImpl implements UserLogin {
    public static boolean login(String username, String password, HttpServletRequest request) {
        String str_Reader = "select * from Reader where username='" + username +
                "'";
        boolean flag;
        Connection con = henu.util.DbcpPool.getConnection();
        try {
            PreparedStatement pstm = con.prepareStatement(str_Reader);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                String x = rs.getString(2);
                if (!x.equals(password)) {
                    request.setAttribute("error", "密码错误");
                    flag = false;
                } else {
                    henu.bean.Reader reader = new henu.bean.Reader();
                    reader.setUsername(rs.getString(1));
                    reader.setPassword(rs.getString(2));
                    reader.setName(rs.getString(3));
                    reader.setGender(rs.getString(4));
                    reader.setAge(rs.getInt(5));
                    reader.setTel(rs.getString(6));
                    reader.setID(rs.getString(7));
                    reader.setBirthday(String.valueOf(rs.getDate(8)));
                    request.getSession().setAttribute("Reader", reader);//存入会话中
                    flag = true;
                }

            } else {

                String str_Operator = "select * from Operator where username='" + username +
                        "'";
                PreparedStatement pstm1 = con.prepareStatement(str_Operator);
                ResultSet rs1 = pstm1.executeQuery();
                if (rs1.next()) {
                    String x = rs1.getString(2);
                    if (!x.equals(password)) {
                        request.setAttribute("error", "密码错误");
                        flag = false;
                    } else {
                        henu.bean.Operator operator = new henu.bean.Operator();
                        operator.setUsername(rs1.getString(1));
                        operator.setPassword(rs1.getString(2));
                        operator.setName(rs1.getString(3));
                        operator.setGender(rs1.getString(4));
                        operator.setAge(rs1.getInt(5));
                        operator.setTel(rs1.getString(6));
                        request.getSession().setAttribute("Operator", operator);//存入会话中
                        rs1.close();
                        pstm1.close();
                        flag = true;
                    }
                } else {

                    request.setAttribute("error", "用户不存在");
                    flag = false;
                }
                rs1.close();
                pstm1.close();
            }
            rs.close();
            pstm.close();
            con.close();
            return flag;
        } catch (SQLException e) {
            request.setAttribute("error", "内部错误");
            return false;
        }
    }


    public static void updateReaderMoney(Reader reader,Float margin) {
        Connection con= DbcpPool.getConnection();
        QueryRunner qr=new QueryRunner();
        try {
            String str = "select money from Reader where username=?";
            String str1 = "UPDATE Reader SET money=? WHERE username=?";
            System.out.println("first");
            Double money=qr.query(con,str,new ScalarHandler<Double>(),reader.getUsername());
            money-= margin;
            System.out.println("second");
            qr.update(con, str1,money,reader.getUsername());
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        DbUtils.closeQuietly(con);
    }
}
