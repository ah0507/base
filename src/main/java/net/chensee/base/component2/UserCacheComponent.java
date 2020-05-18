package net.chensee.base.component2;

import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.user.vo.UserDetailsAllVo;
import net.chensee.base.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xx
 * @program base
 * @date 2019-08-13 16:12
 * @description 用户数据缓存组件
 */
@Component
@Slf4j
public class UserCacheComponent {

    @Autowired
    private CacheManager cacheManager;

    public void saveUserInfo(String accessToken, String refreshToken, UserDetailsAllVo userDetails) {
        Cache userCache = cacheManager.getCache("_user");

        Map<String, Object> ui = new HashMap<>(3);
        ui.put("accessToken", accessToken);
        ui.put("refreshToken", refreshToken);
        ui.put("userDetails", userDetails);

        userCache.put(accessToken, JsonUtil.toStr(ui));
        // userCache.put(accessToken, ui);
    }

    public String getUserInfo(String accessToken) {
        Cache userCache = cacheManager.getCache("_user");
        Cache.ValueWrapper valueWrapper = userCache.get(accessToken);
        if (valueWrapper == null) {
            return null;
        }
        return (String) userCache.get(accessToken).get();
    }

    public void removeUserInfo(Long userId) {
        Cache userCache = cacheManager.getCache("_user");
        userCache.evict(userId);
    }

    public void saveLoginInfo(String accessToken, Object loginInfo) {
        Assert.notNull(accessToken, "缓存Key值不能为空");
        Assert.notNull(loginInfo, "缓存Key值不能为空");
        Cache loginCache = cacheManager.getCache("_login");
        loginCache.put(accessToken, loginInfo);
    }

    public Object getLoginInfo(String accessToken) {
        Assert.notNull(accessToken, "缓存Key值不能为空");
        Cache loginCache = cacheManager.getCache("_login");
        Cache.ValueWrapper valueWrapper = loginCache.get(accessToken);
        if (valueWrapper == null) {
            return null;
        }
        return valueWrapper.get();
    }

}
