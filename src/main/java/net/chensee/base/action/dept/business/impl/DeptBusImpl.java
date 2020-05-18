package net.chensee.base.action.dept.business.impl;

import net.chensee.base.action.dept.mapper.DeptDao;
import net.chensee.base.action.dept.po.DeptPo;
import net.chensee.base.action.dept.business.DeptBus;
import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.folder.mapper.FolderDao;
import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.role.mapper.RoleDao;
import net.chensee.base.action.role.vo.RoleVo;
import net.chensee.base.action.user.mapper.UserDao;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.action.userGroup.mapper.UserGroupDao;
import net.chensee.base.action.userGroup.vo.UserGroupVo;
import net.chensee.base.common.po.BaseInfoPo;
import net.chensee.base.utils.ResFolderUtils;
import net.chensee.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author : shibo
 * @date : 2019/6/12 9:28
 */
@Component
public class DeptBusImpl implements DeptBus {
    private static final Logger logger = LoggerFactory.getLogger(DeptPo.class);

    @Resource
    private DeptDao deptDao;
    @Resource
    private FolderDao folderDao;
    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;
    @Resource
    private UserGroupDao userGroupDao;

    @Override
    public void addDept(DeptPo deptPo) {
        deptPo.setStatus(BaseInfoPo.Status_Able);
        deptDao.addDept(deptPo);
        FolderPo folderPo = new FolderPo();
        folderPo.setBusiness("dept");
        folderPo.setBusinessId(deptPo.getId().toString());
        folderPo.setType("auto");
        folderPo.setIsLeaf(0);
        folderPo.setName(deptPo.getShortName());
        Long deptParentId = deptPo.getParentId();
        if (deptParentId != null && deptParentId > 0L) {
            DeptVo deptVo = deptDao.getDeptById(deptParentId);
            if (deptVo != null) {
                List<FolderVo> folderVo = folderDao.getByBusiness(folderPo.getBusiness(), deptVo.getId().toString());
                if (folderVo != null) {
                    folderPo.setParentId(folderVo.get(0).getId());
                    folderPo.setRank(folderVo.get(0).getRank() + 1);
                }
            }
        }
        folderPo.setRank(folderPo.getRank() == null ? 1 : folderPo.getRank());
        folderPo.setStatus(BaseInfoPo.Status_Able);
        folderDao.addFolder(folderPo);
    }

    @Override
    public void updateDept(DeptPo deptPo) {
        //关于对应的文件夹分两种情况：如果没有变更上级，则只需要改变对应文件夹的数据；
        //如果变更了上级，则禁用原来的文件夹，新建一个文件夹变更上级。
        DeptVo deptVo = deptDao.getDeptById(deptPo.getId());
        List<FolderVo> folderVoList = folderDao.getByBusiness("dept", deptPo.getId().toString());
        FolderVo folderVo = folderVoList.get(0);
        FolderPo folderPo = new FolderPo();
        BeanUtils.copyProperties(folderVo, folderPo);
        folderPo.setName(deptPo.getShortName());
        if (deptVo.getParentId() == deptPo.getParentId()) {
            //没有变更上级
            folderDao.updateFolder(folderPo);
        }else {
            //变更了上级
            List<FolderVo> deptParent = folderDao.getByBusiness("dept", deptPo.getParentId().toString());
            FolderVo folderParent = deptParent.get(0);
            folderPo.setRank(folderParent.getRank()+1);
            folderDao.deleteFolder(folderVo.getId());
            folderPo.setStatus(BaseInfoPo.Status_Able);
            folderDao.addFolder(folderPo);
        }

        /*List<FolderVo> folderVo = folderDao.getByBusiness("dept", deptPo.getId().toString());
        FolderPo folderPo = new FolderPo();
        BeanUtils.copyProperties(folderVo.get(0), folderPo);
        folderPo.setBusinessId(deptPo.getId().toString());
        folderPo.setName(deptPo.getShortName());
        folderPo.setParentId((long) -1);
        folderPo.setRank(1);
        Long deptParentId = deptPo.getParentId();
        if (deptParentId != null && deptParentId > 0L) {
            DeptVo deptVo = deptDao.getDeptById(deptParentId);
            if (deptVo != null) {
                //即父级没变
                List<FolderVo> folderVo1 = folderDao.getByBusiness(folderPo.getBusiness(), deptVo.getId().toString());
                if (folderVo != null) {
                    folderPo.setParentId(folderVo1.get(0).getId());
                    folderPo.setRank(folderVo1.get(0).getRank() + 1);
                }
            }
        }*/
        //更新dept
        deptDao.updateDept(deptPo);

        // folderDao.updateFolder(folderPo);
    }

    @Override
    public void deleteDept(Long id) {
        //不止要删除部门，还要删除其下属部门
        List<DeptVo> childrenDepts = getChildrenDeptByDeptId(id);
        List<Long> deptIdList = new ArrayList<>();
        if (childrenDepts != null && childrenDepts.size() != 0) {
            for (DeptVo deptVo : childrenDepts) {
                deptIdList.add(deptVo.getId());
            }
        }
        if (deptIdList != null && deptIdList.size() != 0) {
            deptDao.deleteBatchDept(deptIdList);
        }else {
            deptDao.deleteDept(id);
        }
        //不只要删除对应的文件夹，还要删除其下属文件夹
        List<FolderVo> folderVo = folderDao.getByBusiness("dept", id.toString());
        List<FolderVo> childFolders= getChildrenFoldersByFolderId(folderVo.get(0).getId());
        List<Long> folderIdList = new ArrayList<>();
        if (childFolders != null && childFolders.size() != 0) {
            for (FolderVo folder : childFolders) {
                folderIdList.add(folder.getId());
            }
        }
        if (folderIdList != null && folderIdList.size() != 0) {
            folderDao.deleteBatchFolder(folderIdList);
        }else {
            folderDao.deleteFolder(folderVo.get(0).getId());
        }
    }

    @Override
    public List<DeptVo> getAllDept() {
        Set<Long> currentFolders = ResFolderUtils.getCurrentFolder();
        for (Long folderId : currentFolders) {
            logger.debug("-----------当前可视文件夹"+folderId);
        }
        return deptDao.getAllDept(currentFolders);
    }

    @Override
    public DeptVo getDeptById(Long id) {
        return deptDao.getDeptById(id);
    }

    @Override
    public List<UserVo> getUserByDeptId(Long id) {
        if (id < 0L) {
            return userDao.getAllUsers();
        }
        return deptDao.getUserByDeptId(id);
    }

    @Override
    public void addUserToDept (String userIds, Long deptId) {
        List<Long> userIdList = StringUtil.formatIds(userIds);
        List<UserVo> userVos = deptDao.getUserByDeptId(deptId);
        FolderPo folderPo = null;
        Long userId = null;
        for (UserVo userVo : userVos) {
            userId = userVo.getId();
            if (!userIdList.contains(userId)) {
                deptDao.removeUserFromDept(userId, deptId);
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
                //将该部门业务文件夹下的该用户业务文件夹状态设为1  禁用
                List<FolderVo> folderVos = folderDao.getUserFolderByDeptId(deptId.toString());
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
                userIdList.remove(userId);
            }
        }
        UserVo userVo = null;
        for (Long user : userIdList) {
            userVo = userDao.getById(user);
            deptDao.addUserToDept(user, deptId);
            folderPo = new FolderPo();
            //查看该部门业务文件夹下是否有该用户文件夹，如果有，则将其状态改为可用，如果没有，则新建一个用户业务文件夹添加到部门文件夹下
            List<FolderVo> folderVos = folderDao.getUserFolderByDeptId(deptId.toString());
            List<FolderVo> deptFolder = folderDao.getByBusiness("dept", deptId.toString());
            boolean flag = false;
            for (FolderVo folderVo : folderVos) {
                if (folderVo.getBusiness().equals("user") && folderVo.getBusinessId().equals(user)) {
                    flag = true;
                    BeanUtils.copyProperties(folderVo, folderPo);
                    folderPo.setStatus(BaseInfoPo.Status_Able);
                    folderDao.updateFolder(folderPo);
                    break;
                }
            }
            if (!flag) {
                folderPo.setType("auto");
                folderPo.setIsLeaf(1);
                folderPo.setName(userVo.getRealName());
                folderPo.setBusiness("user");
                folderPo.setBusinessId(user.toString());
                folderPo.setRank(deptFolder.get(0).getRank() + 1);
                folderPo.setParentId(deptFolder.get(0).getId());
                folderPo.setStatus(BaseInfoPo.Status_Able);
                folderDao.addFolder(folderPo);
            }
        }
    }

    @Override
    public List<DeptVo> getChildrenDeptByDeptId(Long deptId) {
        //包含自己
        DeptVo deptVo = deptDao.getDeptById(deptId);
        List<DeptVo> deptVos = new ArrayList<>();
        deptVos.add(deptVo);
        return this.getChildrenDepts(deptVos, deptId);
    }

    @Override
    public List<FolderVo> getChildrenFoldersByFolderId(Long folderId) {
        //包含自己
        FolderVo folderVo = folderDao.getById(folderId);
        List<FolderVo> folderVos = new ArrayList<>();
        folderVos.add(folderVo);
        return this.getChildrenFolders(folderVos, folderId);
    }

    @Override
    public List<RoleVo> getRolesByDeptId(Long deptId) {
        return deptDao.getRolesByDeptId(deptId);
    }

    @Override
    public List<UserGroupVo> getGroupsByDeptId(Long deptId) {
        return deptDao.getUserGroupByDeptId(deptId);
    }

    private List<DeptVo> getChildrenDepts(List<DeptVo> vos, Long deptId) {
        List<DeptVo> deptVos = deptDao.getChildrenDepts(deptId);
        if (deptVos != null && deptVos.size() > 0) {
            for (DeptVo deptVo : deptVos) {
                vos.add(deptVo);
                this.getChildrenDepts(vos, deptVo.getId());
            }
        }
        return vos;
    }

    private List<FolderVo> getChildrenFolders(List<FolderVo> vos, Long folderId) {
        List<FolderVo> childFolders = folderDao.getChildFolders(folderId);
        if (childFolders != null && childFolders.size() > 0) {
            for (FolderVo folderVo : childFolders) {
                vos.add(folderVo);
                this.getChildrenFolders(vos, folderVo.getId());
            }
        }
        return vos;
    }
}
