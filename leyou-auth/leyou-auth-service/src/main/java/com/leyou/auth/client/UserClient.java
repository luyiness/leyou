package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Date:2021/03_1:24 下午
 * @Description：
 */
@FeignClient("user-service")
public interface UserClient extends UserApi {
}
