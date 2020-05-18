package net.chensee.base.action.userGroup.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.chensee.base.common.vo.BaseInfoVo;

/**
 * @description: 用户组视图
 * @author: wanghuan
 * @create: 2019-07-22 15:01
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupVo extends BaseInfoVo {

    /**名称*/
    private String name;
    /**部门ID*/
    private Long deptId;
    /**部门名称*/
    private String deptName;


}
