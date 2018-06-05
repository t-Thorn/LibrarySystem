package Servlets;

import henu.bean.BookInfo;
import henu.dao.Pages;
import henu.dao.impl.book.Book_Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Book_Delete", value = "/Servlets/Book_Delete")
public class Book_Delete extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

         try{
             Book_Info.bookDelete(request);
         }catch (Exception e)
         {
             System.out.println("e"+e.getMessage());
             request.getRequestDispatcher("/Functions/bookinfo.jsp?error=yes").forward(request,
                     response);
         }
        request.getRequestDispatcher("/Functions/bookinfo.jsp").forward(request,
                response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
