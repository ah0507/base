package net.chensee.base.action.dictionary.po;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据字典实体类
 * @author : shibo
 * @date : 2019/6/5 15:58
 */
@Data
public class DictionaryPo {

    /**ID*/
    @NotNull(message = "ID不可为空")
    private Long id;
    /**字典组*/
    @NotNull(message = "字典组ID不可为空")
    private Long groupId;
    /**名称*/
    @NotBlank(message = "名称不可为空")
    private String name;
    /**编码*/
    @NotBlank(message = "编码不可为空")
    private String code;
    /**状态(0 使用 1 删除)*/
    private Integer status;
}
