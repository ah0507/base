package net.chensee.base.action.user.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.action.resource.po.FurtherResourcePo;

/**
 * 用户和资源关联实体类
 * @author : wh
 * @date : 2019年09月17日 10:56:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserResourcePo extends FurtherResourcePo {

    /**用户ID*/
    private Long userId;

    /**资源指向*/
    private Integer direct;

    /**增加字段*/
    private String includeColumns;

}
