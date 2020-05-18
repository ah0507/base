package net.chensee.base.action.userGroup.business;

import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.po.UserGroupPo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;

import java.util.List;
import java.util.Map;

/**
 * @description: 用户组接口
 * @author: wanghuan
 * @create: 2019-07-22 15:12
 **/
public interface UserGroupBus {

    /**
     * @Description: 添加用户组
     * @Param: UserGroupPo
     * @return: void
     */
    void addUserGroup(UserGroupPo userGroupPo);

    /**
     * @Description: 更新用户组信息
     * @Param: UserGroupPo
     * @return: void
     */
    void updateUserGroup(UserGroupPo userGroupPo);

    /**
     * @Description: 删除用户组
     * @Param: id
     * @return: void
     */
    void deleteUserGroupById(Long id);

    /**
     * @Description: 获取所有用户组
     * @Param:
     * @return: List<UserGroupVo>
     */
    List<UserGroupVo> getAllUserGroup(String name, Long pageStart, Integer pageSize);

    /**
     * 获取数据条数
     *
     * @param name
     * @return
     */
    Long getCount(String name);

    /**
     * @Description: 根据ID获取用户组
     * @Param: id
     * @return: UserGroupVo
     */
    UserGroupVo getUserGroupById(Long id);

    /**
     * @Description: 根据用户组ID获取组内所有用户
     * @Param: id
     * @return: List<UserVo>
     */
    List<UserVo> getUserByUserGroupId(Long id);

    /**
     * @Description: 根据用户组ID获取组内的所有角色
     * @Param: id
     * @return: List<RoleVo>
     */
    List<RoleVo> getRoleByUserGroupId(Long id);

    /**
     * @Description: 根据用户组ID获取组内的所有资源
     * @Param: id
     * @return: List<ResourceVo>
     */
    List<ResourceVo> getResourceByUserGroupId(Long id);

    /**
     * @Description: 给用户组分配用户
     * @Param: userId, GroupId, visualFolderId
     * @return: void
     */
    void addUserToUserGroup(String userId, Long groupId);

    /**
     * @Description: 给用户组分配角色
     * @Param: roleId, userGroupId, visualFolderId
     * @return: void
     */
    void addRoleToUserGroup(String roleId, Long groupId);

    /**
     * @Description: 给用户组分配资源
     * @Param: groupId, config
     * @return: void
     */
    void allotResourceToUserGroup(Long groupId, Map<Long, Map<Long,String>> paramMap);

    /**
     * @Description: 获取指定用户、资源、文件夹的排除字段
     * @Param: groupId, resourceId, folderId
     * @return: String
     */
    String getExcludeColumns(Long groupId, Long resourceId, Long folderId);
}
