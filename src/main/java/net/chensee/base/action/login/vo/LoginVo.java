package net.chensee.base.action.login.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.vo.IdVo;

/**
 * @author : shibo
 * @date : 2019/6/5 9:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginVo extends IdVo {

    /**用户ID*/
    private Long userId;
    private String realName;
    private String nickName;
    private String mobile;
    private String avatar;

    /**用户登录名*/
    private String loginName;
    /**用户登录密码*/
    private String loginPass;
    /**登陆类型*/
    private String type;
    /**状态*/
    private Integer status;
}
