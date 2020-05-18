package net.chensee.base.action.resource.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.Folderable;
import net.chensee.base.common.vo.DataColumnVo;
import net.chensee.base.common.vo.IdVo;
import net.chensee.base.utils.JsonUtil;

import java.util.List;

/**
 * @author xx
 * @program base
 * @date 2019-09-16 18:09
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseResourceVo extends IdVo implements Folderable {

    /**
     * 父ID
     */
    private Long parentId;
    private String parentName;
    /**
     * 菜单代号，规范标识
     */
    private String code;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单类型(1 菜单 2 业务操作)
     */
    private String menuType;
    /**
     * 操作类型(1 查看 2 修改)
     */
    private String oprType;
    /**
     * 菜单序号
     */
    private Integer num;
    /**
     * 菜单地址
     */
    private String path;
    /**
     * 请求方式
     */
    private String method;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 文件夹ID
     */
    private Long folderId;
    /**
     * 文件夹名称
     */
    private String folderName;
    /**
     * 菜单标题
     */
    private String title;
    /**
     * 是否隐藏
     */
    private Integer hidden;
    /**
     * 是否需要授权
     */
    private Integer requireAuth;
    /**
     * 组件
     */
    private String component;

    /**
     * 重定向
     */
    private String redirect;
    /**
     * 包含的列
     */
    private List<DataColumnVo> columns;

    public void setColumns(String columns) {
        if (columns != null) {
            this.columns = JsonUtil.toList(columns, DataColumnVo.class);
        }
    }

}
