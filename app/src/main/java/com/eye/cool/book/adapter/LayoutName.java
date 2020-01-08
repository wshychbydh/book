package com.eye.cool.book.adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ycb on 16/4/18.
 *
 * Kotlin must be a compile-time constant when setting annotations,
 * and layout was not generated at the time, so consider using @layoutname + constant name.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LayoutName {

  String value();
}
