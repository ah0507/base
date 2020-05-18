package net.chensee.base.component;

import org.springframework.security.core.AuthenticationException;

/**
 * @author xx
 * @program base
 * @date 2019-07-09 15:35
 * @description
 */
public class MyAuthenticationException extends AuthenticationException {

    private ErrorCodeEnum exceptionEnum;

    /**
     * 加入错误状态值
     * @param exceptionEnum
     */
    public MyAuthenticationException(ErrorCodeEnum exceptionEnum) {
        super(exceptionEnum.name());
        this.exceptionEnum = exceptionEnum;
    }

    public MyAuthenticationException(String msg) {
        super(msg);
    }

    public ErrorCodeEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
