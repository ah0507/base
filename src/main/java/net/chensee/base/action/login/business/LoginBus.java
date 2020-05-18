package net.chensee.base.action.login.business;

import net.chensee.base.action.login.po.LoginPo;
import net.chensee.base.action.login.vo.LoginVo;

/**
 * @author : shibo
 * @date : 2019/6/5 9:15
 */
public interface LoginBus {

    /**
     * 通过名称查询账号
     * @param loginName
     * @return
     */
    LoginVo getLoginByName(String loginName);

    /**
     * 注册登录用户信息
     * @param loginPo
     */
    void register(LoginPo loginPo);

    /**
     * 修改登录用户信息
     * @param loginPo
     */
    void updateLogin(LoginPo loginPo);

    /**
     * 删除登录用户信息
     * @param id
     */
    void deleteLogin(Long id);
}
