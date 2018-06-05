package henu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//数据库连接
public class DbcpPool {

    public static Connection getConnection() {
        try {
            String className="com.microsoft.sqlserver.jdbc.SQLServerDriver";	//数据库驱动
            Class.forName(className);
        }catch (ClassNotFoundException e)
        {

            e.printStackTrace();
            return  null;
        }

        try {
            Connection conn;

            String url="jdbc:sqlserver://localhost:1433;databaseName=Library;user=sa;" +
                    "password=999567;";

            conn= DriverManager.getConnection(url);
            return conn;
        } catch (SQLException e)
        {
            return  null;
        }


    }

}
