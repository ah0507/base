package net.chensee.base.utils;

import net.chensee.base.common.po.BaseInfoPo;

import java.util.Date;

public class CreateAndUpdateInfoUtil {

    public static void addCreateInfo(BaseInfoPo baseInfoPo) {
        Date date = new Date();
        baseInfoPo.setCreateTime(date);
        baseInfoPo.setCreateBy(UserUtil.getCurrentUser().getId());
    }

    public static void addUpdateInfo(BaseInfoPo baseInfoPo) {
        Date date = new Date();
        baseInfoPo.setUpdateTime(date);
        baseInfoPo.setUpdateBy(UserUtil.getCurrentUser().getId());
    }
}
