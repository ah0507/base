package net.chensee.base.action.role.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.vo.BaseInfoVo;

/**
 * @author : shibo
 * @date : 2019/6/12 15:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleVo extends BaseInfoVo {

    /**名称*/
    private String name;
    /**父角色ID*/
    private Long parentId;
    /**父角色名称*/
    private String parentName;
    /**所属部门ID*/
    private Long deptId;
    /**所属部门名称*/
    private String deptName;


}
