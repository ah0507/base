package net.chensee.base.action.resource.service;

import net.chensee.base.action.resource.po.ResourcePo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

/**
 * @Author : shibo
 * @Date : 2019/8/2 14:47
 */
public interface ResourceService {
    /**
     * 添加资源
     * @param resourcePo
     */
    BaseResponse addResource(ResourcePo resourcePo);

    /**
     * 修改资源
     * @param resourcePo
     */
    BaseResponse updateResource(ResourcePo resourcePo);

    /**
     * 删除资源
     * @param id
     */
    BaseResponse deleteResource(Long id);

    /**
     * 获取所有资源
     * @return
     */
    ObjectResponse getAllResource();

    /**
     * @Description: 根据ID获取资源
     * @Param: id
     * @return: resourceVo
     */

    ObjectResponse getResourceById(Long id);
}
