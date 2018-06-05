package Servlets.User;

import henu.dao.impl.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "User_Delete", value = "/Servlets/User/User_Delete")
public class User_Delete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{
            UserInfo.userDelete(request);
        }catch (Exception e)
        {
            System.out.println("e"+e.getMessage());
            request.getRequestDispatcher("/Functions/userview.jsp?error=yes").forward(request,
                    response);
        }
        request.getRequestDispatcher("/Functions/userview.jsp").forward(request,
                response);
    }
}

