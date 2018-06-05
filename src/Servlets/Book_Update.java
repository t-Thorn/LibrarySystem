package Servlets;

import henu.dao.Pages;
import henu.dao.impl.book.book_Borrow;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Book_Update", value = "/Servlets/Book_Update")
public class Book_Update extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));
        Pages pages=Pages.getpage(request,1);
        int index=pages.getFlag()*8+id;
        request.getRequestDispatcher("/Functions/bookModify.jsp?index="+index).forward(request,
                response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}