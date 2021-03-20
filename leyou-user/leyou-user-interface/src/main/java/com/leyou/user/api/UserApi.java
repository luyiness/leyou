package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Date:2021/03_1:22 下午
 * @Description：
 */
public interface UserApi {

    @GetMapping("query")
    public User queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password);
}
