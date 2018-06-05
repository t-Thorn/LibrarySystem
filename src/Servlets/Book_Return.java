package Servlets;

import henu.dao.impl.book.book_Borrow;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@WebServlet(name = "Book_Return", value = "/Servlets/Book_Return")
public class Book_Return extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));
        henu.bean.Reader reader=(henu.bean.Reader) request.getSession().getAttribute("Reader");
       try {
           book_Borrow.updateBorrowByReaderofback(reader,id);
       }catch (Exception e)
       {
           request.getRequestDispatcher("/Functions/bookReturn.jsp?error=yes").forward(request,
                   response);
       }
        request.getRequestDispatcher("/Functions/bookReturn.jsp").forward(request,
                response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
