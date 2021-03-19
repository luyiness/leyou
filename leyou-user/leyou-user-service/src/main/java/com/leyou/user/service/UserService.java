package com.leyou.user.service;

import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Date:2021/03_2:51 下午
 * @Description：
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 手机号、用户名的唯一性校验
     * @param data
     * @param type
     * @return
     */
    public Boolean checkUser(String data, Integer type) {
        User user = new User();
        if (type == 1){
            user.setUsername(data);
        }else if (type == 2){
            user.setPhone(data);
        }else {
            return null;
        }
        return this.userMapper.selectCount(user) == 0;
    }
}
