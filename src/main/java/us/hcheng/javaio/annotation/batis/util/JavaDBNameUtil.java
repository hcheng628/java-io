package us.hcheng.javaio.annotation.batis.util;

import us.hcheng.javaio.annotation.generic.annotation.ClassAnn;
import us.hcheng.javaio.annotation.generic.annotation.FieldAnn;
import java.lang.reflect.Field;
import java.util.*;

public class JavaDBNameUtil {

    private JavaDBNameUtil(){}

    public static <T> List<Field> getFields(Class<T> klass, boolean hasId) {
        List<Field> ret = new ArrayList<>();
        Queue<Class> queue = new LinkedList<>(Arrays.asList(klass));

        while (!queue.isEmpty()) {
            Class currClass = queue.poll();

            if (hasId) {
                ret.addAll(Arrays.stream(currClass.getDeclaredFields()).toList());
            } else {
                ret.addAll(Arrays.stream(currClass.getDeclaredFields()).
                        filter(field -> !field.getName().equals("id")).
                        toList());
            }

            Class par = currClass.getSuperclass();
            if (par != null)
                queue.offer(par);
        }

        return ret;
    }

    public static <T> String tableName(Class<T> klass) {
        ClassAnn classAnn = klass.getAnnotation(ClassAnn.class);
        return classAnn == null ? klass.getSimpleName().toLowerCase() : classAnn.tableName();
    }

    public static String fieldName(Field f) {
        FieldAnn fieldAnn = f.getAnnotation(FieldAnn.class);
        return fieldAnn == null ? f.getName() : fieldAnn.fieldName();
    }

    public static String accesserName(String name, boolean get) {
        StringBuilder sb = new StringBuilder();
        sb.append(get ? "get" : "set");
        sb.append(Character.toUpperCase(name.charAt(0)));
        sb.append(name.substring(1));
        return sb.toString();
    }

}
