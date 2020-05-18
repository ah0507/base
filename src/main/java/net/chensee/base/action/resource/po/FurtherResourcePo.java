package net.chensee.base.action.resource.po;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 和资源关联实体类
 * @author : wh
 * @date : 2019年09月17日 17:58:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FurtherResourcePo extends ResourcePo {

    /**资源父级名称*/
    private String parentName;
    /**文件夹名称*/
    private String folderName;

    /**可视文件夹ID*/
    private Long visualFolderId;
    /**排除字段*/
    private String excludeColumns;
    /**和资源关联的状态*/
    private Integer withResourceStatus;

}
