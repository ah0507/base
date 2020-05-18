package net.chensee.base.action.resource.business;

import net.chensee.base.action.resource.po.ResourcePo;
import net.chensee.base.action.resource.vo.ResourceVo;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/12 16:28
 */
public interface ResourceBus {

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
    void deleteResource(Long id);

    /**
     * 获取所有资源
     * @return
     */
    List<ResourceVo> getAllResource();

    /**
    * @Description: 根据ID获取资源
    * @Param: id
    * @return: resourceVo
    */
    ResourceVo getResourceById(Long id);
}
