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
import java.sql.SQLException;

@WebServlet(name = "User_Create", value = "/Servlets/User/User_Create")
public class User_Create extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Reader reader=new Reader();
        reader.setUsername(request.getParameter("username"));
        String msg="ok";
        if(!UserInfo.userIsExist(reader.getUsername()))
        {
            //不存在该用户
            System.out.println("不存在");
            reader.setPassword(request.getParameter("password"));
            reader.setName(request.getParameter("name"));
            reader.setGender(request.getParameter("gender"));
            reader.setAge(Integer.parseInt(request.getParameter("age")));
            reader.setTel(request.getParameter("tel"));
            reader.setID(request.getParameter("id"));
            reader.setBirthday(request.getParameter("birthday"));
            reader.setMoney(0);
           try {
               UserInfo.userCreate(reader);
           }catch (SQLException e){
               msg=e.getMessage();
               System.out.println(msg);
           }finally {
               if(msg.equals("ok")){
                   request.getRequestDispatcher("/Functions/userview" +
                           ".jsp").forward(request, response);
               }
               else
               {
                   request.setAttribute("message", msg);
                   request.getRequestDispatcher("/Functions/userCreate" +
                           ".jsp").forward(request, response);
               }
           }

        }
        else
        {
            request.setAttribute("message", "用户已存在");
            request.getRequestDispatcher("/Functions/userCreate.jsp").forward
                    (request,
                    response);
        }
    }
}

