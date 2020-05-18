package net.chensee.base.action.role.service.impl;

import net.chensee.base.action.role.business.RoleBus;
import net.chensee.base.action.role.service.RoleService;
import net.chensee.base.action.role.po.RolePo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author : shibo
 * @Date : 2019/8/2 10:37
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private static final Logger logger = LoggerFactory.getLogger(RolePo.class);

    @Resource
    private RoleBus roleBus;

    @Override
    public ObjectResponse addRole(RolePo rolePo) {
        try {
            roleBus.addRole(rolePo);
            return ObjectResponse.ok(rolePo.getId());
        } catch (Exception e) {
            logger.error("添加角色出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse updateRole(RolePo rolePo) {
        try {
            roleBus.updateRole(rolePo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改角色出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteRole(Long id) {
        try {
            roleBus.deleteRole(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除角色出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getAllRole() {
        return ObjectResponse.ok(roleBus.getAllRole());
    }

    @Override
    public ObjectResponse getRoleById(Long id) {
        return ObjectResponse.ok(roleBus.getRoleById(id));
    }

    @Override
    public ObjectResponse getResourceByRoleId(Long id) {
        return ObjectResponse.ok(roleBus.getResourceByRoleId(id));
    }

    @Override
    public ObjectResponse getUserByRoleId(Long id) {
        return ObjectResponse.ok(roleBus.getUserByRoleId(id));
    }

    @Override
    public ObjectResponse getUserGroupByRoleId(Long id) {
        return ObjectResponse.ok(roleBus.getUserGroupByRoleId(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addUserToRole(String userIds, Long roleId) throws Exception{
        try {
            roleBus.addUserToRole(userIds, roleId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加用户到角色异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addUserGroupToRole(String groupIds, Long roleId) throws Exception{
        try {
            roleBus.addUserGroupToRole(groupIds, roleId);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加用户组到角色异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getExcludeColumns(Long roleId, Long resourceId, Long folderId) {
        try {
            return ObjectResponse.ok(roleBus.getExcludeColumns(roleId, resourceId, folderId));
        } catch (Exception e) {
            logger.error("获取排除字段异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse addResourceToRole(Long roleId, Map<Long, Map<Long,String>> paramMap) throws Exception{
        try {
            roleBus.allotResourceToRole(roleId, paramMap);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加资源到角色异常", e);
            return BaseResponse.fail();
        }
    }
}
