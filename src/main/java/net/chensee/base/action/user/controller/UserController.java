package net.chensee.base.action.user.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.folder.po.WritableFolderPo;
import net.chensee.base.action.user.po.UserPo;
import net.chensee.base.action.user.service.UserService;
import net.chensee.base.annotation.CheckFolderAnnontation;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : shibo
 * @date : 2019/6/11 18:08
 */
@RestController
@RequestMapping("")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "获取所有用户")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ObjectResponse getAllUser(@RequestParam(defaultValue = "16") Integer pageSize, @RequestParam(defaultValue = "1") Long pageNumber) {
        return userService.getAllUser(pageSize, pageNumber);
    }

    @ApiOperation(value = "根据名称查询用户")
    @RequestMapping(value = "/user/search/condition", method = RequestMethod.GET)
    public ObjectResponse getByRealName(String name, @RequestParam(defaultValue = "16") Integer pageSize, @RequestParam(defaultValue = "1") Long pageNumber) {
        return userService.getByRealName(name, pageSize, pageNumber);
    }

    @ApiOperation(value = "添加用户")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public BaseResponse addUser(@RequestBody @Validated UserPo userPo) {
        return userService.addUser(userPo);
    }

    @ApiOperation(value = "修改用户")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public BaseResponse updateUser(@RequestBody @Validated UserPo userPo) throws Exception{
        return userService.updateUser(userPo);
    }

    @ApiOperation(value = "通过ID查找用户")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ObjectResponse getById(@PathVariable Long id) {
        if(id == null) {
            return ObjectResponse.fail("id不能为空!");
        }
        return userService.getById(id);
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteUser(@PathVariable Long id) throws Exception{
        if(id == null) {
            return ObjectResponse.fail("id不能为空!");
        }
        return userService.deleteUser(id);
    }

    @ApiOperation(value = "给用户分配部门")
    @RequestMapping(value = "/user/{userId}/depts", method = RequestMethod.PUT)
    public BaseResponse distributeDept(@PathVariable Long userId, String deptIds) throws Exception{
        if(userId == null) {
            return BaseResponse.fail("userId不能为空!");
        }
        deptIds = deptIds == null ? "" : deptIds;
        return userService.distributeDept(userId, deptIds);
    }

    @ApiOperation(value = "获取用户所有部门")
    @RequestMapping(value = "/user/{userId}/depts", method = RequestMethod.GET)
    public ObjectResponse getDeptsByUserId(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不能为空!");
        }
        return userService.getDeptsByUserId(userId);
    }

    @ApiOperation(value = "删除用户的部门")
    @RequestMapping(value = "/user/{userId}/dept/{deptId}", method = RequestMethod.DELETE)
    public BaseResponse distributeDept(@PathVariable Long userId, @PathVariable Long deptId) throws Exception{
        if(userId == null || deptId == null) {
            return BaseResponse.fail("参数不能为空!");
        }
        return userService.deleteUserFromDept(userId, deptId);
    }

    @ApiOperation(value = "获取用户默认部门")
    @RequestMapping(value = "/user/{userId}/dept/default", method = RequestMethod.GET)
    public BaseResponse getUserDefaultDept(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不能为空!");
        }
        return userService.getUserDefaultDept(userId);
    }

    @ApiOperation(value = "修改用户默认部门")
    @RequestMapping(value = "/user/{userId}/dept/{deptId}/isDefault", method = RequestMethod.PUT)
    public BaseResponse updateUserToDeptDefault(@PathVariable Long userId, @PathVariable Long deptId) throws Exception{
        if(userId == null || deptId == null) {
            return ObjectResponse.fail("参数不能为空!");
        }
        return userService.updateUserToDeptDefault(userId, deptId);
    }

    @ApiOperation(value = "给用户分配角色")
    @RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.PUT)
    public BaseResponse distributeRole(@PathVariable Long userId, String roleIds) throws Exception{
        if(userId == null) {
            return BaseResponse.fail("userId不能为空!");
        }
        return userService.distributeRole(userId, roleIds);
    }

    @ApiOperation(value = "获取用户所有角色")
    @RequestMapping(value = "/user/{userId}/roles", method = RequestMethod.GET)
    public ObjectResponse getRolesByUserId(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不能为空!");
        }
        return userService.getRolesByUserId(userId);
    }

    @ApiOperation(value = "给用户分配用户组")
    @RequestMapping(value = "/user/{userId}/groups", method = RequestMethod.PUT)
    public BaseResponse distributeUserGroup(@PathVariable Long userId, String groupIds) throws Exception{
        if(userId == null) {
            return BaseResponse.fail("userId不能为空!");
        }
        return userService.distributeUserGroup(userId, groupIds);
    }

    @ApiOperation(value = "获取用户所有用户组")
    @RequestMapping(value = "/user/{userId}/groups", method = RequestMethod.GET)
    public ObjectResponse getUserGroupsByUserId(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不能为空!");
        }
        return userService.getUserGroupsByUserId(userId);
    }

    @ApiOperation(value = "获取用户所有资源")
    @RequestMapping(value = "/user/{userId}/resources", method = RequestMethod.GET)
    public ObjectResponse getResourcesByUser(@PathVariable Long userId) {
        return userService.getResourcesByUser(userId);
    }

    @ApiOperation(value = "获取用户指定资源、文件夹下的排除字段")
    @RequestMapping(value = "/user/{userId}/{resourceId}/{folderId}/{direct}/excludeColumns", method = RequestMethod.GET)
    public ObjectResponse getExcludeColumns(@PathVariable Long userId,
                                            @PathVariable Long resourceId,
                                            @PathVariable Long folderId,
                                            @PathVariable Long direct) {
        if (userId == null) {
            return ObjectResponse.fail("用户ID不能为空");
        }
        if (resourceId == null) {
            return ObjectResponse.fail("资源ID不能为空");
        }
        if (folderId == null) {
            return ObjectResponse.fail("文件夹ID不能为空");
        }
        if (direct == null) {
            return ObjectResponse.fail("指向标识ID不能为空");
        }
        return userService.getExcludeColumns(userId, resourceId, folderId, direct);
    }

    @ApiOperation(value = "分配用户资源")
    @RequestMapping(value = "/user/{userId}/{oprType}/resources", method = RequestMethod.PUT)
    public BaseResponse allotUserResource(@PathVariable Long userId,
                                          @PathVariable Integer oprType,
                                          @RequestBody Map<Long, Map<Long,String>> config) throws Exception{
        if(userId == null) {
            return BaseResponse.fail("userId不能为空!");
        }
        if(oprType == null) {
            return BaseResponse.fail("oprType不能为空!");
        }
        return userService.allotUserResource(userId, config, oprType);
    }

    @ApiOperation(value = "获取用户拥有的角色和用户组关联资源")
    @RequestMapping(value = "/user/{userId}/roleAndGroupResources", method = RequestMethod.GET)
    public ObjectResponse getRUGResource(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不能为空!");
        }
        return userService.getRUGResources(userId);
    }

    @ApiOperation(value = "获取用户独有的关联资源")
    @RequestMapping(value = "/user/{userId}/userUniqueResources/{direct}", method = RequestMethod.GET)
    public ObjectResponse getUserUniqueResource(@PathVariable Long userId,
                                                @PathVariable Integer direct) {
        if(userId == null) {
            return ObjectResponse.fail("userId不能为空!");
        }
        return userService.getUserUniqueResource(userId,direct);
    }

    @ApiOperation(value = "获取所有的用户可写入文件夹信息")
    @RequestMapping(value = "/user/writablefolders", method = RequestMethod.GET)
    public ObjectResponse getAllWriteFolders(@RequestParam Integer pageNumber,
                                             @RequestParam Integer pageSize) {
        return userService.getAllWriteFolders(pageNumber,pageSize);
    }

    @ApiOperation(value = "配置用户可写入文件夹")
    @RequestMapping(value = "/user/writablefolders", method = RequestMethod.PUT)
    public BaseResponse allocateUserWriteFolder(@RequestParam Map<Long,List<WritableFolderPo>> writableFolderMap) {
        return userService.allocateUserWriteFolder(writableFolderMap);
    }

    @ApiOperation(value = "删除单个用户的指定可写入文件夹")
    @RequestMapping(value = "/user/{userId}/folder/{folderId}", method = RequestMethod.DELETE)
    public BaseResponse deleteUserWriteFolder(@PathVariable Long userId, @PathVariable Long folderId) {
        if(userId == null || folderId == null) {
            return BaseResponse.fail("参数不可为空!");
        }
        return userService.deleteUserWriteFolder(userId, folderId);
    }


    @ApiOperation(value = "获取单个用户可写入文件夹")
    @RequestMapping(value = "/user/{userId}/writablefolder", method = RequestMethod.GET)
    public ObjectResponse getUserWriteFolders(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不可为空!");
        }
        return userService.getUserWriteFolders(userId);
    }

    @ApiOperation(value = "获取用户默认可写入文件夹")
    @RequestMapping(value = "/user/{userId}/defaultFolders", method = RequestMethod.GET)
    public ObjectResponse getUserDefaultWriteFolders(@PathVariable Long userId) {
        if(userId == null) {
            return ObjectResponse.fail("userId不可为空!");
        }
        return userService.getUserDefaultWriteFolders(userId);
    }

    @ApiOperation(value = "添加用户可写入文件夹列表")
    @RequestMapping(value = "/user/{userId}/writeFolder", method = RequestMethod.POST)
    public BaseResponse addUserWriteFolders(@PathVariable Long userId,
                                            @RequestParam List<Long> folderIdList,
                                            @RequestParam(defaultValue = "0") Integer isDefault) {
        if(userId == null || folderIdList == null || folderIdList.size() ==0) {
            return BaseResponse.fail("参数不可为空!");
        }
        return userService.addUserWriteFolders(userId, folderIdList, isDefault);
    }


}
