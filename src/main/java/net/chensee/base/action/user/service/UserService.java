package net.chensee.base.action.user.service;

import net.chensee.base.action.folder.po.WritableFolderPo;
import net.chensee.base.action.user.po.UserPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

import java.util.List;
import java.util.Map;

/**
 * @Author : shibo
 * @Date : 2019/8/4 10:22
 */
public interface UserService {

    /**
     * 添加用户
     * @param userPo
     */
    BaseResponse addUser(UserPo userPo);

    /**
     * 修改用户
     * @param userPo
     */
    BaseResponse updateUser(UserPo userPo) throws Exception;

    /**
     * 删除用户
     * @param id
     */
    BaseResponse deleteUser(Long id) throws Exception;

    /**
     * 获取所有用户
     * @param pageSize
     * @param pageNumber
     * @return
     */
    ObjectResponse getAllUser(Integer pageSize, Long pageNumber);

    /**
     * 根据名称查询用户
     * @param name
     * @param pageSize
     * @param pageNumber
     * @return
     */
    ObjectResponse getByRealName(String name, Integer pageSize, Long pageNumber);

    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    ObjectResponse getById(Long id);

    /**
     * 给用户分配部门
     * @param userId
     * @param deptIds
     * @return
     */
    BaseResponse distributeDept(Long userId, String deptIds) throws Exception;

    /**
     * 获取用户所有部门
     * @param userId
     * @return
     */
    ObjectResponse getDeptsByUserId(Long userId);

    /**
     * 删除用户部门
     * @param userId
     * @param deptId
     * @return
     */
    BaseResponse deleteUserFromDept(Long userId, Long deptId) throws Exception;

    /**
     * 获取用户默认部门
     * @param userId
     * @return
     */
    ObjectResponse getUserDefaultDept(Long userId);

    /**
     * 修改用户部门默认值
     * @param userId
     * @param deptId
     * @return
     */
    BaseResponse updateUserToDeptDefault(Long userId, Long deptId) throws Exception;

    /**
     * 给用户分配角色
     * @param userId
     * @param roleIds
     * @return
     */
    BaseResponse distributeRole(Long userId, String roleIds) throws Exception;

    /**
     * 获取用户所有角色
     * @param userId
     * @return
     */
    ObjectResponse getRolesByUserId(Long userId);

    /**
     * 给用户分配用户组
     * @param userId
     * @param groupIds
     * @return
     */
    BaseResponse distributeUserGroup(Long userId, String groupIds) throws Exception;

    /**
     * 获取用户所有用户组
     * @param userId
     * @return
     */
    ObjectResponse getUserGroupsByUserId(Long userId);

    /**
     * 获取用户所有资源
     * @param userId
     * @return
     */
    ObjectResponse getResourcesByUser(Long userId);

    /**
     * 分配用户资源
     * @param userId
     * @param paramMap
     * @param oprType
     * @return
     */
    BaseResponse allotUserResource(Long userId, Map<Long, Map<Long,String>> paramMap, Integer oprType) throws Exception;

    /**
     * 获取用户拥有的role和userGroup的关联资源
     * @param userId
     * @return
     */
    ObjectResponse getRUGResources(Long userId);

    /**
     * 获取用户自身资源
     * @param userId
     * @return
     */
    ObjectResponse getUserUniqueResource(Long userId,Integer direct);

    /**
     * 设置用户可写入文件夹
     * @param writableFolderMap
     * @return
     */
    BaseResponse allocateUserWriteFolder(Map<Long,List<WritableFolderPo>> writableFolderMap);

    /**
     * 删除用户可写入文件夹
     * @param userId
     * @param folderId
     * @return
     */
    BaseResponse deleteUserWriteFolder(Long userId, Long folderId);

    /**
     * 获取用户可写入文件夹
     * @param userId
     * @return
     */
    ObjectResponse getUserWriteFolders(Long userId);

    /**
     * 获取用户指定资源、文件夹的排除字段
     * @param userId
     * @param resourceId
     * @param folderId
     * @param direct
     * @return
     */
    ObjectResponse getExcludeColumns(Long userId, Long resourceId, Long folderId, Long direct);

    /**
     * 获取用户指定资源、文件夹的排除字段
     * @param userId
     * @return
     */
    ObjectResponse getUserDefaultWriteFolders(Long userId);

    /**
     * 设置用户可写入文件夹
     * @param userId
     * @param folderIdList
     * @param isDefault
     * @return
     */
    BaseResponse addUserWriteFolders(Long userId, List<Long> folderIdList, Integer isDefault);

    /**
     * 获取所有用户可写入文件夹关联信息
     * @return
     */
    ObjectResponse getAllWriteFolders(Integer pageNumber, Integer pageSize);
}
