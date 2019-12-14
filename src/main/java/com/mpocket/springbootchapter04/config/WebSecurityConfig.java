package com.mpocket.springbootchapter04.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//通过@EnableWebMvcSecurity注解开启Spring Security的功能
@EnableWebSecurity
//继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    /**
     * configure(HttpSecurity http)方法
     */
    protected void configure(HttpSecurity http) throws Exception {
        http
//        通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。例如以下代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .anyRequest().authenticated()
                .and()
//        通过formLogin()定义当需要用户登录时候，转到的登录页面。
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                //开启记住密码
                .rememberMe()
                .rememberMeParameter("_spring_security_remember_me");
    }

    @Autowired
    /**
     * configureGlobal(AuthenticationManagerBuilder auth)方法，在内存中创建了一个用户，该用户的名称为user，密码为password，用户角色为USER。
     */
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER");
    }

}
