package net.chensee.base.action.dictionary.service.impl;

import net.chensee.base.action.dictionary.business.DictionaryBus;
import net.chensee.base.action.dictionary.service.DictionaryService;
import net.chensee.base.action.dictionary.po.DictionaryPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author : shibo
 * @Date : 2019/8/1 11:42
 */
@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryPo.class);

    @Resource
    private DictionaryBus dictionaryBus;

    @Override
    public ObjectResponse getByGroupId(Long groupId) {
        return ObjectResponse.ok(dictionaryBus.getByGroupId(groupId));
    }

    @Override
    public ObjectResponse addDictionary(DictionaryPo dictionaryPo) {
        try {
            dictionaryBus.addDictionary(dictionaryPo);
            return ObjectResponse.ok(dictionaryPo.getId());
        } catch (Exception e) {
            logger.error("添加数据字典出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    public BaseResponse updateDictionary(DictionaryPo dictionaryPo) {
        try {
            dictionaryBus.updateDictionary(dictionaryPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改数据字典出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteDictionary(Long id) {
        try {
            dictionaryBus.deleteDictionary(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除数据字典出现异常", e);
            return BaseResponse.fail();
        }
    }
}
