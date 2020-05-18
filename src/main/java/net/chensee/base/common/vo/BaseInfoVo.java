package net.chensee.base.common.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author : shibo
 * @date : 2019/5/21 9:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseInfoVo extends IdVo {

    public static final Integer Status_Able = 0;
    public static final Integer Status_Disable = 1;

    private Date createTime;
    private Long createBy;
    private Date updateTime;
    private Long updateBy;

    @NotNull(message = "文件夹ID不能为空")
    private Long folderId;
    private Integer version;
    private Integer status;
}
