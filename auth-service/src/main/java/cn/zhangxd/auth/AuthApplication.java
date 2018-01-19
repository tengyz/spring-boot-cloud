package cn.zhangxd.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

    public static void main(String[] args) {
    	System.out.println("===========================AuthApplication=====main========================");
        SpringApplication.run(AuthApplication.class, args);
    }

}
