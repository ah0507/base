package net.chensee.base.action.role.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.BaseInfoPo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 角色实体类
 * @author : shibo
 * @date : 2019/6/12 15:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RolePo extends BaseInfoPo {

    /**名称*/
    @NotBlank(message = "名称不可为空")
    private String name;
    /**父角色ID*/
    @NotNull(message = "父角色ID不可为空")
    private Long parentId;
    /**所属部门ID*/
    @NotNull(message = "所属部门ID不可为空")
    private Long deptId;
}
