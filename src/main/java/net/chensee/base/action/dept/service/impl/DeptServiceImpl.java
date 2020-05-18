package net.chensee.base.action.dept.service.impl;

import net.chensee.base.action.dept.po.DeptPo;
import net.chensee.base.action.dept.business.DeptBus;
import net.chensee.base.action.dept.service.DeptService;
import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.folder.mapper.FolderDao;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author : shibo
 * @Date : 2019/8/4 9:53
 */
@Service
@Transactional
public class DeptServiceImpl implements DeptService {
    private static final Logger logger = LoggerFactory.getLogger(DeptPo.class);

    @Resource
    private DeptBus deptBus;
    @Resource
    private FolderDao folderDao;

    @Override
    public ObjectResponse addDept(DeptPo deptPo) {
        try {
            deptBus.addDept(deptPo);
            return ObjectResponse.ok(deptPo.getId());
        } catch (Exception e) {
            logger.error("添加组织出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse updateDept(DeptPo deptPo) {
        try {
            List<FolderVo> folderVo = folderDao.getByBusiness("dept", deptPo.getId().toString());
            if (folderVo == null || folderVo.size() == 0) {
                logger.error("该部门对应文件夹不存在");
                return BaseResponse.fail("该部门对应文件夹不存在");
            }
            deptBus.updateDept(deptPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改组织出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteDept(Long id) {
        try {
            deptBus.deleteDept(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除组织出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getAllDept() {
        return ObjectResponse.ok(deptBus.getAllDept());
    }

    @Override
    public ObjectResponse getDeptById(Long id) {
        return ObjectResponse.ok(deptBus.getDeptById(id));
    }

    @Override
    public ObjectResponse getUserByDeptId(Long id) {
        return ObjectResponse.ok(deptBus.getUserByDeptId(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addUserToDept(String userIds, Long deptId) throws Exception{
        try {
            deptBus.addUserToDept(userIds, deptId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加用户到部门异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getChildrenDeptByDeptId(Long deptId) {
        try {
            DeptVo deptVo = deptBus.getDeptById(deptId);
            if (deptVo == null) {
                return ObjectResponse.fail("该部门不存在!");
            }
            return ObjectResponse.ok(deptBus.getChildrenDeptByDeptId(deptId));
        } catch (Exception e) {
            logger.error("获取部门下所有子部门出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getRolesByDeptId(Long deptId) {
        try {
            return ObjectResponse.ok(deptBus.getRolesByDeptId(deptId));
        } catch (Exception e) {
            logger.error("根据部门获取角色出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getGroupsByDeptId(Long deptId) {
        try {
            return ObjectResponse.ok(deptBus.getGroupsByDeptId(deptId));
        } catch (Exception e) {
            logger.error("根据部门获取用户组出现异常", e);
            return ObjectResponse.fail();
        }
    }
}
