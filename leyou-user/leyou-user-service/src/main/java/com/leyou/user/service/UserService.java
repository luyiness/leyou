package com.leyou.user.service;

import com.leyou.common.utils.NumberUtils;
import com.leyou.user.mapper.UserMapper;
import com.leyou.user.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Date:2021/03_2:51 下午
 * @Description：
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    static final String KEY_PREFIX = "USER:VERIFY";

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

    public void sendVerifyCode(String phone) {
        if (StringUtils.isBlank(phone)) {     //是commons.lang3依赖中的方法
            return;
        }

        //1、生成验证码
        String code = NumberUtils.generateCode(6);  //调用commons模块中工具类的方法

        //2、发送消息到radditMQ
        HashMap<String,String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code", code);

        this.amqpTemplate.convertAndSend("leyou.sms.exchange","sms.verify.code",map);

        //3、将验证码保存到redis中
        this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code,1, TimeUnit.MINUTES);
    }
}
