package net.chensee.base.action.user.business.impl;

import net.chensee.base.action.dept.mapper.DeptDao;
import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.folder.business.FolderBus;
import net.chensee.base.action.folder.mapper.FolderDao;
import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.action.folder.po.WritableFolderPo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.folder.vo.WritableFolderVo;
import net.chensee.base.action.login.mapper.LoginDao;
import net.chensee.base.action.resource.mapper.ResourceDao;
import net.chensee.base.action.resource.vo.FolderAndColumnsVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.role.mapper.RoleDao;
import net.chensee.base.action.role.mapper.RoleResourceDao;
import net.chensee.base.action.role.po.RoleResourcePo;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.business.UserBus;
import net.chensee.base.action.user.mapper.UserDao;
import net.chensee.base.action.user.mapper.UserResourceDao;
import net.chensee.base.action.user.po.UserPo;
import net.chensee.base.action.user.po.UserResourcePo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.mapper.UserGroupDao;
import net.chensee.base.action.userGroup.mapper.UserGroupResourceDao;
import net.chensee.base.action.userGroup.po.UserGroupResourcePo;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import net.chensee.base.common.po.BaseInfoPo;
import net.chensee.base.utils.ResFolderUtils;
import net.chensee.base.utils.ResourceUtil;
import net.chensee.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : shibo
 * @date : 2019/6/11 17:58
 */
@Component
public class UserBusImpl implements UserBus {
    private static final Logger logger = LoggerFactory.getLogger(UserPo.class);

    @Resource
    private UserDao userDao;
    @Resource
    private UserResourceDao userResourceDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private RoleResourceDao roleResourceDao;
    @Resource
    private FolderDao folderDao;
    @Resource
    private UserGroupDao userGroupDao;
    @Resource
    private UserGroupResourceDao userGroupResourceDao;
    @Resource
    private ResourceDao resourceDao;
    @Resource
    private FolderBus folderBus;
    @Resource
    private LoginDao loginDao;
    @Resource
    private DeptDao deptDao;
    @Resource
    private UserBus userBus;

    @Override
    public void addUser(UserPo userPo) {
        userPo.setStatus(BaseInfoPo.Status_Able);
        userDao.addUser(userPo);
    }

    @Override
    public void updateUser(UserPo userPo) throws Exception{
        userDao.updateUser(userPo);
        //修改用户对应所有文件夹的名称
        List<FolderVo> folderVos = folderDao.getByBusiness("user", userPo.getId().toString());
        FolderPo folderPo = null;
        if (folderVos != null && folderVos.size() > 0) {
            for (FolderVo folderVo : folderVos) {
                folderPo = new FolderPo();
                BeanUtils.copyProperties(folderVo, folderPo);
                folderPo.setName(userPo.getRealName());
                folderBus.updateFolder(folderPo);
            }
        }
    }

    @Override
    public void deleteUser(Long id) throws Exception{
        userDao.deleteUser(id);
        //删除用户登录方式
        loginDao.deleteByUserId(id);
        //删除用户对应的文件夹
        List<FolderVo> folderVos = folderDao.getByBusiness("user", id.toString());
        if (folderVos != null && folderVos.size() > 0) {
            for (FolderVo folderVo : folderVos) {
                folderBus.deleteFolder(folderVo.getId());
            }
        }
        //删除用户和组织、角色、用户组、资源之间的关联关系
        userDao.removeUserLinkDept(id);
        userDao.removeUserLinkRole(id);
        userDao.removeUserLinkUserGroup(id);
        userDao.removeUserLinkResource(id);
    }

    @Override
    public List<UserVo> getAllUser(Long pageStart, Integer pageSize) {
        /*List<Long> visualFolderIds = getVisualFolderIds();
        //如果无可视文件夹，则返回空list
        if(visualFolderIds.size() == 0) {
            return new ArrayList<>();
        }*/
        Set<Long> currentFolders = ResFolderUtils.getCurrentFolder();
        if(currentFolders.size() == 0) {
            return new ArrayList<>();
        }
        List<Long> visualFolderIds = new ArrayList<>();
        for (Long folderId : currentFolders) {
            logger.debug("-----------当前可视文件夹"+folderId);
            visualFolderIds.add(folderId);
        }
        return userDao.getAllUser(pageStart, pageSize, visualFolderIds);
    }

    @Override
    public Long getUsersCount(String name) {
        /*List<Long> visualFolderIds = getVisualFolderIds();
        //如果无可视文件夹，则返回空list
        if(visualFolderIds.size() == 0) {
            return 0L;
        }*/
        Set<Long> currentFolders = ResFolderUtils.getCurrentFolder();
        List<Long> visualFolderIds = new ArrayList<>();
        if (currentFolders.size() == 0) {
            return 0L;
        }
        for (Long folderId : currentFolders) {
            logger.debug("-----------当前可视文件夹"+folderId);
            visualFolderIds.add(folderId);
        }
        return userDao.getUsersCount(name,visualFolderIds);
    }

    private List<Long> getVisualFolderIds() {
        Long userId = 1L;
        Long resourceId = 2L;
        List<ResourceVo> resourceVos = userBus.getResourcesByUserId(userId);
        List<Long> visualFolderIds = new ArrayList<>();
        if (resourceVos != null && resourceVos.size() > 0) {
            for (ResourceVo resourceVo : resourceVos) {
                if (resourceVo.getId().equals(resourceId)) {
                    List<FolderAndColumnsVo> folderExcludeColumns = resourceVo.getFolderAndColumns();
                    for (FolderAndColumnsVo folderAndColumnsVo : folderExcludeColumns) {
                        Long folder = folderAndColumnsVo.getFolder();
                        if (!visualFolderIds.contains(folder)) {
                            visualFolderIds.add(folder);
                        }
                    }
                }
            }
        }
        return visualFolderIds;
    }

    @Override
    public Long getCount(String name) {
        return userDao.getCount(name);
    }

    @Override
    public List<UserVo> getByRealName(String name, Long pageStart, Integer pageSize) {
        return userDao.getByRealName(name, pageStart, pageSize);
    }

    @Override
    public UserVo getById(Long id) {
        return userDao.getById(id);
    }

    @Override
    public void distributeDept(Long userId, String deptIds){
        UserVo userVo = userDao.getById(userId);
        List<DeptVo> deptVos = userDao.getDeptsByUserId(userId);
        List<Long> depts = StringUtil.formatIds(deptIds);
        FolderPo folderPo = null;
        for (DeptVo deptVo : deptVos) {
            if (!depts.contains(deptVo.getId())) {
                userDao.removeUserToDept(userId, deptVo.getId());
                //删除用户与该部门下的用户组、角色的关联关系
                List<RoleVo> roleVos = deptDao.getRolesByDeptId(deptVo.getId());
                if (roleVos != null && roleVos.size() > 0) {
                    for (RoleVo roleVo : roleVos) {
                        roleDao.removeUserFromRole(userId, roleVo.getId());
                    }
                }
                List<UserGroupVo> userGroupVos = deptDao.getUserGroupByDeptId(deptVo.getId());
                if (userGroupVos != null && userGroupVos.size() > 0) {
                    for (UserGroupVo userGroupVo : userGroupVos) {
                        userGroupDao.removeUserFromUserGroup(userId, userGroupVo.getId());
                    }
                }
                //将该部门业务文件夹下的该用户业务文件夹状态设为1  禁用
                List<FolderVo> folderVos = folderDao.getUserFolderByDeptId(deptVo.getId().toString());
                for (FolderVo folderVo : folderVos) {
                    if (folderVo.getBusiness().equals("user") && folderVo.getBusinessId().equals(userId)) {
                        folderPo = new FolderPo();
                        BeanUtils.copyProperties(folderVo, folderPo);
                        folderPo.setStatus(BaseInfoPo.Status_Disable);
                        folderDao.updateFolder(folderPo);
                        break;
                    }
                }
            } else {
                depts.remove(deptVo.getId());
            }
        }
        for (Long dept : depts) {
            userDao.addUserToDept(userId, dept);
            folderPo = new FolderPo();
            //查看该部门业务文件夹下是否有该用户文件夹，如果有，则将其状态改为可用，如果没有，则新建一个用户业务文件夹添加到部门文件夹下
            List<FolderVo> folderVos = folderDao.getUserFolderByDeptId(dept.toString());
            List<FolderVo> deptFolder = folderDao.getByBusiness("dept", dept.toString());
            boolean flag = false;
            for (FolderVo folderVo : folderVos) {
                if (folderVo.getBusiness().equals("user") && folderVo.getBusinessId().equals(userId)) {
                    flag = true;
                    BeanUtils.copyProperties(folderVo, folderPo);
                    folderPo.setStatus(BaseInfoPo.Status_Able);
                    folderDao.updateFolder(folderPo);
                    break;
                }
            }
            if (!flag) {
                folderPo.setType("auto");
                folderPo.setName(userVo.getRealName());
                folderPo.setBusiness("user");
                folderPo.setIsLeaf(1);
                folderPo.setBusinessId(userId.toString());
                if (deptFolder != null && deptFolder.size() != 0) {
                    folderPo.setRank(deptFolder.get(0).getRank() + 1);
                }
                folderPo.setParentId(deptFolder.get(0).getId());
                folderPo.setStatus(BaseInfoPo.Status_Able);
                folderDao.addFolder(folderPo);
            }
        }
    }

    @Override
    public List<DeptVo> getDeptsByUserId(Long userId) {
        return userDao.getDeptsByUserId(userId);
    }

    @Override
    public void deleteUserFromDept(Long userId, Long deptId){
        userDao.removeUserToDept(userId, deptId);
        //删除用户与该部门下的用户组、角色的关联关系
        List<RoleVo> roleVos = deptDao.getRolesByDeptId(deptId);
        if (roleVos != null && roleVos.size() > 0) {
            for (RoleVo roleVo : roleVos) {
                roleDao.removeUserFromRole(userId, roleVo.getId());
            }
        }
        List<UserGroupVo> userGroupVos = deptDao.getUserGroupByDeptId(deptId);
        if (userGroupVos != null && userGroupVos.size() > 0) {
            for (UserGroupVo userGroupVo : userGroupVos) {
                userGroupDao.removeUserFromUserGroup(userId, userGroupVo.getId());
            }
        }
        //将该部门业务文件夹下的该用户业务文件夹状态设为1   禁用
        List<FolderVo> folderVos = folderDao.getUserFolderByDeptId(deptId.toString());
        FolderPo folderPo = null;
        for (FolderVo folderVo : folderVos) {
            if (folderVo.getBusiness().equals("user") && folderVo.getBusinessId().equals(userId)) {
                folderPo = new FolderPo();
                BeanUtils.copyProperties(folderVo, folderPo);
                folderPo.setStatus(BaseInfoPo.Status_Disable);
                folderDao.updateFolder(folderPo);
                break;
            }
        }
    }

    @Override
    public DeptVo getUserDefaultDept(Long userId) {
        return userDao.getUserDefaultDept(userId);
    }

    @Override
    public void updateUserToDeptDefault(Long userId, Long deptId){
        //移除原有默认部门
        userDao.removeDefaultDept(userId);
        //修改用户部门默认值
        userDao.updateDefaultDept(userId, deptId);
    }

    @Override
    public void distributeRole(Long userId, String roleIds){
        List<RoleVo> roleVos = userDao.getRolesByUserId(userId);
        List<Long> roles = StringUtil.formatIds(roleIds);
        Long roleId = null;
        for (RoleVo roleVo : roleVos) {
            roleId = roleVo.getId();
            if (!roles.contains(roleId)) {
                userDao.removeUserToRole(userId, roleId);
            } else {
                roles.remove(roleId);
            }
        }
        for (Long role : roles) {
            userDao.addUserToRole(userId, role);
        }
    }

    @Override
    public List<RoleVo> getRolesByUserId(Long userId) {
        return userDao.getRolesByUserId(userId);
    }

    @Override
    public void distributeUserGroup(Long userId, String groupIds){
        List<UserGroupVo> userGroupVos = userDao.getUserGroupsByUserId(userId);
        List<Long> groups = StringUtil.formatIds(groupIds);
        Long userGroupId = null;
        for (UserGroupVo userGroup : userGroupVos) {
            userGroupId = userGroup.getId();
            if (!groups.contains(userGroupId)) {
                userDao.removeUserToUserGroup(userId, userGroupId);
            } else {
                groups.remove(userGroupId);
            }
        }
        for (Long group : groups) {
            userDao.addUserToUserGroup(userId, group);
        }
    }

    @Override
    public List<UserGroupVo> getUserGroupsByUserId(Long userId) {
        return userDao.getUserGroupsByUserId(userId);
    }

    @Override
    public List<ResourceVo> getResourcesByUserId(Long userId) {
        //获取用户所有相关联的角色对应的资源
        List<RoleResourcePo> roleResourcePos = roleResourceDao.getRoleResourceByUserId(userId);

        //获取用户所有相关联的所有组对应的资源
        List<UserGroupResourcePo> userGroupResourcePos = userGroupResourceDao.getUserGroupResourceByUserId(userId);

        //获取用户自身关联的资源
        List<UserResourcePo> userResourcePos = userResourceDao.getUserUniqueResourcesByUserId(userId);

        List<ResourceVo> allResourceVos = ResourceUtil.transferResourceVo(roleResourcePos, userGroupResourcePos, userResourcePos);
        return ResourceUtil.mergeResourceVo(allResourceVos);
    }

    /**
     *
     * @param userId
     * @param paramMap  resourceId, folderId, columns
     * @param oprType  -1 排除 (direct < 0)  1 包含 (direct > 0)
     */
    @Override
    public void allotUserResource(Long userId, Map<Long, Map<Long,String>> paramMap, Integer oprType){
        // 删除  第一步 userId 和 direct 删除
        if (oprType > 0) {
            userDao.removeIncludeResourceByUserOprType(userId);
        }else if (oprType < 0) {
            userDao.removeExcludeResourceByUserOprType(userId);
        }else {
            new Exception("方向参数不符合规则");
        }
        // 删除  第二步 userId, resourceId, folderId 删除
        // insert into
        for (Map.Entry<Long, Map<Long,String>> entry : paramMap.entrySet()) {
            Long resourceId = entry.getKey();
            Map<Long, String> folderIdColumnsMap = entry.getValue();
            if (folderIdColumnsMap.isEmpty()) {
                userDao.addOwnResource(userId,resourceId,1L,-1,null,null);
            }
            for (Map.Entry<Long, String> entry1 : folderIdColumnsMap.entrySet()) {
                Long folderId = entry1.getKey();
                userDao.removeResourceByUserResourceFolder(userId,resourceId,folderId);
                String columns = entry1.getValue();
                if (oprType < 0) {
                    //如果是oprType<1，则为排除 判断是排除资源还是排除文件夹
                    if (resourceId != null && folderId == null && columns == null) {
                        //则为排除资源
                        userDao.addOwnResource(userId,resourceId,folderId,-1,columns,null);
                    }else if (resourceId != null && folderId != null && columns == null) {
                        //则为排除文件夹
                        userDao.addOwnResource(userId,resourceId,folderId,-2,columns,null);
                    }else if (resourceId != null && folderId != null && columns != null) {
                        //则为排除字段
                        userDao.addOwnResource(userId,resourceId,folderId,-3,columns,null);
                    }else {
                        new Exception("所传的关联资源参数不符合规则");
                    }
                }else if (oprType > 0) {
                    userDao.addOwnResource(userId,resourceId,folderId,1,null,columns);
                }else {
                    new Exception("所传的关联资源参数不符合规则");
                }
            }
        }
    }

    /**
     * 获取用户拥有的role和userGroup的关联资源
     * @param userId
     * @return
     */
    public List<ResourceVo> getRUGResources(Long userId) {

        List<RoleResourcePo> roleResourceByUserId = roleResourceDao.getRoleResourceByUserId(userId);
        List<UserGroupResourcePo> userGroupResourceByUserId = userGroupResourceDao.getUserGroupResourceByUserId(userId);

        List<ResourceVo> resourceVos = ResourceUtil.transferResourceVo(roleResourceByUserId, userGroupResourceByUserId, null);
        return ResourceUtil.mergeResourceVo(resourceVos);
    }

    /**
     * 获取用户自身资源 ---- 对应 user_resource 表
     * @param userId
     * @return
     */
    public List<ResourceVo> getUserUniqueResource(Long userId, Integer direct) {
        List<UserResourcePo> userResourceByUserId = new ArrayList<>();
        if (direct > 0) {
            userResourceByUserId = userResourceDao.getUserIncludeResourcesByUserId(userId);
        }else if (direct < 0) {
            //获取所有direct<0的排除资源
            userResourceByUserId = userResourceDao.getUserExcludeResourcesByUserId(userId);
        }else {
            new Exception("资源指向参数不符合规则");
        }
        List<ResourceVo> resourceVos = ResourceUtil.transferResourceVo(null, null, userResourceByUserId);
        return ResourceUtil.mergeResourceVo(resourceVos);
    }

    @Override
    public void addUserWriteFolder(Long userId, Long folderId, Integer isDefault) {
        FolderVo folderVo = folderDao.getById(folderId);
        String folderName = folderVo.getName();
        userDao.addUserWriteFolder(userId, folderId, folderName, isDefault);
    }

    @Override
    public void deleteUserWriteFolder(Long userId, Long folderId) {
        userDao.deleteUserWriteFolder(userId, folderId);
    }

    @Override
    public List<FolderVo> getUserWriteFolders(Long userId) {
        return userDao.getUserWriteFolders(userId);
    }

    @Override
    public String getExcludeColumns(Long userId, Long resourceId, Long folderId, Long direct) {
        return userDao.getExcludeColumns(userId, resourceId, folderId ,direct);
    }

    @Override
    public boolean isExistUserInSelectDept(Long userId, Long deptId) {
        return userDao.isExistUserInSelectDept(userId, deptId) == 0 ? false : true;
    }

    @Override
    public List<FolderVo> getDefaultWriteFolder(Long userId) {
        return userDao.getDefaultWriteFolder(userId);
    }

    @Override
    public void addUserWriteFolders(Long userId, List<Long> folderIdList, Integer isDefault) {
        for (Long folderId : folderIdList) {
            FolderVo folderVo = folderDao.getById(folderId);
            String folderName = folderVo.getName();
            userDao.addUserWriteFolder(userId, folderId, folderName, isDefault);
        }
    }

    @Override
    public void allocateUserWriteFolder(Map<Long,List<WritableFolderPo>> writableFolderMap) throws Exception {
        //先删除userId相关所有可写入文件夹
        Long userId;
        List<WritableFolderPo> writableFolderPoList;
        for (Map.Entry<Long,List<WritableFolderPo>> entry : writableFolderMap.entrySet()) {
            userId = entry.getKey();
            writableFolderPoList = entry.getValue();
            userDao.deleteUserAllWriteFolder(userId);
            if (writableFolderPoList != null && writableFolderPoList.size() != 0) {
                for (WritableFolderPo writableFolderPo : writableFolderPoList) {
                    userDao.addUserWriteFolder(userId,writableFolderPo.getFolderId(),
                            writableFolderPo.getFolderName(),writableFolderPo.getIsDefault());
                }
            }

            if (userDao.getCountIsDefault(userId) != 1) {
                throw new Exception("每个用户必须有且只有一个默认可写入文件夹");
            }
        }

    }

    @Override
    public List<WritableFolderVo> getAllWriteFolders(Integer pageNumber, Integer pageSize) {
        return userDao.getAllWritableFolders(pageSize*(pageNumber-1),pageSize);
    }
}
