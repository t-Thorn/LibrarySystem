package Servlets;

import henu.bean.BookInfo;
import henu.dao.impl.book.Book_Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "subrcibe_Book", value = "/Servlets/subrcibe_Book")
public class subrcibe_Book extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //BookInfo bookInfo= Book_Info.getConcreteBook(request);
        try {
            Book_Info.bookSubscribe(request);
        }catch (Exception e)
        {
            System.out.println("outside:"+e.getMessage());
        }
        request.getRequestDispatcher("/Functions/bookinfo.jsp").forward
                (request,
                        response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
