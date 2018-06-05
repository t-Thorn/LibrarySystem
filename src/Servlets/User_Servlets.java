package Servlets;

import henu.dao.impl.user.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "User_Servlets", value = "/Servlets/User_Servlets")
public class User_Servlets extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (UserDaoImpl.login(username, password, request))
            request.getRequestDispatcher("/show.jsp").forward(request,
                    response);
        else
            request.getRequestDispatcher("/index.jsp").forward(request,
                    response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
