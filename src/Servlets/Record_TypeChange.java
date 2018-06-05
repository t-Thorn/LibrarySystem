package Servlets;

import henu.dao.impl.book.Book_Info;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Record_TypeChange", value = "/Servlets/Record_TypeChange")
public class Record_TypeChange extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { ;
        String z=request.getParameter("id");
        String pagetype=(String) request.getSession().getAttribute("pageType");
        request.getSession().setAttribute("page",null);
        if(pagetype.equals("RB"))
            request.getSession().setAttribute("pageType", "RB_");
        else
            request.getSession().setAttribute("pageType", "RB");
        request.setAttribute("id", z);
        request.getRequestDispatcher("/Functions/borrowRecord.jsp").forward
                (request,
                        response);
    }

}

