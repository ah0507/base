package net.chensee.base.action.folder.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.vo.IdVo;

import java.util.Date;

/**
 * @Author : shibo
 * @Date : 2019/7/22 10:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FolderVo extends IdVo {

    /**父文件夹ID*/
    private Long parentId;
    private String parentName;
    /**文件夹名称*/
    private String name;
    /**文件夹类型(1 自动  2 手动)*/
    private String type;
    /**文件夹等级*/
    private Integer rank;

    /**业务*/
    private String business;
    /**业务ID*/
    private String businessId;

    /**是否叶子节点*/
    private Integer isLeaf;

    private Date createTime;
    private Long createBy;
    private Date updateTime;
    private Long updateBy;

    private Integer version;
    private Integer status;
}
