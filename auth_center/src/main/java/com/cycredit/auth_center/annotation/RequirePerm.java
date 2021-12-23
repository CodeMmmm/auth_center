package com.cycredit.auth_center.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author helang
 * @date 2021/11/22
 */
@Target(ElementType.METHOD) // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 运行时可见
public @interface RequirePerm {
    // 权限标识
    String value();
    // 备注
    String remark();
}
