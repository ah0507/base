package net.chensee.base.action.role.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.action.resource.po.FurtherResourcePo;

/**
 * 角色和资源关联实体类
 * @author : wh
 * @date : 2019年09月17日 10:52:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleResourcePo extends FurtherResourcePo {

    /**角色ID*/
    private Long roleId;

}
