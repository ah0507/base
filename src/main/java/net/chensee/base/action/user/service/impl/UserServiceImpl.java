package net.chensee.base.action.user.service.impl;

import net.chensee.base.action.folder.po.WritableFolderPo;
import net.chensee.base.action.user.po.UserPo;
import net.chensee.base.action.user.business.UserBus;
import net.chensee.base.action.user.service.UserService;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author : shibo
 * @Date : 2019/8/4 10:22
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserPo.class);

    @Resource
    private UserBus userBus;

    @Override
    public BaseResponse addUser(UserPo userPo) {
        try {
            userBus.addUser(userPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加用户出现异常", e);
        }
        return BaseResponse.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateUser(UserPo userPo) throws Exception{
        try {
            userBus.updateUser(userPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改用户出现异常", e);
        }
        return BaseResponse.fail();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteUser(Long id) throws Exception{
        try {
            userBus.deleteUser(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除用户出现异常", e);
        }
        return BaseResponse.fail();
    }

    @Override
    public ObjectResponse getAllUser(Integer pageSize, Long pageNumber) {
        List<UserVo> userVos = userBus.getAllUser(pageSize * (pageNumber - 1), pageSize);
        //Long count = userBus.getCount(null);
        Long count = userBus.getUsersCount(null);
        Map map = new HashMap();
        map.put("data", userVos);
        map.put("total", count);
        return ObjectResponse.ok(map);
    }

    @Override
    public ObjectResponse getByRealName(String name, Integer pageSize, Long pageNumber) {
        List<UserVo> userVos = userBus.getByRealName(name, pageSize * (pageNumber - 1), pageSize);
        //Long count = userBus.getCount(name);
        Long count = userBus.getUsersCount(name);
        Map map = new HashMap();
        map.put("data", userVos);
        map.put("total", count);
        return ObjectResponse.ok(map);
    }

    @Override
    public ObjectResponse getById(Long id) {
        try {
            return ObjectResponse.ok(userBus.getById(id));
        } catch (Exception e) {
            logger.error("通过ID查询用户出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse distributeDept(Long userId, String deptIds) throws Exception{
        try {
            userBus.distributeDept(userId, deptIds);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("给用户分配部门出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getDeptsByUserId(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getDeptsByUserId(userId));
        } catch (Exception e) {
            logger.error("获取用户所有部门出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteUserFromDept(Long userId, Long deptId) throws Exception{
        try {
            userBus.deleteUserFromDept(userId, deptId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除用户部门出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getUserDefaultDept(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getUserDefaultDept(userId));
        } catch (Exception e) {
            logger.error("获取用户默认部门出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateUserToDeptDefault(Long userId, Long deptId) throws Exception{
        try {
            //移除原有默认部门
            userBus.updateUserToDeptDefault(userId, deptId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改默认部门出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse distributeRole(Long userId, String roleIds) throws Exception{
        try {
            userBus.distributeRole(userId, roleIds);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("给用户分配角色出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getRolesByUserId(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getRolesByUserId(userId));
        } catch (Exception e) {
            logger.error("获取用户所有角色出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse distributeUserGroup(Long userId, String groupIds) throws Exception{
        try {
            userBus.distributeUserGroup(userId, groupIds);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("给用户分配用户组出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getUserGroupsByUserId(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getUserGroupsByUserId(userId));
        } catch (Exception e) {
            logger.error("获取用户所有用户组出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getResourcesByUser(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getResourcesByUserId(userId));
        } catch (Exception e) {
            logger.error("获取用户所有资源出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse allotUserResource(Long userId, Map<Long, Map<Long,String>> paramMap, Integer oprType) throws Exception{
        try {
            userBus.allotUserResource(userId, paramMap, oprType);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("分配用户资源出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getRUGResources(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getRUGResources(userId));
        } catch (Exception e) {
            logger.error("获取用户拥有的role和userGroup的关联资源出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getUserUniqueResource(Long userId,Integer direct) {
        try {
            return ObjectResponse.ok(userBus.getUserUniqueResource(userId,direct));
        } catch (Exception e) {
            logger.error("获取用户自身资源出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse allocateUserWriteFolder(Map<Long,List<WritableFolderPo>> writableFolderMap) {
        try {
            userBus.allocateUserWriteFolder(writableFolderMap);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("配置用户写入文件夹出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteUserWriteFolder(Long userId, Long folderId) {
        try {
            userBus.deleteUserWriteFolder(userId, folderId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除用户写入文件夹出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getUserWriteFolders(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getUserWriteFolders(userId));
        } catch (Exception e) {
            logger.error("获取用户写入文件夹出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getExcludeColumns(Long userId, Long resourceId, Long folderId, Long direct) {
        try {
            return ObjectResponse.ok(userBus.getExcludeColumns(userId, resourceId, folderId, direct));
        } catch (Exception e) {
            logger.error("获取用户指定资源、文件夹的排除字段异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getUserDefaultWriteFolders(Long userId) {
        try {
            return ObjectResponse.ok(userBus.getDefaultWriteFolder(userId));
        } catch (Exception e) {
            logger.error("获取用户默认可写入文件夹异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse addUserWriteFolders(Long userId, List<Long> folderIdList, Integer isDefault) {
        try {
            userBus.addUserWriteFolders(userId, folderIdList, isDefault);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加用户写入文件夹出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getAllWriteFolders(Integer pageNumber, Integer pageSize) {
        try {
            return ObjectResponse.ok(userBus.getAllWriteFolders(pageNumber,pageSize));
        } catch (Exception e) {
            logger.error("获取所有用户可写入文件夹关联信息出现异常", e);
            return ObjectResponse.fail("获取所有用户可写入文件夹关联信息出现异常");
        }
    }
}
