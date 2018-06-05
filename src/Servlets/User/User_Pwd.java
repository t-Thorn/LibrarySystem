package Servlets.User;

import henu.bean.Operator;
import henu.bean.Reader;
import henu.dao.impl.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "User_Pwd", value = "/Servlets/User/User_Pwd")
public class User_Pwd extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oldpwd = request.getParameter("oldpwd");
        String newpwd = request.getParameter("newpwd");
        String confirmpwdpwd = request.getParameter("confirmpwd");
        String message = "";
        request.setCharacterEncoding("UTF-8");
        if (oldpwd.equals("") && newpwd.equals("") && confirmpwdpwd.equals(""))
            message = "请输入完整";
        else if (!newpwd.equals(confirmpwdpwd)) {
            message = "两次新密码不同";
        } else {
            Operator operator = (Operator) request.getSession().getAttribute("Operator");
            if (operator != null) {
                message=UserInfo.oldPwdConfirmOfOperator(operator.getUsername(), oldpwd);
                System.out.println("msg+"+message);
                if (message.equals(""))
                {
                    System.out.println("密码正确");
                    UserInfo.updateUserPwd(operator.getUsername(), newpwd, 1);
                }
                else
                {
                    System.out.println("密码错误");
                    message="密码错误";
                }
            }//更新操作员
            else {
                Reader reader = (Reader) request.getSession().getAttribute("Reader");
                message=UserInfo.oldPwdConfirmOfReader(reader.getUsername(), oldpwd);
                if (message.equals(""))
                {
                    System.out.println("密码正确");
                    UserInfo.updateUserPwd(reader.getUsername(), newpwd, 2);
                }
                else
                {
                    System.out.println("密码错误");
                    message="密码错误";
                }
            }
        }
        if (!message.equals("")) {
            request.getRequestDispatcher("/Functions/pwdModify.jsp?message=" + message).forward
                    (request,
                            response);
        } else
            request.getRequestDispatcher("/Functions/bookinfo.jsp").forward
                    (request,
                            response);
    }
}
