package net.chensee.base.action.user.mapper;

import net.chensee.base.action.user.po.UserResourcePo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserResourceDao {

    /**
     * @Description: 获取用户自身的所有资源
     * @param: userId
     * @return: List<UserResourcePo>
     */
    List<UserResourcePo> getUserUniqueResourcesByUserId(@Param("userId") Long userId);

    /**
     * @Description: 获取用户自身的增加资源
     * @param: userId
     * @return: List<UserResourcePo>
     */
    List<UserResourcePo> getUserIncludeResourcesByUserId(@Param("userId") Long userId);

    /**
     * @Description: 获取用户自身的排除资源
     * @param: userId
     * @return: List<UserResourcePo>
     */
    List<UserResourcePo> getUserExcludeResourcesByUserId(@Param("userId") Long userId);

}
