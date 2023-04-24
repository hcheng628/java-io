package us.hcheng.javaio.annotation.generic.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAnn {
    String fieldName();
}
