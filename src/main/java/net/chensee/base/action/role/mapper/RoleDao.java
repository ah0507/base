package net.chensee.base.action.role.mapper;

import net.chensee.base.action.role.po.RolePo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author : shibo
 * @date : 2019/6/12 15:28
 */
public interface RoleDao {

    /**
     * 添加角色
     * @param rolePo
     */
    void addRole(RolePo rolePo);

    /**
     * 修改角色
     * @param rolePo
     */
    void updateRole(RolePo rolePo);

    /**
     * 删除角色
     * @param id
     */
    void deleteRole(@Param("id") Long id);

    /**
     * 获取所有角色
     * @param
     * @return
     */
    List<RoleVo> getAllRole(@Param("currentFolders") Set<Long> currentFolders);

    /**
    * @Description: 根据ID获取角色
    * @Param: id
    * @return: RoleVo
    */
    RoleVo getRoleById(@Param("id") Long id);

    /**
    * @Description: 根据角色ID获取角色下所有用户
    * @Param: id
    * @return: List<UserVo>
    */
    List<UserVo> getUserByRoleId(@Param("id") Long id);

    /**
     * @Description: 根据角色ID获取角色下所有用户组
     * @Param: id
     * @return: List<UserGroupVo>
     */
    List<UserGroupVo> getUserGroupByRoleId(@Param("id") Long id);
    
    /** 
    * @Description: 给角色添加用户
    * @Param: userId,roleId,visualFolderId
    * @return: void
    */
    void addUserToRole(@Param("userId") Long userId,
                      @Param("roleId") Long roleId);
    
    /** 
    * @Description: 给角色添加资源 
    * @Param: userId,resourceId,visualFolderId
    * @return: void
    */
    void addResourceToRole(@Param("roleId") Long roleId,
                           @Param("resourceId") Long resourceId,
                           @Param("visualFolderId") Long visualFolderId,
                           @Param("excludeColumns") String excludeColumns);

    /**
    * @Description: 给角色添加用户组
    * @Param: roleId,userGroupId,visualFolderId
    * @return: void
    */
    void addUserGroupToRole(@Param("roleId") Long roleId,
                           @Param("groupId") Long groupId);

    /**
    * @Description: 删除角色内指定用户
    * @Param: roleId,userId
    * @return: void
    */
    void removeUserFromRole(@Param("userId") Long userId,
                            @Param("roleId") Long roleId);

    /**
     * @Description: 删除角色内指定用户组
     * @Param: roleId,groupId
     * @return: void
     */
    void removeUserGroupFromRole(@Param("groupId") Long groupId,
                                 @Param("roleId") Long roleId);

    /**
     * @Description: 删除角色内资源
     * @Param: roleId
     * @return: void
     */
    void removeResourceByRoleId(@Param("roleId") Long roleId);

    /**
     * 移除角色资源
     * @param roleId
     * @param resourceId
     */
    void removeResourceFromRole(@Param("roleId") Long roleId,
                                @Param("resourceId") Long resourceId);


    //List<ResourcePo> getResourcePoByRoleId(@Param("id") Long roleId);

    /**
     * 通过roleId获取资源ID的集合
     * @param roleId
     * @return
     */
    List<Long> getResourceIdsByRoleId(@Param("id") Long roleId);

    /**
     * 获取指定角色、资源、文件夹的资源数目
     * @param roleId
     * @param resourceId
     * @param folderId
     * @return
     */
    Integer getResourceByIds(@Param("roleId")Long roleId,
                             @Param("resourceId")Long resourceId,
                             @Param("folderId")Long folderId);

    /**
     * 更新指定角色、资源、文件夹、排除字段的资源
     * @param roleId
     * @param resourceId
     * @param folderId
     * @param excludeColumns
     */
    void updateResourceToRole(@Param("roleId")Long roleId,
                              @Param("resourceId")Long resourceId,
                              @Param("folderId")Long folderId,
                              @Param("excludeColumns")String excludeColumns);

    /**
     * 获取指定角色、资源、文件夹的排除字段
     * @param roleId
     * @param resourceId
     * @param folderId
     * @return
     */
    String getExcludeColumns(@Param("roleId")Long roleId,
                             @Param("resourceId")Long resourceId,
                             @Param("folderId")Long folderId);

    /**
     * 删除指定角色、资源、文件夹的关联信息
     * @param roleId
     * @param resourceId
     * @param folderId
     * @return
     */
    void removeResourceByRoleResourceFolder(@Param("roleId")Long roleId,
                                                  @Param("resourceId")Long resourceId,
                                                  @Param("folderId")Long folderId);
}
