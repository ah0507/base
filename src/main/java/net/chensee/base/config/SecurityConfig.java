package net.chensee.base.config;

import net.chensee.base.action.folder.business.FolderBus;
import net.chensee.base.action.resource.business.ResourceBus;
import net.chensee.base.component.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author : xx
 * @program: base
 * @create: 2019-07-01 16:00
 * @description: SecurityConfig
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 不需要认证的接口
    @Value("${com.example.oauth.security.antMatchers}")
    private String[] antMatchers;
    @Autowired
    private ResourceBus resourceBus;
    @Autowired
    private FolderBus folderBus;

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //校验用户
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 配置如何通过拦截器保护请求
     * 指定哪些请求需要认证，哪些请求不需要认证，以及所需要的权限
     * 通过调用authorizeRequests()和anyRequest().authenticated()就会要求所有进入应用的HTTP请求都要进行认证
     * <p>
     * 方法描述
     * anonymous()                         允许匿名用户访问
     * authenticated()                     允许经过认证的用户访问
     * denyAll()                           无条件拒绝所有访问
     * fullyAuthenticated()                如果用户是完整的话（不是通过Remember-me功能认证的），就允许访问
     * hasAnyAuthority(String...)          如果用户具备给定权限中的某一个的话，就允许访问
     * hasAnyRole(String...)               如果用户具备给定角色中的某一个的话，就允许访问
     * hasAuthority(String)                如果用户具备给定权限的话，就允许访问
     * hasIpAddress(String)                如果请求来自给定IP地址的话，就允许访问
     * hasRole(String)                     如果用户具备给定角色的话，就允许访问
     * not()                               对其他访问方法的结果求反
     * permitAll()                         无条件允许访问
     * rememberMe()                        如果用户是通过Remember-me功能认证的，就允许访问
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //关闭csrf验证
        http
                // 基于token，所以不需要session  如果基于session 则表使用这段代码
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //对请求进行认证  url认证配置顺序为：1.先配置放行不需要认证的 permitAll() 2.然后配置 需要特定权限的 hasRole() 3.最后配置 anyRequest().authenticated()
                .authorizeRequests()
                // 所有 /oauth/v1/api/login/ 请求的都放行 不做认证即不需要登录即可访问
                .antMatchers(antMatchers).permitAll()
                // 其他请求都需要进行认证,认证通过够才能访问
                // 待考证：如果使用重定向 httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest,httpServletResponse); 重定向跳转的url不会被拦截（即在这里配置了重定向的url需要特定权限认证不起效），但是如果在Controller 方法上配置了方法级的权限则会进行拦截
                .anyRequest().authenticated()
                .and().exceptionHandling()
                // 认证配置当用户请求了一个受保护的资源，但是用户没有通过登录认证，则抛出登录认证异常，MyAuthenticationEntryPointHandler类中commence()就会调用
                .authenticationEntryPoint(authenticationEntryPoint())
                //用户已经通过了登录认证，在访问一个受保护的资源，但是权限不够，则抛出授权异常，MyAccessDeniedHandler类中handle()就会调用
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                // 加入自定义UsernamePasswordAuthenticationFilter替代原有Filter
                .addFilterAt(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 添加JWT filter 验证其他请求的Token是否合法
                .addFilterBefore(authenticationTokenFilterBean(), FilterSecurityInterceptor.class)
                // 添加动态权限验证filter
                .addFilterBefore(securityInterceptor(), FilterSecurityInterceptor.class)
                // 添加权限属性filter 对请求的权限进行获取，使其符合 行/列 权限要求
                .addFilterBefore(authAttrsFilterBean(), FilterSecurityInterceptor.class)
                //
                .formLogin()
                // 登录url
                .loginProcessingUrl("/login")
                // .loginProcessingUrl("/auth/v1/api/login/enter")  //使用自己定义的Controller 中的方法 登录会进入Controller 中的方法
                // username参数名称 后台接收前端的参数名
                .usernameParameter("loginName")
                //登录密码参数名称 后台接收前端的参数名
                .passwordParameter("loginPass")
                //登录成功跳转路径
                //.successForwardUrl("/")
                //登录失败跳转路径
                .failureUrl("/login")
                //登录页面路径
                .loginPage("/login")
                //登录成功后 MyAuthenticationSuccessHandler类中onAuthenticationSuccess（）被调用
                .successHandler(authenticationSuccessHandler())
                //登录失败后 MyAuthenticationFailureHandler 类中onAuthenticationFailure（）被调用
                .failureHandler(authenticationFailureHandler())
                .permitAll()
                .and()
                .logout()
                //退出系统url
                // .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                //退出系统后的url跳转
                // .logoutSuccessUrl("/login")
                //退出系统后的 业务处理
                .logoutSuccessHandler(logoutSuccessHandler())
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                //登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                // 勾选Remember me登录会在PERSISTENT_LOGINS表中，生成一条记录
                .rememberMe()
                //cookie的有效期(秒)
                .tokenValiditySeconds(3600)
                .and()
                // 禁用csrf  关闭csrf验证
                .csrf().disable();
        // 开启csrf  通过cookie来传递csrf token
        // .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        // 登入API不启用CSFR检查
        // .ignoringAntMatchers("/login");
        // 禁用缓存
        http.headers().cacheControl();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new MyAccessDeniedHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new MyLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new MyAuthenticationEntryPointHandler();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        return new MyUsernamePasswordAuthenticationPreFilter(authenticationManagerBean(), authenticationSuccessHandler(), authenticationFailureHandler());
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    @Bean
    public AuthAttrsFilter authAttrsFilterBean() throws Exception {
        AuthAttrsFilter authAttrsFilter = new AuthAttrsFilter();
        authAttrsFilter.setIgnoreMatchers(antMatchers);
        authAttrsFilter.setFolderBus(folderBus);
        return authAttrsFilter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public MyFilterSecurityInterceptor securityInterceptor() {
        MyFilterSecurityInterceptor myFilterSecurityInterceptor = new MyFilterSecurityInterceptor();
        myFilterSecurityInterceptor.setMyAccessDecisionManager(new MyAccessDecisionManager());
        MyInvocationSecurityMetadataSourceService metadataSourceService = new MyInvocationSecurityMetadataSourceService();
        metadataSourceService.setResourceBus(resourceBus);
        myFilterSecurityInterceptor.setSecurityMetadataSource(metadataSourceService);
        return myFilterSecurityInterceptor;
    }

    /*public MyAccessDecisionManager accessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    public FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource() {
        return new MyInvocationSecurityMetadataSourceService();
    }*/

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

}

