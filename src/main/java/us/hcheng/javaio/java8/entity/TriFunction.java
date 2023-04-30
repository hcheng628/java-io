package us.hcheng.javaio.java8.entity;


public interface TriFunction<T, U, V, W> {

    W apply(T t, U u, V v);

}
