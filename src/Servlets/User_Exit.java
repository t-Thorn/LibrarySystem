package Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "User_Exit", value = "/Servlets/User_Exit")
public class User_Exit extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();//消除回话
        session.invalidate();
       // session.removeAttribute("Operator");
       // session.removeAttribute("Reader");
       // session.removeAttribute("page");
       // session.removeAttribute("method");

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
