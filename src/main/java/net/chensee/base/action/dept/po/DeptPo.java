package net.chensee.base.action.dept.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.BaseInfoPo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 部门组织实体类
 * @author : shibo
 * @date : 2019/6/12 9:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptPo extends BaseInfoPo {

    /**名称*/
    @NotBlank(message = "名称不可为空")
    private String name;
    /**简称*/
    @NotBlank(message = "简称不可为空")
    private String shortName;
    /**父级ID*/
    @NotNull(message = "父级ID不可为空")
    private Long parentId;
    /**组织类型*/
    @NotNull(message = "组织类型不可为空")
    private Integer type;
    /**是否虚拟组织*/
    @NotNull(message = "是否虚拟组织不可为空")
    private Integer isVirtual;

}
