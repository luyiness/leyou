package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Date:2021/03_2:38 下午
 * @Description：
 */
@SpringBootApplication
@EnableDiscoveryClient
public class LeyouUploadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUploadApplication.class, args);
    }
}
