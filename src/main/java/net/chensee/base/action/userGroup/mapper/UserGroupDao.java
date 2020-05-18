package net.chensee.base.action.userGroup.mapper;

import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.po.UserGroupPo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @description: 用户组Dao接口
 * @author: wanghuan
 * @create: 2019-07-22 15:06
 **/
public interface UserGroupDao {

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
    void deleteUserGroupById(@Param("id") Long id);

    /**
     * @Description: 获取所有用户组
     * @Param:
     * @return: List<UserGroupVo>
     */
    List<UserGroupVo> getAllUserGroup(@Param("name") String name,
                                      @Param("pageStart") Long pageStart,
                                      @Param("pageSize") Integer pageSize,
                                      @Param("currentFolders") Set<Long> currentFolders);

    /**
     * @Description: 根据ID获取用户组
     * @Param: id
     * @return: UserGroupVo
     */
    UserGroupVo getUserGroupById(@Param("id") Long id);

    /**
     * @Description: 根据用户组ID获取组内所有用户
     * @Param: id
     * @return: List<UserVo>
     */
    List<UserVo> getUserByUserGroupId(@Param("id") Long id);

    /**
     * @Description: 根据用户组ID获取组内的所有角色
     * @Param: id
     * @return: List<RoleVo>
     */
    List<RoleVo> getRoleByUserGroupId(@Param("id") Long id);

    /**
     * @Description: 给用户组分配用户
     * @Param: userId, GroupId, visualFolderId
     * @return: void
     */
    void addUserToUserGroup(@Param("userId") Long userId,
                            @Param("groupId") Long groupId);

    /**
     * @Description: 给用户组分配角色
     * @Param: roleId, userGroupId, visualFolderId
     * @return: void
     */
    void addRoleToUserGroup(@Param("roleId") Long roleId,
                            @Param("groupId") Long groupId);

    /**
     * @Description: 给用户组分配资源
     * @Param: groupId, resourceId, visualFolderIds
     * @return: void
     */
    void addResourceToUserGroup(@Param("groupId") Long groupId,
                                @Param("resourceId") Long resourceId,
                                @Param("visualFolderId") Long visualFolderId,
                                @Param("excludeColumns") String excludeColumns);

    /**
     * @Description: 获取用户组总数目
     * @Param: name
     * @return: long
     */
    long getCount(@Param("name") String name);

    /**
     * 删除指定userGroupId的关联资源
     * @param groupId
     */
    void removeResourceByGroupId(@Param("groupId") Long groupId);

    /**
     * @Description: 删除用户组内指定用户
     * @Param: userId，groupId
     * @return: void
     */
    void removeUserFromUserGroup(@Param("userId") Long userId,
                                 @Param("groupId") Long groupId);

    /**
     * @Description: 删除用户组内指定角色
     * @Param: roleId, groupId
     * @return: void
     */
    void removeRoleFromUserGroup(@Param("roleId") Long roleId,
                                 @Param("groupId") Long groupId);

    /**
     * @Description: 删除用户组内指定资源
     * @Param: groupId, resourceId
     * @return: void
     */
    void deleteResourceByGroupId(@Param("groupId") Long groupId);

    /**
     * 将资源从用户组中移除
     * @param groupId,resourceId
     * @return: void
     */
    void removeResourceFromUserGroup(@Param("groupId") Long groupId,
                                     @Param("resourceId") Long resourceId);

    /**
     * 通过用户组ID获取资源的集合
     * @param groupId
     * @return List<Long>
     */
    List<Long> getResourceIdsByUserGroupId(@Param("groupId")Long groupId);

    /**
     * 获取指定用户组、资源、文件夹的数量
     * @param groupId,resourceId,visualFolderId
     * @return Integer
     */
    Integer getResourceToUserGroup(@Param("groupId") Long groupId,
                                   @Param("resourceId") Long resourceId,
                                   @Param("visualFolderId") Long visualFolderId);

    /**
     * 更新指定用户组、资源、文件夹的排除字段
     * @param groupId,resourceId,visualFolderId,excludeColumns
     * @return void
     */
    void updateResourceToUserGroup(@Param("groupId") Long groupId,
                                   @Param("resourceId") Long resourceId,
                                   @Param("visualFolderId") Long visualFolderId,
                                   @Param("excludeColumns") String excludeColumns);

    /**
     * 获取指定用户组、资源、文件夹的排除字段
     * @param groupId,resourceId,visualFolderId
     * @return String
     */
    String getExcludeColumns(@Param("groupId") Long groupId,
                             @Param("resourceId") Long resourceId,
                             @Param("visualFolderId") Long visualFolderId);

    /**
     * 删除指定角色、资源、文件夹的关联信息
     * @param groupId
     * @param resourceId
     * @param folderId
     * @return
     */
    void removeResourceByGroupResourceFolder(@Param("groupId")Long groupId,
                                             @Param("resourceId")Long resourceId,
                                             @Param("folderId")Long folderId);
}
