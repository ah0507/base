package net.chensee.base.action.dept.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.Folderable;
import net.chensee.base.common.vo.BaseInfoVo;

/**
 * @author : shibo
 * @date : 2019/6/12 9:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptVo extends BaseInfoVo implements Folderable {

    /**名称*/
    private String name;
    /**简称*/
    private String shortName;
    /**父级ID*/
    private Long parentId;
    private String parentName;
    private String parentShortName;
    /**组织类型*/
    private Integer type;
    /**是否虚拟组织*/
    private Integer isVirtual;

    /**文件夹ID*/
    private Long folderId;
    /**所属文件夹名称*/
    private String folderName;

}
