package henu.dao.impl.book.methods;

import henu.bean.BookInfo;
import henu.dao.SelectMethods;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class getBookByWriter extends SelectMethods {
    public BookInfo[] getBookInfo(String writer){
        try {
            Connection con= DbcpPool.getConnection();
            QueryRunner qr=new QueryRunner();
            String str="SELECT * FROM bookinfo where writer='"+writer+"'";
            List<BookInfo> bookInfos=qr.query(con,str,new BeanListHandler<BookInfo>(BookInfo.class));
            BookInfo []result=new BookInfo[bookInfos.size()];
            int i=0;
            System.out.println(result.length);
            for (BookInfo s : bookInfos) {
                result[i++]=s;
            }
            DbUtils.closeQuietly(con);
            return result;
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
