package Servlets;

import henu.dao.Pages;
import henu.dao.impl.book.pages.book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "page_Jump", value = "/Servlets/page_Jump")
public class page_Jump  extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id=Integer.parseInt(request.getParameter("id"));

        Pages pages=(Pages) request.getSession().getAttribute("page");//获得页面对象
        pages.setFlag(id);
        request.getSession().setAttribute("page",pages);
        if (pages instanceof book) {
            request.getRequestDispatcher("/Functions/bookinfo.jsp").forward(request,
                    response);
        }
        else {
            request.getRequestDispatcher("/Functions/userview.jsp").forward(request,
                    response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
