package net.chensee.base.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xx
 * @program base
 * @date 2019-09-09 16:59
 * @description 数据对
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<R, T> {

    private R r;
    private T t;

}
