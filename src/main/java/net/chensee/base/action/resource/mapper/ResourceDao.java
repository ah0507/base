package net.chensee.base.action.resource.mapper;

import net.chensee.base.action.resource.po.ResourcePo;
import net.chensee.base.action.resource.vo.ResourceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author : shibo
 * @date : 2019/6/12 16:26
 */
public interface ResourceDao {

    /**
     * 添加资源
     * @param resourcePo
     */
    void addResource(ResourcePo resourcePo);

    /**
     * 修改资源
     * @param resourcePo
     */
    void updateResource(ResourcePo resourcePo);

    /**
     * 删除资源
     * @param id
     */
    void deleteResource(@Param("id") Long id);

    /**
     * 获取所有资源
     * @return
     */
    List<ResourceVo> getAllResource();

    /**
     * 根据ID获取资源
     * @param id
     * @return
     */
    ResourceVo getById(@Param("id") Long id);

    /**
     * 获取子资源
     * @param id
     * @return
     */
    List<ResourceVo> getChildren(@Param("id") Long id);

    /**
     * 获取用户、用户组、角色 与 资源的关联数据条数
     * @param resourceId
     * @return
     */
    Map<String, Long> getLinkResources(@Param("resourceId") Long resourceId);
}
