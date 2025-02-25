package com.cungthinh.authservices.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "Không tìm thấy user", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1002, "User đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1003, "Tài khoản hoặc mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(1004, "Đã xảy ra lỗi xác thực", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Bạn không có quyền truy cập vào tài nguyên này", HttpStatus.FORBIDDEN),
    PERMISSION_NOT_FOUND(1006, "Permission không tồn tại", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1007, "Role không tồn tại", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(1008, "Token không hợp lệ", HttpStatus.UNAUTHORIZED),
    PROFILE_CREATION_FAILED(
            1009, "Lỗi khởi tạo profile, vui lòng kiểm tra lại dữ liệu", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
