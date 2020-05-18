package net.chensee.base.action.role.service;

import net.chensee.base.action.role.po.RolePo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

import java.util.Map;

/**
 * @Author : shibo
 * @Date : 2019/8/2 10:37
 */
public interface RoleService {

    /**
     * 添加角色
     *
     * @param rolePo
     */
    ObjectResponse addRole(RolePo rolePo);

    /**
     * 修改角色
     *
     * @param rolePo
     */
    BaseResponse updateRole(RolePo rolePo);

    /**
     * 删除角色
     *
     * @param id
     */
    BaseResponse deleteRole(Long id);

    /**
     * 获取所有角色
     *
     * @return
     */
    ObjectResponse getAllRole();

    /**
     * @Description: 根据ID获取角色
     * @Param: id
     * @return: ObjectResponse
     */
    ObjectResponse getRoleById(Long id);

    /**
     * @Description: 获取角色下所有资源
     * @Param: id
     * @return: ObjectResponse
     */
    ObjectResponse getResourceByRoleId(Long id);

    /**
     * @Description: 获取角色下所有用户
     * @Param: id
     * @return: ObjectResponse
     */
    ObjectResponse getUserByRoleId(Long id);

    /**
     * @Description: 获取角色下所有用户组
     * @Param: id
     * @return: ObjectRepsonse
     */
    ObjectResponse getUserGroupByRoleId(Long id);

    /**
     * @Description: 给角色添加用户
     * @Param: userIds, roleId
     * @return: BaseResponse
     */
    BaseResponse addUserToRole(String userIds, Long roleId) throws Exception;

    /**
     * @Description: 给角色添加资源
     * @Param: roleId, paramMap
     * @return: BaseResponse
     */
    BaseResponse addResourceToRole(Long roleId, Map<Long, Map<Long,String>> paramMap) throws Exception;

    /**
     * @Description: 给角色添加用户组
     * @Param: roleId, groupIds
     * @return: BaseResponse
     */
    BaseResponse addUserGroupToRole(String groupIds, Long roleId) throws Exception;

    /**
     * @Description: 查询指定角色、资源、文件夹的排除字段
     * @Param: roleId, resourceId, visualFolderId
     * @return: ObjectResponse
     */
    ObjectResponse getExcludeColumns(Long roleId, Long resourceId, Long folderId);
}
