package net.chensee.base.action.resource.po;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.po.BaseInfoPo;

import javax.validation.constraints.NotBlank;

/**
 * 资源实体类
 * @author : shibo
 * @date : 2019/6/12 16:00
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResourcePo extends BaseInfoPo {

    /**父ID*/
    private Long parentId;
    /**菜单代号，规范标识*/
    @NotBlank(message = "菜单代号不可为空")
    private String code;
    /**菜单名称*/
    @NotBlank(message = "菜单名称不可为空")
    private String name;
    /**菜单类型(1 菜单 2 业务操作)*/
    private String menuType;
    /**操作类型(1 查看 2 修改)*/
    private String oprType;
    /**菜单序号*/
    private Integer num;
    /**菜单地址*/
    private String path;
    /**请求方式*/
    private String method;
    /**菜单图标*/
    private String icon;
    /**菜单标题*/
    private String title;
    /**是否隐藏*/
    private Integer hidden;
    /**是否需要授权*/
    private Integer requireAuth;
    /**组件*/
    private String component;
    /**重定向*/
    private String redirect;
    /**包含的列*/
    private String columns;
}
