package net.chensee.base.action.dept.business;

import net.chensee.base.action.dept.po.DeptPo;
import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/12 9:27
 */
public interface DeptBus {

    /**
     * 添加部门组织
     * @param deptPo
     * @return
     */
    void addDept(DeptPo deptPo);

    /**
     * 修改部门组织
     * @param deptPo
     * @return
     */
    void updateDept(DeptPo deptPo);

    /**
     * 删除部门组织
     * @param id
     * @return
     */
    void deleteDept(Long id);

    /**
     * 获取所有的部门组织
     * @return
     */
    List<DeptVo> getAllDept();

    /**
    * @Description: 根据ID获取部门
    * @Param: id
    * @return: ObjectResponse
    */
    DeptVo getDeptById(Long id);

    /**
    * @Description: 获取部门下所有用户
    * @Param: id
    * @return: ObjectResponse
    */
    List<UserVo> getUserByDeptId(Long id);

    /**
    * @Description: 给部门添加用户
    * @Param: userID,deptId,isDefault
    * @return: BaseResponse
    */
    void addUserToDept(String userIds,Long deptId);

    /**
     * 获取部门下所有子部门
     * @param deptId
     * @return
     */
    List<DeptVo> getChildrenDeptByDeptId(Long deptId);

    /**
     * 获取文件夹下所有子文件夹
     * @param folderId
     * @return
     */
    List<FolderVo> getChildrenFoldersByFolderId(Long folderId);

    /**
     * 根据部门获取角色
     * @param deptId
     * @return
     */
    List<RoleVo> getRolesByDeptId(Long deptId);

    /**
     * 根据部门获取用户组
     * @param deptId
     * @return
     */
    List<UserGroupVo> getGroupsByDeptId(Long deptId);
}
