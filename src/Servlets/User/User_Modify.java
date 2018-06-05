package Servlets.User;

import henu.dao.Pages;
import henu.dao.impl.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "User_Modify", value = "/Servlets/User/User_Modify")
public class User_Modify extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        Pages pages=Pages.getpage(request,2);
        int index=pages.getFlag()*8+id;
        request.getRequestDispatcher("/Functions/userModify.jsp?index="+index).forward(request,
                response);
    }
}
