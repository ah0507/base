package net.chensee.base.action.login.service;

import net.chensee.base.action.login.po.LoginPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;

/**
 * @Author : shibo
 * @Date : 2019/8/2 10:24
 */
public interface LoginService {

    /**
     * 用户登录
     * @param loginName
     * @param loginPass
     * @param code
     * @return
     */
    ObjectResponse login(String loginName, String loginPass, String code);

    /**
     * 注册登录用户信息
     * @param loginPo
     */
    BaseResponse register(LoginPo loginPo);

    /**
     * 修改登录用户信息
     * @param loginPo
     */
    BaseResponse updateLogin(LoginPo loginPo);

    /**
     * 删除登录用户信息
     * @param id
     */
    BaseResponse deleteLogin(Long id);

    /**
     * 生成四位数字验证码
     * @return
     */
    ObjectResponse makeCode();
}
