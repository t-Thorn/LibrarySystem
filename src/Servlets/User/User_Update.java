package Servlets.User;

import henu.bean.BookInfo;
import henu.bean.Reader;
import henu.dao.impl.book.Book_Info;
import henu.dao.impl.book.pages.book;
import henu.dao.impl.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "User_Update", value = "/Servlets/User/User_Update")
public class User_Update extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username=request.getParameter("username");
        Reader reader=new Reader();
        reader.setUsername(username);
        reader.setPassword(request.getParameter("password"));

        reader.setName(request.getParameter("name"));
        reader.setID(request.getParameter("id"));
        reader.setTel(request.getParameter("tel"));
        reader.setAge(Integer.parseInt(request.getParameter("age")));
        reader.setGender(request.getParameter("gender"));
        reader.setBirthday( request.getParameter("birthday"));
        reader.setMoney(Float.parseFloat(request.getParameter("money")));
        UserInfo.updateUserInfo(reader);
        request.getRequestDispatcher("/Functions/userview.jsp").forward
                (request, response);

    }
}
