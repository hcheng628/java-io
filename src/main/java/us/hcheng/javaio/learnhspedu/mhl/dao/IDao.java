package us.hcheng.javaio.learnhspedu.mhl.dao;

import java.util.List;

public interface IDao<T> {
    int execute(String sql, Object... params);
    List<T> query(String sql, Class<T> clazz, Object... params);
    T queryOne(String sql, Class<T> clazz, Object... params);
    Object queryScalar(String sql, Object... params);
}
