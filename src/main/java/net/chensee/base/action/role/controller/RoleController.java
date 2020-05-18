package net.chensee.base.action.role.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.role.po.RolePo;
import net.chensee.base.action.role.service.RoleService;
import net.chensee.base.annotation.CheckFolderAnnontation;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author : shibo
 * @date : 2019/6/12 15:57
 */
@RestController
@RequestMapping("")
@Slf4j
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation(value = "获取所有角色")
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public ObjectResponse getAllRole() {
        return roleService.getAllRole();
    }

    @ApiOperation(value = "添加角色")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/role", method = RequestMethod.POST)
    public ObjectResponse addRole(@RequestBody @Validated RolePo rolePo) {
        return roleService.addRole(rolePo);
    }

    @ApiOperation(value = "修改角色")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/role", method = RequestMethod.PUT)
    public BaseResponse updateRole(@RequestBody @Validated RolePo rolePo) {
        return roleService.updateRole(rolePo);
    }

    @ApiOperation(value = "删除角色")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteRole(@PathVariable Long id) {
        if (id == null) {
            return BaseResponse.fail("角色ID不能为空");
        }
        return roleService.deleteRole(id);
    }

    @ApiOperation(value = "根据ID获取角色")
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public ObjectResponse getRoleById(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("角色ID不能为空");
        }
        return roleService.getRoleById(id);
    }

    @ApiOperation(value = "根据角色ID获取资源")
    @RequestMapping(value = "/role/{roleId}/resources", method = RequestMethod.GET)
    public ObjectResponse getResourceByRoleId(@PathVariable Long roleId) {
        if (roleId == null) {
            return ObjectResponse.fail("角色ID不能为空");
        }
        return roleService.getResourceByRoleId(roleId);
    }

    @ApiOperation(value = "根据角色ID获取用户")
    @RequestMapping(value = "/role/{roleId}/users", method = RequestMethod.GET)
    public ObjectResponse getUserByRoleId(@PathVariable Long roleId) {
        if (roleId == null) {
            return ObjectResponse.fail("角色ID不能为空");
        }
        return roleService.getUserByRoleId(roleId);
    }

    @ApiOperation(value = "根据角色ID获取用户组")
    @RequestMapping(value = "/role/{roleId}/userGroups", method = RequestMethod.GET)
    public ObjectResponse getUserGroupByRoleId(@PathVariable Long roleId) {
        if (roleId == null) {
            return ObjectResponse.fail("角色ID不能为空");
        }
        return roleService.getUserGroupByRoleId(roleId);
    }

    @ApiOperation(value = "给角色分配用户")
    @RequestMapping(value = "/role/{roleId}/users", method = RequestMethod.PUT)
    public BaseResponse addUserToRole(@PathVariable Long roleId, String userIds) throws Exception{
        if (roleId == null) {
            return BaseResponse.fail("角色ID不能为空");
        }
        return roleService.addUserToRole(userIds, roleId);
    }

    @ApiOperation(value = "给角色分配用户组")
    @RequestMapping(value = "/role/{roleId}/userGroups", method = RequestMethod.PUT)
    public BaseResponse addUserGroupToRole(@PathVariable Long roleId, String groupIds) throws Exception{
        if (roleId == null) {
            return BaseResponse.fail("角色ID不能为空");
        }
        return roleService.addUserGroupToRole(groupIds, roleId);
    }

    @ApiOperation(value = "给角色分配资源")
    @RequestMapping(value = "/role/{roleId}/resources", method = RequestMethod.PUT)
    public BaseResponse addResourceToRole(@PathVariable(value = "roleId") Long roleId, @RequestBody Map<Long, Map<Long,String>> config) throws Exception{
        if (roleId == null) {
            return BaseResponse.fail("角色ID不能为空");
        }
        return roleService.addResourceToRole(roleId, config);
    }

}
