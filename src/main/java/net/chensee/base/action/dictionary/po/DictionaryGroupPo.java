package net.chensee.base.action.dictionary.po;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据字典组实体类
 * @author : shibo
 * @date : 2019/6/5 15:56
 */
@Data
public class DictionaryGroupPo {

    /**ID*/
    @NotNull(message = "ID不可为空")
    private Long id;
    /**字典组名称*/
    @NotBlank(message = "字典组名称不可为空")
    private String name;
    /**字典组编码*/
    @NotBlank(message = "字典组编码不可为空")
    private String code;
    /**状态(0 使用 1 删除)*/
    private Integer status;
}
