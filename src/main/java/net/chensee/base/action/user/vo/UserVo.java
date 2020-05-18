package net.chensee.base.action.user.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.vo.BaseInfoVo;

/**
 * @author : shibo
 * @date : 2019/6/11 17:54
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends BaseInfoVo {

    /**真实姓名*/
    private String realName;
    /**昵称*/
    private String nickName;
    /**手机号*/
    private String mobile;
    /**头像*/
    private String avatar;
}
