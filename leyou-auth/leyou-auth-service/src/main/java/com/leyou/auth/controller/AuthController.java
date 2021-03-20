package com.leyou.auth.controller;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.service.AuthService;
import com.leyou.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Date:2021/03_1:03 下午
 * @Description：
 */
@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username")String username,
                                         @RequestParam("password")String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response){

        String token = this.authService.accredit(username,password);

        if (StringUtils.isBlank(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  //UNAUTHORIZED 是身份未认证状态码
        }

        CookieUtils.setCookie(request, response, jwtProperties.getCookieName(), token, jwtProperties.getExpire()*60);
                                                                                                        //生存时间，传入的是分钟，要*60换算成秒
        return ResponseEntity.ok(null);
    }
}
