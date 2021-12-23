package com.cycredit.auth_center.Exception;

/**
 * @author helang
 * @date 2021/11/22
 */
public class PermissionException extends RuntimeException {
    public PermissionException() {
        super();
    }

    public PermissionException(String message) {
        super(message);
    }
}
