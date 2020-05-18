package net.chensee.base.action.resource.business.impl;

import net.chensee.base.action.resource.mapper.ResourceDao;
import net.chensee.base.action.resource.po.ResourcePo;
import net.chensee.base.action.resource.business.ResourceBus;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.common.po.BaseInfoPo;
import net.chensee.base.utils.ResFolderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author : shibo
 * @date : 2019/6/12 16:35
 */
@Component
public class ResourceBusImpl implements ResourceBus {
    private static final Logger logger = LoggerFactory.getLogger(ResourcePo.class);

    @Resource
    private ResourceDao resourceDao;

    @Override
    public void addResource(ResourcePo resourcePo) {
            resourcePo.setStatus(BaseInfoPo.Status_Able);
            resourceDao.addResource(resourcePo);
    }

    @Override
    public void updateResource(ResourcePo resourcePo) {
            resourceDao.updateResource(resourcePo);
    }

    @Override
    public void deleteResource(Long id) {
            resourceDao.deleteResource(id);
    }

    @Override
    public List<ResourceVo> getAllResource() {
        /*Set<Long> currentFolders = ResFolderUtils.getCurrentFolder();
        for (Long folderId : currentFolders) {
            logger.debug("-----------当前可视文件夹"+folderId);
        }*/
        return resourceDao.getAllResource();
    }

    @Override
    public ResourceVo getResourceById(Long id) {
        return resourceDao.getById(id);
    }
}
