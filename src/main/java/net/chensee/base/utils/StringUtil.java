package net.chensee.base.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : shibo
 * @Date : 2019/7/22 15:16
 */
public class StringUtil {

    public static List<Long> formatIds(String ids) {
        List<Long> is = new ArrayList<>();
        if(ids == null || ids.length() == 0) {
            return is;
        }
        String[] ss = ids.split(",");
        for (String s : ss) {
            is.add(Long.parseLong(s));
        }
        return is;
    }
}
