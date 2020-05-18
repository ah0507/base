package net.chensee.base.action.dictionary.business;

import net.chensee.base.action.dictionary.po.DictionaryGroupPo;
import net.chensee.base.action.dictionary.vo.DictionaryGroupVo;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 16:13
 */
public interface DictionaryGroupBus {

    /**
     * 添加字典组
     * @param dictionaryGroupPo
     */
    void addDictionaryGroup(DictionaryGroupPo dictionaryGroupPo);

    /**
     * 修改字典组
     * @param dictionaryGroupPo
     */
    void updateDictionaryGroup(DictionaryGroupPo dictionaryGroupPo);

    /**
     * 删除字典组
     * @param id
     */
    void deleteDictionaryGroup(Long id);

    /**
     * 获取所有的字典组
     * @return
     */
    List<DictionaryGroupVo> getAllDictionaryGroup(Long pageStart, Integer pageSize);

    /**
     * 获取所有的字典组
     * @return
     */
    List<DictionaryGroupVo> getAllDictionaryGroup();

    /**
     * 获取数据条数
     * @param name
     * @return
     */
    Long getCount(String name);

    /**
     * 根据字典组名称查找字典
     * @param name
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<DictionaryGroupVo> getByName(String name, Long pageStart, Integer pageSize);
}
