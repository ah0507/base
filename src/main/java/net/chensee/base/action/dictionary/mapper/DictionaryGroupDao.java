package net.chensee.base.action.dictionary.mapper;

import net.chensee.base.action.dictionary.po.DictionaryGroupPo;
import net.chensee.base.action.dictionary.vo.DictionaryGroupVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 16:08
 */
public interface DictionaryGroupDao {

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
    void deleteDictionaryGroup(@Param("id") Long id);

    /**
     * 获取所有的字典组
     * @return
     */
    List<DictionaryGroupVo> getAllDictionaryGroup(@Param("pageStart") Long pageStart, @Param("pageSize")  Integer pageSize);

    /**
     * 获取所有的字典组
     * @return
     */
    List<DictionaryGroupVo> getAllDictionaryGroups();

    /**
     * 根据字典组名称查找字典
     * @param name
     * @param pageStart
     * @param pageSize
     * @return
     */
    List<DictionaryGroupVo> getByName(@Param("name") String name, @Param("pageStart") Long pageStart, @Param("pageSize")  Integer pageSize);

    /**
     * 获取数据条数
     * @param name
     * @return
     */
    Long getCount(@Param("name") String name);
}
