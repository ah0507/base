package net.chensee.base.action.userGroup.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.BaseInfoPo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 用户组实体类
 * @author: wanghuan
 * @create: 2019-07-22 15:00
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupPo extends BaseInfoPo {

    /**名称*/
    @NotBlank(message = "名称不可为空")
    private String name;
    /**部门ID*/
    @NotNull(message = "部门ID不可为空")
    private Long deptId;

}
