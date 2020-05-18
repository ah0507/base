package net.chensee.base.action.resource.service.impl;

import net.chensee.base.action.resource.business.ResourceBus;
import net.chensee.base.action.resource.service.ResourceService;
import net.chensee.base.action.resource.mapper.ResourceDao;
import net.chensee.base.action.resource.po.ResourcePo;
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
 * @Date : 2019/8/2 14:48
 */
@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {
    private static final Logger logger = LoggerFactory.getLogger(ResourcePo.class);

    @Resource
    private ResourceBus resourceBus;
    @Resource
    private ResourceDao resourceDao;

    @Override
    public BaseResponse addResource(ResourcePo resourcePo) {
        try {
            //校验父资源是否为最低级
            Long parentId = resourcePo.getParentId();
            Map<String, Long> map = null;
            long count = 0;
            if(parentId != null && parentId != -1 && parentId != 0) {
                //查看是否关联用户、用户组、角色
                map = resourceDao.getLinkResources(parentId);
                count = 0;
                for(Map.Entry<String, Long> m : map.entrySet()) {
                    count += m.getValue();
                }
                if(count > 0) {
                    return BaseResponse.fail("选中的父级资源已存在关联，不可作为父级资源");
                }
            }
            resourceBus.addResource(resourcePo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加资源出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse updateResource(ResourcePo resourcePo) {
        try {
            resourceBus.updateResource(resourcePo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改资源出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteResource(Long id) {
        try {
            resourceBus.deleteResource(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除资源出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getAllResource() {
        return ObjectResponse.ok(resourceBus.getAllResource());
    }

    @Override
    public ObjectResponse getResourceById(Long id) {
        return ObjectResponse.ok(resourceBus.getResourceById(id));
    }
}
