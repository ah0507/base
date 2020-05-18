package net.chensee.base.action.login.service.impl;

import net.chensee.base.action.login.po.LoginPo;
import net.chensee.base.action.login.business.LoginBus;
import net.chensee.base.action.login.service.LoginService;
import net.chensee.base.action.login.vo.LoginVo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author : shibo
 * @Date : 2019/8/2 10:24
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginPo.class);
    private static String identifyCode = "";

    @Resource
    private LoginBus loginBus;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public ObjectResponse login(String loginName, String loginPass, String code) {
        if(loginName != null && loginPass != null) {
            //校验验证码
            if(code == null || code == "" || !code.equals(identifyCode)) {
                return ObjectResponse.fail("验证码错误");
            }
            //校验用户名和密码
            String pwd = null;
            LoginVo login = loginBus.getLoginByName(loginName);
            if (login != null) {
                pwd = passwordEncoder.encode(loginPass);
                if (pwd.equals(login.getLoginPass())) {
                    return ObjectResponse.ok(login);
                }
            }
        }
        return ObjectResponse.fail("用户名或密码错误");
    }

    @Override
    public BaseResponse register(LoginPo loginPo) {
        try {
            loginPo.setLoginPass(passwordEncoder.encode(loginPo.getLoginPass()));
            loginBus.register(loginPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("添加登录方式出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse updateLogin(LoginPo loginPo) {
        try {
            loginPo.setLoginPass(passwordEncoder.encode(loginPo.getLoginPass()));
            loginBus.updateLogin(loginPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改登录方式出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public BaseResponse deleteLogin(Long id) {
        try {
            loginBus.deleteLogin(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除登录方式出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse makeCode() {
        int i = (int)((Math.random()*9+1)*1000);
        identifyCode = String.valueOf(i);
        return ObjectResponse.ok(identifyCode);
    }
}
