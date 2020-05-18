package net.chensee.base.action.userGroup.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.action.resource.po.FurtherResourcePo;

/**
 * 用户组和资源关联实体类
 * @author : wh
 * @date : 2019年09月17日 10:54:49
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupResourcePo extends FurtherResourcePo {

    /**用户组ID*/
    private Long groupId;

}
