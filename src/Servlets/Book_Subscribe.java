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
@WebServlet(name = "Book_Subscribe", value = "/Servlets/Book_Subscribe")
public class Book_Subscribe extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookInfo bookInfo=Book_Info.getConcreteBook(request);
        request.getRequestDispatcher("/Functions/bookSubscribe.jsp?ISBN="+bookInfo.getISBN()).forward
                (request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}