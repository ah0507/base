package net.chensee.base.action.dept.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.dept.po.DeptPo;
import net.chensee.base.action.dept.service.DeptService;
import net.chensee.base.annotation.CheckFolderAnnontation;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author : shibo
 * @date : 2019/6/12 10:47
 */
@RestController
@RequestMapping("")
@Slf4j
public class DeptController {

    @Resource
    private DeptService deptService;

    @ApiOperation(value = "获取所有部门组织")
    @RequestMapping(value = "/dept", method = RequestMethod.GET)
    public ObjectResponse getAllDept() {
        return deptService.getAllDept();
    }

    @ApiOperation(value = "添加部门组织")
    @RequestMapping(value = "/dept", method = RequestMethod.POST)
    @CheckFolderAnnontation(0)
    public ObjectResponse addDept(@RequestBody @Validated DeptPo deptPo) {
        return deptService.addDept(deptPo);
    }

    @ApiOperation(value = "修改部门组织")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/dept", method = RequestMethod.PUT)
    public BaseResponse updateDept(@RequestBody @Validated DeptPo deptPo) {
        return deptService.updateDept(deptPo);
    }

    @ApiOperation(value = "删除部门组织")
    @RequestMapping(value = "/dept/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteDept(@PathVariable Long id) {
        if (id == null) {
            return BaseResponse.fail("部门ID不能为空");
        }
        return deptService.deleteDept(id);
    }

    @ApiOperation(value = "根据ID获取部门")
    @RequestMapping(value = "/dept/{id}", method = RequestMethod.GET)
    public ObjectResponse getDeptById(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("部门ID不能为空");
        }
        return deptService.getDeptById(id);
    }

    @ApiOperation(value = "获取部门下所有用户")
    @RequestMapping(value = "/dept/{id}/users", method = RequestMethod.GET)
    public ObjectResponse getUserByDeptId(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("部门ID不能为空");
        }
        return deptService.getUserByDeptId(id);
    }

    @ApiOperation(value = "给部门添加用户")
    @RequestMapping(value = "/dept/{deptId}/users", method = RequestMethod.POST)
    public BaseResponse addUserToDept(@PathVariable Long deptId, String userIds) {
        if (deptId == null) {
            return BaseResponse.fail("deptId不能为空");
        }
        BaseResponse baseResponse;
        try {
            baseResponse = deptService.addUserToDept(userIds, deptId);
        } catch (Exception e) {
            e.printStackTrace();
            baseResponse = BaseResponse.fail("给部门添加用户失败");
        }
        return baseResponse;
    }

    @ApiOperation(value = "获取部门下的子部门")
    @RequestMapping(value = "/dept/{deptId}/children", method = RequestMethod.GET)
    public ObjectResponse getChildrenDeptByDeptId(@PathVariable Long deptId) {
        if (deptId == null) {
            return ObjectResponse.fail("deptId不能为空!");
        }
        return deptService.getChildrenDeptByDeptId(deptId);
    }

    @ApiOperation(value = "获取部门下所有角色")
    @RequestMapping(value = "/dept/{deptId}/roles", method = RequestMethod.GET)
    public ObjectResponse getRolesByDeptId(@PathVariable Long deptId) {
        if (deptId == null) {
            return ObjectResponse.fail("deptId不能为空");
        }
        return deptService.getRolesByDeptId(deptId);
    }

    @ApiOperation(value = "获取部门下所有用户组")
    @RequestMapping(value = "/dept/{deptId}/userGroups", method = RequestMethod.GET)
    public ObjectResponse getGroupsByDeptId(@PathVariable Long deptId) {
        if (deptId == null) {
            return ObjectResponse.fail("deptId不能为空");
        }
        return deptService.getGroupsByDeptId(deptId);
    }
}
