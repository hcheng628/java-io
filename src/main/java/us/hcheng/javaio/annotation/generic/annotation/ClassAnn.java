package us.hcheng.javaio.annotation.generic.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClassAnn {
    String tableName();
}
