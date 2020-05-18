package net.chensee.base.common.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author : shibo
 * @date : 2019/5/17 9:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseInfoPo extends IdPo {
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
