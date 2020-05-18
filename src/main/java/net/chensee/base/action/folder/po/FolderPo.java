package net.chensee.base.action.folder.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.IdPo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author : shibo
 * @Date : 2019/7/22 10:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FolderPo extends IdPo {

    /**父文件夹ID*/
    private Long parentId;
    /**文件夹名称*/
    @NotBlank(message = "文件夹名称不可为空")
    private String name;
    /**文件夹类型(1 自动  2 手动)*/
    //@NotBlank(message = "文件夹类型不可为空")
    private String type;
    /**文件夹等级*/
    //@NotNull(message = "文件夹等级不可为空")
    private Integer rank;

    /**业务*/
    //@NotBlank(message = "业务类型不可为空")
    private String business;
    /**业务ID*/
    //@NotBlank(message = "业务ID不可为空")
    private String businessId;

    /**是否叶子节点*/
    //@NotNull(message = "是否叶子节点不可为空")
    private Integer isLeaf;

    private Date createTime;
    private Long createBy;
    private Date updateTime;
    private Long updateBy;

    private Integer version;
    private Integer status;
}
