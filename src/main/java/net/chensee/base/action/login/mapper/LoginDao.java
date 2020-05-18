package net.chensee.base.action.login.mapper;

import net.chensee.base.action.login.po.LoginPo;
import net.chensee.base.action.login.vo.LoginVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : shibo
 * @date : 2019/6/5 9:13
 */
@Mapper
public interface LoginDao {

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
    void deleteLogin(@Param("id") Long id);

    /**
     * 通过登录名查找登录信息
     * @param loginName
     * @return
     */
    LoginVo getLoginByName(@Param("loginName") String loginName);

    /**
     * 删除用户的登录方式
     * @param userId
     */
    void deleteByUserId(@Param("userId") Long userId);
}
