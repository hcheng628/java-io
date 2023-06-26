package us.hcheng.javaio.learnhspedu.mhl.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import us.hcheng.javaio.learnhspedu.mhl.util.DBUtil;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class BasicDao<T> implements IDao<T> {
    protected static DataSource ds;

    static {
        ds = DBUtil.getDataSource();
    }

    @Override
    public int execute(String sql, Object... params) {
        try {
            return new QueryRunner(ds).update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<T> query(String sql, Class<T> clazz, Object... params) {
        try {
                return new QueryRunner(ds).query(sql, new BeanListHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T queryOne(String sql, Class<T> clazz, Object... params) {
        try {
            return new QueryRunner(ds).query(sql, new BeanHandler<>(clazz), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object queryScalar(String sql, Object... params) {
        try {
            return new QueryRunner(ds).query(sql, new ScalarHandler<>(), params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
