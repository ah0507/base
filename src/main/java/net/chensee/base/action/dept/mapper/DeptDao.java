package net.chensee.base.action.dept.mapper;

import net.chensee.base.action.dept.po.DeptPo;
import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author : shibo
 * @date : 2019/6/12 9:26
 */
public interface DeptDao {

    /**
     * 添加组织部门
     * @param deptPo
     */
    void addDept(DeptPo deptPo);

    /**
     * 修改组织部门
     * @param deptPo
     */
    void updateDept(DeptPo deptPo);

    /**
     * 删除组织部门
     * @param id
     */
    void deleteDept(Long id);

    /**
     * 获取所有组织部门
     * @param currentFolders
     * @return
     */
    List<DeptVo> getAllDept(@Param("currentFolders") Set<Long> currentFolders);

    /**
    * @Description: 根据ID获取部门
    * @Param: id
    * @return: DeptVo
    */
    DeptVo getDeptById(@Param("id") Long id);
    
    /** 
    * @Description: 根据部门ID获取部门下所有用户
    * @Param: id
    * @return: List<UserVo>
    */
    List<UserVo> getUserByDeptId(@Param("id") Long id);

    /**
    * @Description: 给部门添加用户
    * @Param: userId,deptId
    * @return: void
    */
    void addUserToDept(@Param("userId") Long userId,
                      @Param("deptId") Long deptId);

    /**
    * @Description: 删除指定部门中的指定用户
    * @Param: userId,deptId
    * @return: void
    */
    void removeUserFromDept(@Param("userId") Long userId,
                            @Param("deptId") Long deptId);

    /**
     * 获取子部门
     * @param deptId
     * @return
     */
    List<DeptVo> getChildrenDepts(@Param("deptId") Long deptId);

    /**
     * 根据部门获取角色
     * @param deptId
     * @return
     */
    List<RoleVo> getRolesByDeptId(@Param("deptId") Long deptId);

    /**
     * 获取部门下所有用户组
     * @param deptId
     * @return
     */
    List<UserGroupVo> getUserGroupByDeptId(@Param("deptId") Long deptId);

    /**
     * 批量删除组织部门
     * @param deptIdList
     */
    void deleteBatchDept(@Param("deptIdList") List<Long> deptIdList);
}
