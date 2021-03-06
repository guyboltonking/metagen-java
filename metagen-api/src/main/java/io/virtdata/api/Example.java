package io.virtdata.api;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(value = Examples.class)
public @interface Example {
    String value();
}
