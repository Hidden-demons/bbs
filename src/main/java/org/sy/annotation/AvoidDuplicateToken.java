package org.sy.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Auther: H_ui
 * @Date:2019/10/15 9:20
 * @Description: 修饰注解的 注解 --》 元注解
 *
 * @Target(ElementType.METHOD)用在方法上
 * @Retention(RetentionPolicy.RUNTIME) 在运行时使用，一般选择这个
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AvoidDuplicateToken {
    public  boolean save() default  false;
    public  boolean rmove() default false;
}
