package net.chensee.base.action.role.business;

import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.po.RolePo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;

import java.util.List;
import java.util.Map;

/**
 * @author : shibo
 * @date : 2019/6/12 15:30
 */
public interface RoleBus {

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
    void deleteRole(Long id);

    /**
     * 获取所有角色
     * @return
     */
    List<RoleVo> getAllRole();

    /** 
    * @Description: 根据ID获取角色
    * @Param: id
    * @return: ObjectResponse
    */
    RoleVo getRoleById(Long id);
    
    /** 
    * @Description: 获取角色下所有资源 
    * @Param: id
    * @return: ObjectResponse
    */
    List<ResourceVo> getResourceByRoleId(Long id);

    /**
     * @Description: 获取角色下所有用户
     * @Param: id
     * @return: ObjectResponse
     */
    List<UserVo> getUserByRoleId(Long id);

    /**
     * @Description: 获取角色下所有用户组
     * @Param: id
     * @return: ObjectRepsonse
     */
    List<UserGroupVo> getUserGroupByRoleId(Long id);

    /**
    * @Description: 给角色添加用户
    * @Param: userId,roleId,visualFolderId
    * @return: BaseResponse
    */
    void addUserToRole(String userIds,Long roleId);

    /**
    * @Description: 给角色分配资源
    * @Param: roleId,resourceId,visualFolderId
    * @return: BaseResponse
    */
    void allotResourceToRole(Long roleId, Map<Long, Map<Long,String>> paramMap);

    /**
    * @Description: 给角色添加用户组
    * @Param: roleId,groupId,visualFolderId
    * @return: BaseResponse
    */
    void addUserGroupToRole(String groupIds,Long roleId);

    String getExcludeColumns(Long roleId, Long resourceId, Long folderId);
}
