package com.eye.cool.book.adapter

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by ycb on 16/4/18.
 *
 * Kotlin must be a compile-time constant when setting annotations,
 * and layout was not generated at the time, so consider using @layoutname + constant name.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
annotation class LayoutName(val value: String)