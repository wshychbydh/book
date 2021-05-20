package com.eye.cool.book.adapter

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by ycb on 16/4/18.
 */
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(RetentionPolicy.RUNTIME)
annotation class LayoutId(val value: Int = 0)