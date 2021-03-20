package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Date:2021/03_11:01 上午
 * @Description：
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LeyouAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeyouAuthApplication.class, args);
    }
}
