package net.chensee.base.common;

/**
 * @author : shibo
 * @date : 2019/5/17 15:58
 */
public class BaseResponse {
    private Integer code;
    private String msg;

    public static BaseResponse ok() {
        BaseResponse rd = new BaseResponse();
        rd.setCode(ResponseCodeConstants.OK);
        return rd;
    }

    public static BaseResponse fail() {
        BaseResponse rd = new BaseResponse();
        rd.setCode(ResponseCodeConstants.FAIL);
        return rd;
    }

    public static BaseResponse fail(Integer code) {
        BaseResponse rd = new BaseResponse();
        rd.setCode(ResponseCodeConstants.FAIL);
        return rd;
    }

    public static BaseResponse fail(String msg) {
        BaseResponse rd = new BaseResponse();
        rd.setCode(ResponseCodeConstants.FAIL);
        rd.setMsg(msg);
        return rd;
    }

    public static BaseResponse fail(Integer code, String msg) {
        BaseResponse rd = new BaseResponse();
        rd.setCode(code);
        rd.setMsg(msg);
        return rd;
    }

    public static BaseResponse of(Integer code, String msg) {
        BaseResponse rd = new BaseResponse();
        rd.setCode(code);
        rd.setMsg(msg);
        return rd;
    }

    public boolean isOk() {
        return ResponseCodeConstants.OK.equals(this.getCode());
    }

    public boolean isFail() {
        return !this.isOk();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setCode(final Integer code) {
        this.code = code;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public BaseResponse() {
    }

    public BaseResponse(final Integer code, final String msg) {
        this.code = code;
        this.msg = msg;
    }
}
