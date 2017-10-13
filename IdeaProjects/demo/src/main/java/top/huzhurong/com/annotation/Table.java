package com.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
	 public String name() default "";
}
