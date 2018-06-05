package henu.dao.impl.book.methods;

import henu.bean.BookInfo;
import henu.bean.Reader;
import henu.dao.SelectMethods;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class getUserByGender extends SelectMethods {
    public Reader[] getBookInfo(String name){
        try {
            Connection con= DbcpPool.getConnection();
            QueryRunner qr=new QueryRunner();
            String str="SELECT * FROM Reader where gender='"+name+"'";
            List<Reader> readers=qr.query(con,str,new BeanListHandler<Reader>(Reader.class));
            Reader []result=new Reader[readers.size()];
            int i=0;

            for (Reader r : readers) {
                result[i++]=r;
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
