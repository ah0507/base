package net.chensee.base.action.user.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.BaseInfoPo;

import javax.validation.constraints.NotBlank;

/**
 * 用户实体类
 * @author : shibo
 * @date : 2019/6/11 17:30
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPo extends BaseInfoPo {

    /**真实姓名*/
    @NotBlank(message = "真实姓名不可为空")
    private String realName;
    /**昵称*/
    @NotBlank(message = "昵称不可为空")
    private String nickName;
    /**手机号*/
    private String mobile;
    /**头像*/
    private String avatar;
}
