package net.chensee.base.common.vo;

import lombok.Data;

/**
 * @author xx
 * @program base
 * @date 2019-09-16 11:45
 * @description 数据列
 */
@Data
public class DataColumnVo {

    private String key;
    private String name;
    private Integer width;
    private Boolean isListNotShow;

}
