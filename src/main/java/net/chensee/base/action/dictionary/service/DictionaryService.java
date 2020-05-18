package net.chensee.base.action.dictionary.service;

import net.chensee.base.action.dictionary.po.DictionaryPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

/**
 * @Author : shibo
 * @Date : 2019/8/1 11:38
 */
public interface DictionaryService {

    /**
     * 通过类型ID获取所有的字典信息
     * @param groupId
     * @return
     */
    ObjectResponse getByGroupId(Long groupId);

    /**
     * 添加字典
     * @param dictionaryPo
     */
    ObjectResponse addDictionary(DictionaryPo dictionaryPo);

    /**
     * 修改字典
     * @param dictionaryPo
     */
    BaseResponse updateDictionary(DictionaryPo dictionaryPo);

    /**
     * 删除字典
     * @param id
     */
    BaseResponse deleteDictionary(Long id);
}
