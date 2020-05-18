package net.chensee.base.action.login.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.login.service.LoginService;
import net.chensee.base.action.login.po.LoginPo;
import net.chensee.base.action.user.vo.UserDetailsAllVo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import net.chensee.base.component2.UserCacheComponent;
import net.chensee.base.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : shibo
 * @date : 2019/6/5 9:13
 */
@RestController
@RequestMapping("")
@Slf4j
public class LoginController {

    @Resource
    private LoginService loginService;
    @Autowired
    private UserCacheComponent userCacheComponent;

    /*@ApiOperation(value = "登录")
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ObjectResponse userLogin(@RequestParam String loginName, @RequestParam String loginPass, @RequestParam String identifyCode) {
        try {
            if(loginName == null || loginPass == null) {
                return ObjectResponse.fail("用户名或密码不能为空");
            }
            return loginService.login(loginName, loginPass, identifyCode);
        } catch (Exception e) {
            log.error("登录异常", e);
        }
        return ObjectResponse.fail("登录异常");
    }*/

    @ApiOperation(value = "添加登录方式")
    @ResponseBody
    @RequestMapping(value = "/login/account", method = RequestMethod.POST)
    public BaseResponse register(@RequestBody LoginPo loginPo) {
        return loginService.register(loginPo);
    }

    @ApiOperation(value = "修改登录方式")
    @ResponseBody
    @RequestMapping(value = "/login/account", method = RequestMethod.PUT)
    public BaseResponse updateLogin(@RequestBody LoginPo loginPo) {
        return loginService.updateLogin(loginPo);
}

    @ApiOperation(value = "删除登录方式")
    @RequestMapping(value = "/login/account/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteLogin(@PathVariable Long id) {
        return loginService.deleteLogin(id);
    }

    @ApiOperation(value = "生成四位数字验证码")
    @RequestMapping(value = "login/account/makeCode", method = RequestMethod.GET)
    public ObjectResponse makeCode() {
        return loginService.makeCode();
    }

    @ApiOperation(value = "登陆验证")
    @RequestMapping(value = "/login/valid", method = RequestMethod.GET)
    public ObjectResponse validLogin(HttpServletRequest request) {
        Object accessToken = request.getAttribute("_accessToken");
        if (accessToken == null) {
            return ObjectResponse.fail(501, "请重新登陆");
        }
        Object loginInfo = userCacheComponent.getLoginInfo((String) accessToken);
        if (loginInfo == null) {
            return ObjectResponse.fail(501, "请重新登陆");
        }
        return ObjectResponse.ok(loginInfo);
    }
}
