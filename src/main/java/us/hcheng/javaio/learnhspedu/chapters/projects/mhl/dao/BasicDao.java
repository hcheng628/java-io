package us.hcheng.javaio.learnhspedu.chapters.projects.mhl.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import us.hcheng.javaio.learnhspedu.chapters.projects.mhl.util.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BasicDao<T> implements IDao<T> {
    protected static Connection conn;

    static {
        conn = DBUtil.getConnection();
    }

    @Override
    public int execute(String sql, Object... params) {
        try {
            return new QueryRunner().update(conn, sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> query(String sql, Class<T> clazz, Object... params) {
        try {
            return new QueryRunner().query(conn, sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T queryOne(String sql, Class<T> clazz, Object... params) {
        try {
            return new QueryRunner().query(conn, sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object queryScalar(String sql, Object... params) {
        try {
            return new QueryRunner().query(conn, sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
