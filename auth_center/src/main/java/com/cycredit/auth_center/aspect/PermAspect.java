package com.cycredit.auth_center.aspect;

import com.cycredit.auth_center.Exception.PermissionException;
import com.cycredit.auth_center.annotation.RequirePerm;
import com.cycredit.auth_center.mapper.PermissionMapper;
import com.cycredit.auth_center.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author helang
 * @date 2021/11/22
 */
@Aspect
@Component
@Slf4j
public class PermAspect {
    @Autowired
    private PermissionMapper permissionMapper;

    @Pointcut("@annotation(com.cycredit.auth_center.annotation.RequirePerm)")
    private void check() {
    }

    @Before("check()")
    public void check(JoinPoint pjp) throws Throwable {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        RequirePerm annotation = method.getAnnotation(RequirePerm.class);
        // 获取权限值
        String perm = annotation.value();
        // 查库鉴权，无权限抛出异常
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // token获取或从请求头中获取
        String role = request.getHeader("Role");
        Long id;
        if (StringUtils.isEmpty(role)) {
            String token = request.getHeader("Token");
            if (StringUtils.isEmpty(token)) {
                throw new PermissionException("无权限");
            } else {
                id = JWTUtil.getRoleId(token);
            }
        } else {
            try {
                id = Long.valueOf(role);
            } catch (NumberFormatException e) {
                throw new PermissionException("无权限");
            }
        }
        if (null == id) {
            throw new PermissionException("无权限");
        }
        List<String> perms = permissionMapper.findByUser(id);
        if (!perms.contains(perm)) {
            throw new PermissionException("无权限");
        }
    }
}
