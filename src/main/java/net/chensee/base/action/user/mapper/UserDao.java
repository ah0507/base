package net.chensee.base.action.user.mapper;

import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.folder.vo.WritableFolderVo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.po.UserPo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : shibo
 * @date : 2019/6/11 17:54
 */
public interface UserDao {

    /**
     * 添加用户
     * @param userPo
     */
    void addUser(UserPo userPo);

    /**
     * 修改用户
     * @param userPo
     */
    void updateUser(UserPo userPo);

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Long id);

    /**
     * 获取所有用户
     * @param pageStart
     * @param pageSize
     * @param list
     * @return
     */
    List<UserVo> getAllUser(@Param("pageStart") Long pageStart, @Param("pageSize") Integer pageSize, @Param("list") List<Long> list);

    /**
     * 获取所有用户
     * @return
     */
    List<UserVo> getAllUsers();

    /**
     * 获取所有用户的数量
     * @param name
     * @param list
     * @return
     */
    Long getUsersCount(@Param("name") String name, @Param("list") List<Long> list);

    /**
     * 根据名称查询用户
     * @param name
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<UserVo> getByRealName(@Param("name") String name, @Param("pageStart") Long pageStart, @Param("pageSize") Integer pageSize);

    /**
     * 获取数据条数
     * @return
     */
    Long getCount(@Param("name") String name);

    /**
     * 通过ID查找用户
     * @param id
     * @return
     */
    UserVo getById(@Param("id") Long id);

    /**
     * 获取用户所有部门
     * @param userId
     * @return
     */
    List<DeptVo> getDeptsByUserId(@Param("userId") Long userId);

    /**
     * 获取用户默认部门
     * @param userId
     * @return
     */
    DeptVo getUserDefaultDept(@Param("userId") Long userId);

    /**
     * 移除用户部门关联数据
     * @param userId
     * @param deptId
     */
    void removeUserToDept(@Param("userId") Long userId, @Param("deptId") Long deptId);

    /**
     * 添加用户部门关联数据
     * @param userId
     * @param deptId
     */
    void addUserToDept(@Param("userId") Long userId, @Param("deptId") Long deptId);

    /**
     * 移除现有默认部门
     * @param userId
     */
    void removeDefaultDept(@Param("userId") Long userId);

    /**
     * 修改用户部门默认值
     * @param userId
     * @param deptId
     */
    void updateDefaultDept(@Param("userId") Long userId, @Param("deptId") Long deptId);

    /**
     * 获取用户所有角色
     * @param userId
     * @return
     */
    List<RoleVo> getRolesByUserId(@Param("userId") Long userId);

    /**
     * 移除用户角色关联数据
     * @param userId
     * @param roleId
     */
    void removeUserToRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 添加用户角色关联数据
     * @param userId
     * @param roleId
     */
    void addUserToRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 获取用户所有用户组
     * @param userId
     * @return
     */
    List<UserGroupVo> getUserGroupsByUserId(@Param("userId") Long userId);

    /**
     * 移除用户与用户组关联数据
     * @param userId
     * @param userGroupId
     */
    void removeUserToUserGroup(@Param("userId") Long userId, @Param("userGroupId") Long userGroupId);

    /**
     * 添加用户与用户组关联数据
     * @param userId
     * @param userGroupId
     */
    void addUserToUserGroup(@Param("userId") Long userId, @Param("userGroupId") Long userGroupId);

    /**
     * 获取用户排除资源ID
     * @param userId
     * @return
     */
    List<Long> getExcludeResourceIds(@Param("userId") Long userId);

    List<Map<String,Integer>> getExcludeResources(@Param("userId") Long userId);
    /**
     * 删除用户特有资源
     * @param userId
     */
    void deleteUserToResource(@Param("userId") Long userId);

    /**
     * 添加用户独有资源
     * @param userId
     * @param resourceId
     * @param visualFolderId
     * @param direct
     */
    void addOwnResource(@Param("userId") Long userId,
                        @Param("resourceId") Long resourceId,
                        @Param("visualFolderId") Long visualFolderId,
                        @Param("direct") Integer direct,
                        @Param("excludeColumns") String excludeColumns,
                        @Param("includeColumns") String includeColumns);

    /**
     * 添加用户写入文件夹
     * @param userId
     * @param folderId
     * @param folderName
     * @param isDefault
     */
    void addUserWriteFolder(@Param("userId") Long userId, @Param("folderId") Long folderId,
                            @Param("folderName") String folderName, @Param("isDefault") Integer isDefault);

    /**
     * 删除用户写入文件夹
     * @param userId
     * @param folderId
     */
    void deleteUserWriteFolder(@Param("userId") Long userId, @Param("folderId") Long folderId);

    /**
     * 获取用户可写入文件夹
     * @param userId
     * @return
     */
    List<FolderVo> getUserWriteFolders(@Param("userId") Long userId);

    /**
     * 删除用户和组织关联关系
     * @param userId
     */
    void removeUserLinkDept(@Param("userId") Long userId);

    /**
     * 删除用户和角色关联关系
     * @param userId
     */
    void removeUserLinkRole(@Param("userId") Long userId);

    /**
     * 删除用户和用户组关联关系
     * @param userId
     */
    void removeUserLinkUserGroup(@Param("userId") Long userId);

    /**
     * 删除用户和资源关联关系
     * @param userId
     */
    void removeUserLinkResource(@Param("userId") Long userId);

    /**
     * 获取用户指定资源、文件夹的排除字段
     * @param userId
     * @param resourceId
     * @param folderId
     * @param direct
     * @return
     */
    String getExcludeColumns(@Param("userId") Long userId,
                             @Param("resourceId") Long resourceId,
                             @Param("folderId") Long folderId,
                             @Param("direct") Long direct);

    /**
     * 根据userId和oprType删除指定关联资源
     * @param userId
     */
    void removeExcludeResourceByUserOprType(@Param("userId") Long userId);

    /**
     * 根据userId和oprType删除指定关联资源
     * @param userId
     */
    void removeIncludeResourceByUserOprType(@Param("userId") Long userId);

    /**
     * 根据userID、resourceID和folderID删除指定关联资源
     * @param userId
     * @param resourceId
     * @param folderId
     */
    void removeResourceByUserResourceFolder(@Param("userId") Long userId,
                                            @Param("resourceId") Long resourceId,
                                            @Param("folderId") Long folderId);

    /**
     * 选中部门中是否包含用户
     * @param userId
     * @param deptId
     */
    int isExistUserInSelectDept(@Param("userId") Long userId,
                                @Param("deptId") Long deptId);

    /**
     * 获取当前用户默认可写入文件夹
     * @param userId
     */
    List<FolderVo> getDefaultWriteFolder(@Param("userId") Long userId);

    /**
     * 删除userId的所有可写入文件夹关联信息
     * @param userId
     */
    void deleteUserAllWriteFolder(Long userId);

    /**
     * 获取所有用户可写入文件夹的关联信息
     */
    List<WritableFolderVo> getAllWritableFolders(@Param("pageStart") Integer pageStart,
                                                 @Param("pageSize") Integer pageSize);

    /**
     * 获取当前用户默认可写入文件夹的数量
     * @param userId
     */
    Integer getCountIsDefault(Long userId);
}
