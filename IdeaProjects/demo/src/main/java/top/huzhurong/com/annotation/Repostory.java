package com.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * ×¢½â³Ö¾Ã²ã
 * @author Öñ
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Repostory {
	 String value() default "";
}
