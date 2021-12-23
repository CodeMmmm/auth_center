package com.cycredit.auth_center.handler;

import com.cycredit.auth_center.Exception.PermissionException;
import com.cycredit.auth_center.entity.ResponseMessage;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(PermissionException.class)
    public ResponseMessage handleAuth(PermissionException e) {
        return ResponseMessage.error(500, e.getMessage(), "无权限");
    }
}
