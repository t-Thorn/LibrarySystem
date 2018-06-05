package henu.dao;

import henu.bean.BookInfo;
import henu.util.DbcpPool;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class SelectMethods {
    public abstract Object[] getBookInfo(String name);
}
