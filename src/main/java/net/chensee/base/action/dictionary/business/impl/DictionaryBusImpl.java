package net.chensee.base.action.dictionary.business.impl;

import net.chensee.base.action.dictionary.mapper.DictionaryDao;
import net.chensee.base.action.dictionary.po.DictionaryPo;
import net.chensee.base.action.dictionary.business.DictionaryBus;
import net.chensee.base.action.dictionary.vo.DictionaryVo;
import net.chensee.base.common.po.BaseInfoPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 17:32
 */
@Component
public class DictionaryBusImpl implements DictionaryBus {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryPo.class);

    @Resource
    private DictionaryDao dictionaryDao;

    @Override
    public List<DictionaryVo> getByGroupId(Long groupId) {
        return dictionaryDao.getByGroupId(groupId);
    }

    @Override
    public void addDictionary(DictionaryPo dictionaryPo) {
        dictionaryPo.setStatus(BaseInfoPo.Status_Able);
        dictionaryDao.addDictionary(dictionaryPo);
    }

    @Override
    public void updateDictionary(DictionaryPo dictionaryPo) {
        dictionaryDao.updateDictionary(dictionaryPo);
    }

    @Override
    public void deleteDictionary(Long id) {
        dictionaryDao.deleteDictionary(id);
    }
}
