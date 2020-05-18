package net.chensee.base.action.userGroup.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.userGroup.po.UserGroupPo;
import net.chensee.base.action.userGroup.service.UserGroupService;
import net.chensee.base.annotation.CheckFolderAnnontation;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @description: 用户组Controller
 * @author: wanghuan
 * @create: 2019-07-22 15:11
 **/
@RestController
@RequestMapping("")
@Slf4j
public class UserGroupController {

    @Resource
    private UserGroupService userGroupService;

    @ApiOperation(value = "添加用户组")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/userGroup", method = RequestMethod.POST)
    public ObjectResponse addUserGroup(@RequestBody @Validated UserGroupPo userGroupPo) {
        return userGroupService.addUserGroup(userGroupPo);
    }

    @ApiOperation(value = "更新用户组")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/userGroup", method = RequestMethod.PUT)
    public BaseResponse updateUserGroup(@RequestBody @Validated UserGroupPo userGroupPo) {
        return userGroupService.updateUserGroup(userGroupPo);
    }

    @ApiOperation(value = "删除用户组")
    @RequestMapping(value = "/userGroup/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteUserGroupById(@PathVariable Long id) {
        if (id == null) {
            return BaseResponse.fail("用户组ID不能为空");
        }
        return userGroupService.deleteUserGroupById(id);
    }

    @ApiOperation(value = "获取所有用户组")
    @RequestMapping(value = "/userGroup", method = RequestMethod.GET)
    public ObjectResponse getAllUserGroup(@RequestParam(defaultValue = "16") Integer pageSize,
                                          @RequestParam(defaultValue = "1") Long pageNumber) {
        return userGroupService.getAllUserGroup(null, pageSize, pageNumber);
    }

    @ApiOperation(value = "根据用户组名称查询用户")
    @RequestMapping(value = "/userGroup/search/condition", method = RequestMethod.GET)
    public ObjectResponse getAllUserGroupByUnspecificName(String name,
                                                          @RequestParam(defaultValue = "16") Integer pageSize,
                                                          @RequestParam(defaultValue = "1") Long pageNumber) {
        return userGroupService.getAllUserGroup(name, pageSize, pageNumber);
    }

    @ApiOperation(value = "根据ID获取用户组")
    @RequestMapping(value = "/userGroup/{id}", method = RequestMethod.GET)
    public ObjectResponse getUserGroupById(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("用户组ID不能为空");
        }
        return userGroupService.getUserGroupById(id);
    }

    @ApiOperation(value = "根据用户组ID查找组内所有用户")
    @RequestMapping(value = "/userGroup/{id}/users", method = RequestMethod.GET)
    public ObjectResponse getUserByUserGroupId(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("用户组ID不能为空");
        }
        return userGroupService.getUserByUserGroupId(id);
    }

    @ApiOperation(value = "根据用户组ID查找组内所有资源")
    @RequestMapping(value = "/userGroup/{id}/resources", method = RequestMethod.GET)
    public ObjectResponse getResourceByUserGroupId(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("用户组ID不能为空");
        }
        return userGroupService.getResourceByUserGroupId(id);
    }

    @ApiOperation(value = "根据用户组ID查找组内所有角色")
    @RequestMapping(value = "/userGroup/{id}/roles", method = RequestMethod.GET)
    public ObjectResponse getRoleByUserGroupId(@PathVariable Long id) {
        if (id == null) {
            return ObjectResponse.fail("用户组ID不能为空");
        }
        return userGroupService.getRoleByUserGroupId(id);
    }

    @ApiOperation(value = "给用户组分配用户")
    @RequestMapping(value = "/userGroup/{groupId}/users", method = RequestMethod.PUT)
    public BaseResponse addUserToUserGroup(@PathVariable Long groupId, String userIds) throws Exception{
        if (groupId == null) {
            return BaseResponse.fail("用户组ID不能为空");
        }
        return userGroupService.addUserToUserGroup(userIds, groupId);
    }

    @ApiOperation(value = "给用户组分配角色")
    @RequestMapping(value = "/userGroup/{groupId}/roles", method = RequestMethod.PUT)
    public BaseResponse addRoleToUserGroup(@PathVariable Long groupId, String roleIds) throws Exception{
        if (groupId == null) {
            return BaseResponse.fail("用户组ID不能为空");
        }
        return userGroupService.addRoleToUserGroup(roleIds, groupId);
    }

    @ApiOperation(value = "给用户组分配资源")
    @RequestMapping(value = "/userGroup/{groupId}/resources", method = RequestMethod.PUT)
    public BaseResponse addResourceToUserGroup(@PathVariable Long groupId,
                                               @RequestBody Map<Long, Map<Long,String>> config) throws Exception{
        if (groupId == null) {
            return BaseResponse.fail("用户组ID不能为空");
        }
        return userGroupService.addResourceToUserGroup(groupId, config);
    }

    /*@ApiOperation(value = "获取指定用户、资源、文件夹的排除字段")
    @RequestMapping(value = "/userGroup/{groupId}/{resourceId}/{folderId}/excludeColumns", method = RequestMethod.GET)
    public ObjectResponse getExcludeColumns(@PathVariable Long groupId,
                                          @PathVariable Long resourceId,
                                          @PathVariable Long folderId) {
        if (groupId == null) {
            return ObjectResponse.fail("用户组ID不能为空");
        }
        if (resourceId == null) {
            return ObjectResponse.fail("资源ID不能为空");
        }
        if (folderId == null) {
            return ObjectResponse.fail("文件夹ID不能为空");
        }
        return userGroupService.getExcludeColumns(groupId, resourceId, folderId);
    }*/

}
