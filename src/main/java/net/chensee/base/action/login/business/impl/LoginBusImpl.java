package net.chensee.base.action.login.business.impl;

import net.chensee.base.action.login.mapper.LoginDao;
import net.chensee.base.action.login.po.LoginPo;
import net.chensee.base.action.login.business.LoginBus;
import net.chensee.base.action.login.vo.LoginVo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : shibo
 * @date : 2019/6/5 9:15
 */
@Component
public class LoginBusImpl implements LoginBus {

    @Resource
    private LoginDao loginDao;

    @Override
    public LoginVo getLoginByName(String loginName) {
        return loginDao.getLoginByName(loginName);
    }

    @Override
    public void register(LoginPo loginPo) {
        loginDao.register(loginPo);
    }

    @Override
    public void updateLogin(LoginPo loginPo) {
        loginDao.updateLogin(loginPo);
    }

    @Override
    public void deleteLogin(Long id) {
        loginDao.deleteLogin(id);
    }
}
