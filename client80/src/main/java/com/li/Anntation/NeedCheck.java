package com.li.Anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE}) // 可用在方法或类上
@Retention(RetentionPolicy.RUNTIME) // 在运行时有效
public @interface NeedCheck {
}
