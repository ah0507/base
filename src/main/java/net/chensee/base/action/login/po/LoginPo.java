package net.chensee.base.action.login.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.IdPo;

/**
 * 登录方式实体类
 * @author : shibo
 * @date : 2019/6/5 9:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginPo extends IdPo {

    /**用户ID*/
    private Long userId;
    /**用户登录名*/
    private String loginName;
    /**用户登录密码*/
    private String loginPass;
    /**登陆类型*/
    private String type;
    /**状态*/
    private Integer status;
}
