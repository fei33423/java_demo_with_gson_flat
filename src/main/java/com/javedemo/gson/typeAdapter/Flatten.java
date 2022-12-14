package com.javedemo.gson.typeAdapter;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Flatten {
    /**
     * 前缀
     * @return
     */
    String value() default "";
}
