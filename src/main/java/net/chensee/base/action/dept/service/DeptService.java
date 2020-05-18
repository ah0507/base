package net.chensee.base.action.dept.service;

import net.chensee.base.action.dept.po.DeptPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

/**
 * @Author : shibo
 * @Date : 2019/8/4 9:53
 */
public interface DeptService {

    /**
     * 添加部门组织
     * @param deptPo
     * @return
     */
    ObjectResponse addDept(DeptPo deptPo);

    /**
     * 修改部门组织
     * @param deptPo
     * @return
     */
    BaseResponse updateDept(DeptPo deptPo);

    /**
     * 删除部门组织
     * @param id
     * @return
     */
    BaseResponse deleteDept(Long id);

    /**
     * 获取所有的部门组织
     * @return
     */
    ObjectResponse getAllDept();

    /**
     * @Description: 根据ID获取部门
     * @Param: id
     * @return: ObjectResponse
     */
    ObjectResponse getDeptById(Long id);

    /**
     * @Description: 获取部门下所有用户
     * @Param: id
     * @return: ObjectResponse
     */
    ObjectResponse getUserByDeptId(Long id);

    /**
     * @Description: 给部门添加用户
     * @Param: userID,deptId,isDefault
     * @return: BaseResponse
     */
    BaseResponse addUserToDept(String userIds,Long deptId) throws Exception;

    /**
     * 获取部门下所有子部门
     * @param deptId
     * @return
     */
    ObjectResponse getChildrenDeptByDeptId(Long deptId);

    /**
     * 根据部门获取角色
     * @param deptId
     * @return
     */
    ObjectResponse getRolesByDeptId(Long deptId);

    /**
     * 根据部门获取用户组
     * @param deptId
     * @return
     */
    ObjectResponse getGroupsByDeptId(Long deptId);
}
