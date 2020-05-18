package net.chensee.base.action.role.business.impl;

import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.business.RoleBus;
import net.chensee.base.action.role.mapper.RoleDao;
import net.chensee.base.action.role.mapper.RoleResourceDao;
import net.chensee.base.action.role.po.RolePo;
import net.chensee.base.action.role.po.RoleResourcePo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.vo.UserVo;
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
 * @author : shibo
 * @date : 2019/6/12 15:31
 */
@Component
public class RoleBusImpl implements RoleBus {
    private static final Logger logger = LoggerFactory.getLogger(RolePo.class);

    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleResourceDao roleResourceDao;

    @Override
    public void addRole(RolePo rolePo) {
        rolePo.setStatus(BaseInfoPo.Status_Able);
        roleDao.addRole(rolePo);
    }

    @Override
    public void updateRole(RolePo rolePo) {
        roleDao.updateRole(rolePo);
    }

    @Override
    public void deleteRole(Long id) {
        roleDao.deleteRole(id);
    }

    @Override
    public List<RoleVo> getAllRole() {
        Set<Long> currentFolders = ResFolderUtils.getCurrentFolder();
        for (Long folderId : currentFolders) {
            logger.debug("-----------当前可视文件夹"+folderId);
        }
        return roleDao.getAllRole(currentFolders);
    }

    @Override
    public RoleVo getRoleById(Long id) {
        return roleDao.getRoleById(id);
    }

    @Override
    public List<ResourceVo> getResourceByRoleId(Long id) {

        List<RoleResourcePo> resourceByRoleId = roleResourceDao.getResourceByRoleId(id);
        logger.debug("首次获得资源："+resourceByRoleId);
        List<ResourceVo> vos = ResourceUtil.transferResourceVo(resourceByRoleId,null,null);
        logger.debug("处理过后的资源："+vos);
        return vos;
    }

    @Override
    public List<UserVo> getUserByRoleId(Long id) {
        return roleDao.getUserByRoleId(id);
    }

    @Override
    public List<UserGroupVo> getUserGroupByRoleId(Long id) {
        return roleDao.getUserGroupByRoleId(id);
    }

    @Override
    public void addUserToRole(String userIds, Long roleId){
        List<UserVo> userVoList = roleDao.getUserByRoleId(roleId);
        List<Long> userIdList = StringUtil.formatIds(userIds);
        Long userId = null;
        for (UserVo userVo : userVoList) {
            userId = userVo.getId();
            if (!userIdList.contains(userId)) {
                roleDao.removeUserFromRole(userId, roleId);
            } else {
                userIdList.remove(userId);
            }
        }
        for (Long user : userIdList) {
            roleDao.addUserToRole(user, roleId);
        }
    }

    @Override
    public void addUserGroupToRole(String groupIds, Long roleId){
        List<UserGroupVo> groupVoList = roleDao.getUserGroupByRoleId(roleId);
        List<Long> groupIdList = StringUtil.formatIds(groupIds);
        Long groupId = null;
        for (UserGroupVo userGroupVo : groupVoList) {
            groupId = userGroupVo.getId();
            if (!groupIdList.contains(groupId)) {
                roleDao.removeUserGroupFromRole(groupId, roleId);
            } else {
                groupIdList.remove(groupId);
            }
        }
        for (Long group : groupIdList) {
            roleDao.addUserGroupToRole(group, roleId);
        }
    }

    @Override
    public String getExcludeColumns(Long roleId, Long resourceId, Long folderId) {
        return roleDao.getExcludeColumns(roleId,resourceId,folderId);
    }

    @Override
    public void allotResourceToRole(Long roleId, Map<Long, Map<Long,String>> paramMap){

        //1、删除该角色下所有关联资源
        roleDao.removeResourceByRoleId(roleId);

        //2、转换paramMap信息 并添加关联资源
        for (Map.Entry<Long,Map<Long,String>> entry : paramMap.entrySet()) {
            Long resourceId = entry.getKey();
            Map<Long, String> folderColumnsMap = entry.getValue();
            for (Map.Entry<Long,String> entry1 : folderColumnsMap.entrySet()) {
                Long folderId = entry1.getKey();
                String columns = entry1.getValue();
                roleDao.addResourceToRole(roleId,resourceId,folderId,columns);
            }
        }

    }
}
