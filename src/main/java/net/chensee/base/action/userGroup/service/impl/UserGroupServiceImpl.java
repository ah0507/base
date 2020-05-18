package net.chensee.base.action.userGroup.service.impl;

import net.chensee.base.action.userGroup.business.UserGroupBus;
import net.chensee.base.action.userGroup.service.UserGroupService;
import net.chensee.base.action.userGroup.po.UserGroupPo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : shibo
 * @Date : 2019/8/2 12:55
 */
@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {
    private Logger logger = LoggerFactory.getLogger(UserGroupPo.class);

    @Resource
    private UserGroupBus userGroupBus;

    @Override
    public ObjectResponse addUserGroup(UserGroupPo userGroupPo) {
        try {
            userGroupBus.addUserGroup(userGroupPo);
            return ObjectResponse.ok(userGroupPo.getId());
        } catch (Exception e) {
            logger.error("添加用户组异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse updateUserGroup(UserGroupPo userGroupPo) {
        try {
            userGroupBus.updateUserGroup(userGroupPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("更新用户组异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteUserGroupById(Long id) {
        try {
            userGroupBus.deleteUserGroupById(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除用户组异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getAllUserGroup(String name, Integer pageSize, Long pageNumber) {
        List<UserGroupVo> userGroupVos = userGroupBus.getAllUserGroup(name, pageSize * (pageNumber - 1), pageSize);
        Long count = userGroupBus.getCount(name);
        Map map = new HashMap();
        map.put("data", userGroupVos);
        map.put("total", count);
        return ObjectResponse.ok(map);
    }

    @Override
    public ObjectResponse getUserGroupById(Long id) {
        return ObjectResponse.ok(userGroupBus.getUserGroupById(id));
    }

    @Override
    public ObjectResponse getUserByUserGroupId(Long id) {
        return ObjectResponse.ok(userGroupBus.getUserByUserGroupId(id));
    }

    @Override
    public ObjectResponse getRoleByUserGroupId(Long id) {
        return ObjectResponse.ok(userGroupBus.getRoleByUserGroupId(id));
    }

    @Override
    public ObjectResponse getResourceByUserGroupId(Long id) {
        return ObjectResponse.ok(userGroupBus.getResourceByUserGroupId(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addUserToUserGroup(String userIds, Long groupId) throws Exception{
        try {
            userGroupBus.addUserToUserGroup(userIds, groupId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加用户到用户组异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addRoleToUserGroup(String roleIds, Long groupId) throws Exception{
        try {
            userGroupBus.addRoleToUserGroup(roleIds, groupId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加角色到用户组异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addResourceToUserGroup(Long groupId, Map<Long, Map<Long,String>> paramMap) throws Exception{
        try {
            userGroupBus.allotResourceToUserGroup(groupId, paramMap);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加资源到用户组异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getExcludeColumns(Long groupId, Long resourceId, Long folderId) {
        try {
            return ObjectResponse.ok(userGroupBus.getExcludeColumns(groupId, resourceId, folderId));
        } catch (Exception e) {
            logger.error("获取指定用户组、资源、文件夹的排除字段异常", e);
            return ObjectResponse.fail();
        }
    }
}
