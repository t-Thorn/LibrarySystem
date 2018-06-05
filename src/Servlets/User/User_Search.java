package Servlets.User;

import henu.dao.impl.book.Book_Info;
import henu.dao.impl.book.bookSearch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "User_Search", value = "/Servlets/User/User_Search")
public class User_Search extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String x=request.getParameter("context");
        String y=request.getParameter("method");
        request.getSession().setAttribute("context",x);
        request.getSession().setAttribute("method",y);
        request.getSession().setAttribute("page",null);
        request.getRequestDispatcher("/Functions/userview.jsp").forward
                (request, response);

    }
}

