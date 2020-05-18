package net.chensee.base.action.userGroup.business.impl;

import net.chensee.base.action.folder.mapper.FolderDao;
import net.chensee.base.action.resource.mapper.ResourceDao;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.business.UserGroupBus;
import net.chensee.base.action.userGroup.mapper.UserGroupDao;
import net.chensee.base.action.userGroup.mapper.UserGroupResourceDao;
import net.chensee.base.action.userGroup.po.UserGroupPo;
import net.chensee.base.action.userGroup.po.UserGroupResourcePo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import net.chensee.base.common.po.BaseInfoPo;
import net.chensee.base.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @description: 用户组接口实现
 * @author: wanghuan
 * @create: 2019-07-22 15:12
 **/
@Component
public class UserGroupBusImpl implements UserGroupBus {
    private Logger logger = LoggerFactory.getLogger(UserGroupBusImpl.class);

    @Resource
    private UserGroupDao userGroupDao;
    @Resource
    private UserGroupResourceDao userGroupResourceDao;
    @Resource
    private ResourceDao resourceDao;
    @Resource
    private FolderDao folderDao;

    @Override
    public void addUserGroup(UserGroupPo userGroupPo) {
        userGroupPo.setStatus(BaseInfoPo.Status_Able);
        userGroupDao.addUserGroup(userGroupPo);
    }

    @Override
    public void updateUserGroup(UserGroupPo userGroupPo) {
        userGroupDao.updateUserGroup(userGroupPo);
    }

    @Override
    public void deleteUserGroupById(Long id) {
        userGroupDao.deleteUserGroupById(id);
    }

    @Override
    public List<UserGroupVo> getAllUserGroup(String name, Long pageStart, Integer pageSize) {
        Set<Long> currentFolders = ResFolderUtils.getCurrentFolder();
        for (Long folderId : currentFolders) {
            logger.debug("-----------当前可视文件夹"+folderId);
        }
        return userGroupDao.getAllUserGroup(name, pageStart, pageSize, currentFolders);
    }

    @Override
    public Long getCount(String name) {
        return userGroupDao.getCount(name);
    }

    @Override
    public UserGroupVo getUserGroupById(Long id) {
        return userGroupDao.getUserGroupById(id);
    }

    @Override
    public List<UserVo> getUserByUserGroupId(Long id) {
        return userGroupDao.getUserByUserGroupId(id);
    }

    @Override
    public List<RoleVo> getRoleByUserGroupId(Long id) {
        return userGroupDao.getRoleByUserGroupId(id);
    }

    @Override
    public List<ResourceVo> getResourceByUserGroupId(Long id) {
        List<UserGroupResourcePo> resourceByUserGroupId = userGroupResourceDao.getResourceByUserGroupId(id);
        logger.debug("首次获得资源："+resourceByUserGroupId);
        List<ResourceVo> resourceVos = ResourceUtil.transferResourceVo(null,resourceByUserGroupId,null);
        logger.debug("处理后的资源："+resourceVos);
        return resourceVos;
    }

    @Override
    public void addUserToUserGroup(String userIds, Long groupId){
        //获取该groupId下所有User
        List<UserVo> userVos = userGroupDao.getUserByUserGroupId(groupId);
        List<Long> users = StringUtil.formatIds(userIds);
        Long userId = null;
        for (UserVo userVo : userVos) {
            userId = userVo.getId();
            //如果解析得到的userID中含有已在groupID组中的userID，则移除userID以避免重复添加
            //如果没有，则将已在groupID组中的user移除，因为它将不属于该组
            if (!users.contains(userId)) {
                userGroupDao.removeUserFromUserGroup(userId, groupId);
            } else {
                users.remove(userId);
            }
        }
        for (Long user : users) {
            userGroupDao.addUserToUserGroup(user, groupId);
        }
    }

    @Override
    public void addRoleToUserGroup(String roleIds, Long groupId){
        List<RoleVo> roleVos = userGroupDao.getRoleByUserGroupId(groupId);
        List<Long> roles = StringUtil.formatIds(roleIds);
        Long roleId = null;
        for (RoleVo roleVo : roleVos) {
            roleId = roleVo.getId();
            //  逻辑同addUserToUserGroup
            if (!roles.contains(roleId)) {
                userGroupDao.removeRoleFromUserGroup(roleId, groupId);
            } else {
                roles.remove(roleId);
            }
        }
        for (Long role : roles) {
            userGroupDao.addRoleToUserGroup(role, groupId);
        }
    }

    @Override
    public void allotResourceToUserGroup(Long groupId, Map<Long, Map<Long,String>> paramMap){
        //1、删除该用户组下所有关联资源
        userGroupDao.removeResourceByGroupId(groupId);

        //2、转换paramMap信息 并添加关联资源
        for (Map.Entry<Long,Map<Long,String>> entry : paramMap.entrySet()) {
            Long resourceId = entry.getKey();
            Map<Long, String> folderColumnsMap = entry.getValue();
            for (Map.Entry<Long,String> entry1 : folderColumnsMap.entrySet()) {
                Long folderId = entry1.getKey();
                String columns = entry1.getValue();
                userGroupDao.addResourceToUserGroup(groupId,resourceId,folderId,columns);
            }
        }
    }

    @Override
    public String getExcludeColumns(Long groupId, Long resourceId, Long folderId) {
        return userGroupDao.getExcludeColumns(groupId, resourceId, folderId);
    }

}
