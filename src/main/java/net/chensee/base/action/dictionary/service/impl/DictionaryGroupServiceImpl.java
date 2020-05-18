package net.chensee.base.action.dictionary.service.impl;

import net.chensee.base.action.dictionary.business.DictionaryGroupBus;
import net.chensee.base.action.dictionary.service.DictionaryGroupService;
import net.chensee.base.action.dictionary.po.DictionaryGroupPo;
import net.chensee.base.action.dictionary.business.DictionaryBus;
import net.chensee.base.action.dictionary.vo.DictionaryGroupVo;
import net.chensee.base.action.dictionary.vo.DictionaryVo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : shibo
 * @Date : 2019/8/1 16:43
 */
@Service
@Transactional
public class DictionaryGroupServiceImpl implements DictionaryGroupService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryGroupPo.class);

    @Resource
    private DictionaryGroupBus dictionaryGroupBus;
    @Resource
    private DictionaryBus dictionaryBus;

    @Override
    public ObjectResponse addDictionaryGroup(DictionaryGroupPo dictionaryGroupPo) {
        try {
            dictionaryGroupBus.addDictionaryGroup(dictionaryGroupPo);
            return ObjectResponse.ok(dictionaryGroupPo.getId());
        } catch (Exception e) {
            logger.error("添加字典组出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse updateDictionaryGroup(DictionaryGroupPo dictionaryGroupPo) {
        try {
            dictionaryGroupBus.updateDictionaryGroup(dictionaryGroupPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改字典组出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteDictionaryGroup(Long id) {
        try {
            dictionaryGroupBus.deleteDictionaryGroup(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除字典组出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getAllDictionaryGroup(Integer pageSize, Long pageNumber) {
        List<DictionaryGroupVo> dictionaryGroupVos = dictionaryGroupBus.getAllDictionaryGroup((pageNumber - 1) * pageSize, pageSize);
        Long count = dictionaryGroupBus.getCount(null);
        this.setDictionaryToGroup(dictionaryGroupVos);
        Map map = new HashMap();
        map.put("data", dictionaryGroupVos);
        map.put("total", count);
        return ObjectResponse.ok(map);
    }

    @Override
    public ObjectResponse getAllDictionaryGroup() {
        List<DictionaryGroupVo> dictionaryGroupVos = dictionaryGroupBus.getAllDictionaryGroup();
        this.setDictionaryToGroup(dictionaryGroupVos);
        return ObjectResponse.ok(dictionaryGroupVos);
    }

    @Override
    public ObjectResponse getByName(String name, Integer pageSize, Long pageNumber) {
        List<DictionaryGroupVo> dictionaryGroupVos = dictionaryGroupBus.getByName(name, (pageNumber - 1) * pageSize, pageSize);
        Long count = dictionaryGroupBus.getCount(name);
        this.setDictionaryToGroup(dictionaryGroupVos);
        Map map = new HashMap();
        map.put("data", dictionaryGroupVos);
        map.put("total", count);
        return ObjectResponse.ok(map);
    }

    private void setDictionaryToGroup(List<DictionaryGroupVo> groupVos) {
        if(groupVos != null && groupVos.size() > 0) {
            Long groupId = null;
            List<DictionaryVo> dictionaryVos = null;
            for (DictionaryGroupVo group : groupVos) {
                groupId = group.getId();
                dictionaryVos = dictionaryBus.getByGroupId(groupId);
                group.setDictionaryVos(dictionaryVos);
            }
        }
    }
}
