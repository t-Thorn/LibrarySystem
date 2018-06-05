package Servlets;

import henu.dao.Pages;
import henu.dao.impl.book.Book_Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Book_Record", value = "/Servlets/Book_Record")
public class Book_Record extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        Pages page_now = (Pages) request.getSession().getAttribute("page");
        System.out.println("test1:"+page_now.getFlag());
        int index_result=page_now.getFlag()*8+id;
        System.out.println("test2:"+index_result);
        request.getSession().setAttribute("index_result",index_result);
        request.getRequestDispatcher("/Functions/borrowRecord.jsp").forward
                (request, response);
    }
}
