package us.hcheng.javaio.annotation.batis.dao;

public interface IEntityDao {

    <T> boolean create(T t);

    <T> T queryById(Class<T> klass, int id);


    <T> boolean update(T t);

    <T> boolean delete(T t);

}
