package Servlets;

import henu.bean.BookInfo;
import henu.dao.impl.book.Book_Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Book_Create", value = "/Servlets/Book_Create")
public class Book_Create extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int ISBN=Integer.parseInt(request.getParameter("ISBN"));
        BookInfo book=new BookInfo();
        book.setISBN(ISBN);
        book.setBookName(request.getParameter("bookName"));

        book.setTypeName(request.getParameter("typeName"));
        book.setWriter(request.getParameter("writer"));
        book.setTranslator(request.getParameter("translator"));
        book.setPublisher(request.getParameter("pulisher"));
        book.setPublishDate(request.getParameter("pulishDate"));
        book.setPrice(Float.valueOf( request.getParameter("price")));
        book.setRemain(Integer.parseInt(request.getParameter("remain")));
        Book_Info.bookCreate(book);
        request.getRequestDispatcher("/Functions/bookinfo.jsp").forward(request,
                response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

