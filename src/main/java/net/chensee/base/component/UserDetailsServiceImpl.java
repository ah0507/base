package net.chensee.base.component;

import net.chensee.base.action.dept.vo.DeptVo;
import net.chensee.base.action.login.mapper.LoginDao;
import net.chensee.base.action.login.vo.LoginVo;
import net.chensee.base.action.resource.vo.ResourceVo;
import net.chensee.base.action.user.business.UserBus;
import net.chensee.base.action.user.vo.UserDetailsAllVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:09
 * @description: UserDetailsServiceImpl
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginDao loginDao;
    @Autowired
    private UserBus userBus;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //查数据库
        LoginVo loginVo = loginDao.getLoginByName(userName);
        if (loginVo != null && loginVo.getUserId() != null) {
            UserDetailsAllVo userDetailsAll = new UserDetailsAllVo();
            userDetailsAll.setId(loginVo.getUserId());
            userDetailsAll.setLoginName(loginVo.getLoginName());
            userDetailsAll.setLoginPass(loginVo.getLoginPass());
            userDetailsAll.setIssuedAt(new Date());

            List<ResourceVo> resourcesByUserId = userBus.getResourcesByUserId(loginVo.getUserId());
            userDetailsAll.setResourceVos(resourcesByUserId);
            userDetailsAll.authorities();

            List<DeptVo> deptsByUserId = userBus.getDeptsByUserId(loginVo.getUserId());
            userDetailsAll.setDeptVos(deptsByUserId);
            return userDetailsAll;

        }
        return null;
    }
}
