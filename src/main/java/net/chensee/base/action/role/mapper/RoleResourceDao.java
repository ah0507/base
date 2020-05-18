package net.chensee.base.action.role.mapper;

import net.chensee.base.action.role.po.RoleResourcePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleResourceDao {

    /**
     * @Description: 根据角色ID获取角色下所有资源
     * @Param: id
     * @return: List<RoleResourcePo>
     */
    List<RoleResourcePo> getResourceByRoleId(@Param("id") Long id);

    /**
     * 根据userId来获取该用户所拥有角色的关联资源
     * @param userId
     * @return
     */
    List<RoleResourcePo> getRoleResourceByUserId(@Param("userId") Long userId);

}
