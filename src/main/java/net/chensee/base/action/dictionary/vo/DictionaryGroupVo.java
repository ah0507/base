package net.chensee.base.action.dictionary.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : shibo
 * @date : 2019/6/5 16:06
 */
@Data
public class DictionaryGroupVo {

    /**ID*/
    private Long id;
    /**字典组名称*/
    private String name;
    /**字典组编码*/
    private String code;
    /**状态(0 使用 1 删除)*/
    private Integer status;

    List<DictionaryVo> dictionaryVos;
}
