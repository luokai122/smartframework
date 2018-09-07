package org.smart4j.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author StrangeLuo
 */
//用于设定注解使用范围,注解在class interface (including annotation type), or enum declaration
@Target(ElementType.TYPE)
//描述保留注释的各种策略，它们与元注释(@Retention)一起指定注释要保留多长时间
//注释将被编译器记录在类文件中*在运行时保留VM，因此可以反读。
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
}
