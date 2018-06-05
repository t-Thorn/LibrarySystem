package Servlets;

import henu.bean.Reader;
import henu.dao.Pages;
import henu.dao.impl.book.book_Borrow;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "bookBorrw", value = "/Servlets/bookBorrw")
public class bookBorrw extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));
        Pages pages=Pages.getpage(request,1);
        int index=pages.getFlag()*8+id;
        try{
            book_Borrow.borrow(request,index);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            request.getRequestDispatcher("/bookinfo.jsp?error=yes").forward(request,
                    response);
            return;
        }
        request.getRequestDispatcher("/borrowSuccess.jsp").forward(request,
                response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}