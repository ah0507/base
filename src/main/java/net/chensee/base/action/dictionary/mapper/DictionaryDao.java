package net.chensee.base.action.dictionary.mapper;

import net.chensee.base.action.dictionary.po.DictionaryPo;
import net.chensee.base.action.dictionary.vo.DictionaryVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 16:21
 */
public interface DictionaryDao {

    /**
     * 通过组ID获取所有的字典信息
     * @param groupId
     * @return
     */
    List<DictionaryVo> getByGroupId(@Param("groupId") Long groupId);

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
    void deleteDictionary(@Param("id") Long id);
}
