package net.chensee.base.action.dictionary.business;

import net.chensee.base.action.dictionary.po.DictionaryPo;
import net.chensee.base.action.dictionary.vo.DictionaryVo;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 17:31
 */
public interface DictionaryBus {

    /**
     * 通过类型ID获取所有的字典信息
     * @param groupId
     * @return
     */
    List<DictionaryVo> getByGroupId(Long groupId);

    /**
     * 添加字典
     * @param dictionaryPo
     */
    void addDictionary(DictionaryPo dictionaryPo);

    /**
     * 修改字典
     * @param dictionaryPo
     */
    void updateDictionary(DictionaryPo dictionaryPo);

    /**
     * 删除字典
     * @param id
     */
    void deleteDictionary(Long id);
}
