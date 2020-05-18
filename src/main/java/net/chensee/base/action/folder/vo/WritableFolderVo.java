package net.chensee.base.action.folder.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class WritableFolderVo {
    /*public static final Integer isDefault_Able = 0;
    public static final Integer isDefault_Disable = 1;
    public static final Integer status_Able = 0;
    public static final Integer status_Disable = 1;*/

    /**用户ID*/
    private Long userId;
    /**文件夹ID*/
    private Long folderId;
    /**文件名称*/
    private String folderName;
    /**是否为默认文件夹*/
    private Integer isDefault;
    /**状态*/
    private Integer status;

}
