package net.chensee.base.component;

/**
 * @author xx
 * @program base
 * @date 2019-08-30 11:09
 * @description 授权验证
 */
public enum ErrorCodeEnum {

    // 3 开头  auth fail
    LOGIN_INCORRECT(30001),
    LOGIN_FAIL(30002),
    TOKEN_EXPIRE(30003),
    LOGIN_WITHOUT(30004),
    TOKEN_INVALID(30005),

    // 4 开头  access denied
    AUTHORITY(40001);


    int code = 0;

    ErrorCodeEnum(int code) {
        this.code = code;
    }
}
