package net.chensee.base.action.userGroup.service;

import net.chensee.base.action.userGroup.po.UserGroupPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

import java.util.Map;

/**
 * @Author : shibo
 * @Date : 2019/8/2 12:55
 */
public interface UserGroupService {

    /**
     * @Description: 添加用户组
     * @Param: UserGroupPo
     * @return: void
     */
    ObjectResponse addUserGroup(UserGroupPo userGroupPo);

    /**
     * @Description: 更新用户组信息
     * @Param: UserGroupPo
     * @return: void
     */
    BaseResponse updateUserGroup(UserGroupPo userGroupPo);

    /**
     * @Description: 删除用户组
     * @Param: id
     * @return: void
     */
    BaseResponse deleteUserGroupById(Long id);

    /**
     * @Description: 获取所有用户组
     * @Param:
     * @return: List<UserGroupVo>
     */
    ObjectResponse getAllUserGroup(String name, Integer pageSize, Long pageNumber);

    /**
     * @Description: 根据ID获取用户组
     * @Param: id
     * @return: UserGroupVo
     */
    ObjectResponse getUserGroupById(Long id);

    /**
     * @Description: 根据用户组ID获取组内所有用户
     * @Param: id
     * @return: List<UserVo>
     */
    ObjectResponse getUserByUserGroupId(Long id);

    /**
     * @Description: 根据用户组ID获取组内的所有角色
     * @Param: id
     * @return: List<RoleVo>
     */
    ObjectResponse getRoleByUserGroupId(Long id);

    /**
     * @Description: 根据用户组ID获取组内的所有资源
     * @Param: id
     * @return: List<ResourceVo>
     */
    ObjectResponse getResourceByUserGroupId(Long id);

    /**
     * @Description: 给用户组分配用户
     * @Param: userId, GroupId, visualFolderId
     * @return: void
     */
    BaseResponse addUserToUserGroup(String userId, Long groupId) throws Exception;

    /**
     * @Description: 给用户组分配角色
     * @Param: roleId, userGroupId, visualFolderId
     * @return: void
     */
    BaseResponse addRoleToUserGroup(String roleId, Long groupId) throws Exception;

    /**
     * @Description: 给用户组分配资源
     * @Param: groupId, config
     * @return: void
     */
    BaseResponse addResourceToUserGroup(Long groupId, Map<Long, Map<Long,String>> paramMap) throws Exception;

    /**
     * @Description: 获取指定用户、资源、文件夹的排除字段
     * @Param: groupId, resourceId, folderId
     * @return: String
     */
    ObjectResponse getExcludeColumns(Long groupId, Long resourceId, Long folderId);
}
