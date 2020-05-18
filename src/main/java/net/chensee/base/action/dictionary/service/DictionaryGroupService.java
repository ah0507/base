package net.chensee.base.action.dictionary.service;

import net.chensee.base.action.dictionary.po.DictionaryGroupPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

/**
 * @Author : shibo
 * @Date : 2019/8/1 16:43
 */
public interface DictionaryGroupService {

    /**
     * 添加字典组
     * @param dictionaryGroupPo
     */
    ObjectResponse addDictionaryGroup(DictionaryGroupPo dictionaryGroupPo);

    /**
     * 修改字典组
     * @param dictionaryGroupPo
     */
    BaseResponse updateDictionaryGroup(DictionaryGroupPo dictionaryGroupPo);

    /**
     * 删除字典组
     * @param id
     */
    BaseResponse deleteDictionaryGroup(Long id);

    /**
     * 获取所有的字典组
     * @return
     */
    ObjectResponse getAllDictionaryGroup(Integer pageSize, Long pageNumber);

    /**
     * 获取所有的字典组
     * @return
     */
    ObjectResponse getAllDictionaryGroup();

    /**
     * 根据字典组名称查找字典
     * @param name
     * @param pageSize
     * @param pageNumber
     * @return
     */
    ObjectResponse getByName(String name, Integer pageSize, Long pageNumber);
}
