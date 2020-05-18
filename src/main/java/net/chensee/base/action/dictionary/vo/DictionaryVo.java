package net.chensee.base.action.dictionary.vo;

import lombok.Data;

/**
 * @author : shibo
 * @date : 2019/6/5 16:04
 */
@Data
public class DictionaryVo {

    /**ID*/
    private Long id;
    /**字典组*/
    private Long groupId;
    private String groupCode;
    private String groupName;
    /**名称*/
    private String name;
    /**编码*/
    private String code;
    /**状态(0 使用 1 删除)*/
    private Integer status;
}
