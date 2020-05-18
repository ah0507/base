package net.chensee.base.action.dictionary.business.impl;

import net.chensee.base.action.dictionary.mapper.DictionaryDao;
import net.chensee.base.action.dictionary.mapper.DictionaryGroupDao;
import net.chensee.base.action.dictionary.po.DictionaryGroupPo;
import net.chensee.base.action.dictionary.business.DictionaryGroupBus;
import net.chensee.base.action.dictionary.vo.DictionaryGroupVo;
import net.chensee.base.common.po.BaseInfoPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 16:15
 */
@Component
public class DictionaryGroupBusImpl implements DictionaryGroupBus {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryGroupPo.class);

    @Resource
    private DictionaryGroupDao dictionaryGroupDao;
    @Resource
    private DictionaryDao dictionaryDao;

    @Override
    public void addDictionaryGroup(DictionaryGroupPo dictionaryGroupPo) {
        dictionaryGroupPo.setStatus(BaseInfoPo.Status_Able);
        dictionaryGroupDao.addDictionaryGroup(dictionaryGroupPo);
    }

    @Override
    public void updateDictionaryGroup(DictionaryGroupPo dictionaryGroupPo) {
        dictionaryGroupDao.updateDictionaryGroup(dictionaryGroupPo);
    }

    @Override
    public void deleteDictionaryGroup(Long id) {
        dictionaryGroupDao.deleteDictionaryGroup(id);
    }

    @Override
    public List<DictionaryGroupVo> getAllDictionaryGroup(Long pageStart, Integer pageSize) {
        return dictionaryGroupDao.getAllDictionaryGroup(pageStart, pageSize);
    }

    public List<DictionaryGroupVo> getAllDictionaryGroup() {
        return dictionaryGroupDao.getAllDictionaryGroups();
    }

    @Override
    public Long getCount(String name) {
        return dictionaryGroupDao.getCount(name);
    }

    @Override
    public List<DictionaryGroupVo> getByName(String name, Long pageStart, Integer pageSize) {
        return dictionaryGroupDao.getByName(name, pageStart, pageSize);
    }
}
