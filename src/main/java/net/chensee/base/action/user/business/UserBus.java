package net.chensee.base.action.user.business;

import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.folder.po.WritableFolderPo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.folder.vo.WritableFolderVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.po.UserPo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;

import java.util.List;
import java.util.Map;

/**
 * @author : shibo
 * @date : 2019/6/11 17:58
 */
public interface UserBus {

    /**
     * 添加用户
     * @param userPo
     */
    void addUser(UserPo userPo);

    /**
     * 修改用户
     * @param userPo
     */
    void updateUser(UserPo userPo) throws Exception;

    /**
     * 删除用户
     * @param id
     */
    void deleteUser(Long id) throws Exception;

    /**
     * 获取所有用户
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<UserVo> getAllUser(Long pageStart, Integer pageSize);

    /**
     * 获取用户的数量(包括模糊查询)
     * @param name
     * @return
     */
    Long getUsersCount(String name);

    /**
     * 查询数据数量
     * @param name
     * @return
     */
    Long getCount(String name);

    /**
     * 根据名称查询用户
     * @param name
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<UserVo> getByRealName(String name, Long pageStart, Integer pageSize);

    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    UserVo getById(Long id);

    /**
     * 给用户分配部门
     * @param userId
     * @param deptIds
     * @return
     */
    void distributeDept(Long userId, String deptIds);

    /**
     * 获取用户所有部门
     * @param userId
     * @return
     */
    List<DeptVo> getDeptsByUserId(Long userId);

    /**
     * 删除用户部门
     * @param userId
     * @param deptId
     * @return
     */
    void deleteUserFromDept(Long userId, Long deptId);

    /**
     * 获取用户默认部门
     * @param userId
     * @return
     */
    DeptVo getUserDefaultDept(Long userId);

    /**
     * 修改用户部门默认值
     * @param userId
     * @param deptId
     * @return
     */
    void updateUserToDeptDefault(Long userId, Long deptId);

    /**
     * 给用户分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    void distributeRole(Long userId, String roleIds);

    /**
     * 获取用户所有角色
     * @param userId
     * @return
     */
    List<RoleVo> getRolesByUserId(Long userId);

    /**
     * 给用户分配用户组
     * @param userId
     * @param groupIds
     * @return
     */
    void distributeUserGroup(Long userId, String groupIds);

    /**
     * 获取用户所有用户组
     * @param userId
     * @return
     */
    List<UserGroupVo> getUserGroupsByUserId(Long userId);

    /**
     * 获取用户所有资源
     * @param userId
     * @return
     */
    List<ResourceVo> getResourcesByUserId(Long userId);

    /**
     * 给用户分配资源
     * @param userId
     * @param paramMap
     * @param oprType
     * @return
     */
    void allotUserResource(Long userId, Map<Long, Map<Long,String>> paramMap, Integer oprType);

    /**
     * 获取用户拥有的role和userGroup的关联资源
     * @param userId
     * @return
     */
    List<ResourceVo> getRUGResources(Long userId);

    /**
     * 获取用户自身资源
     * @param userId
     * @return
     */
    List<ResourceVo> getUserUniqueResource(Long userId, Integer direct);

    /**
     * 设置用户可写入文件夹
     * @param userId
     * @param folderId
     * @param isDefault
     * @return
     */
    void addUserWriteFolder(Long userId, Long folderId, Integer isDefault);

    /**
     * 删除用户可写入文件夹
     * @param userId
     * @param folderId
     * @return
     */
    void deleteUserWriteFolder(Long userId, Long folderId);

    /**
     * 获取用户可写入文件夹
     * @param userId
     * @return
     */
    List<FolderVo> getUserWriteFolders(Long userId);

    /**
     * 获取用户指定资源、文件夹的排除字段
     *
     * @param userId
     * @param resourceId
     * @param folderId
     * @param direct
     * @return
     */
    String getExcludeColumns(Long userId, Long resourceId, Long folderId, Long direct);

    /**
     * 判断当前登录用户是否在所选的部门中
     * @param userId
     * @param deptId
     * @return
     */
    boolean isExistUserInSelectDept(Long userId,Long deptId);

    /**
     * 获取当前用户默认可写入文件夹
     * @param userId
     * @return
     */
    List<FolderVo> getDefaultWriteFolder(Long userId);

    /**
     * 设置用户可写入文件夹
     * @param userId
     * @param folderIdList
     * @param isDefault
     * @return
     */
    void addUserWriteFolders(Long userId, List<Long> folderIdList, Integer isDefault);

    /**
     * 配置用户可写入文件夹
     * @param writableFolderMap
     * @return
     */
    void allocateUserWriteFolder(Map<Long,List<WritableFolderPo>> writableFolderMap) throws Exception;

    /**
     * 获取所有用户可写入文件夹关联信息
     * @return
     */
    List<WritableFolderVo> getAllWriteFolders(Integer pageNumber, Integer pageSize);
}
