package net.chensee.base.action.userGroup.mapper;

import net.chensee.base.action.userGroup.po.UserGroupResourcePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupResourceDao {

    /**
     * @Description: 根据用户组ID获取组内的所有资源
     * @Param: id
     * @return: List<UserGroupResourcePo>
     */
    List<UserGroupResourcePo> getResourceByUserGroupId(@Param("id") Long id);

    /**
     * 根据userId获取该用户所属用户组的关联资源
     * @param userId
     * @return
     */
    List<UserGroupResourcePo> getUserGroupResourceByUserId(@Param("userId")Long userId);

}
