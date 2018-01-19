package cn.zhangxd.svcb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.zhangxd.svcb.service.BBSService;

@RefreshScope
@RestController
public class ServiceBController {

    @Autowired
    EurekaDiscoveryClient discoveryClient;
    
    @Autowired
    BBSService bbsService;

    @Value("${msg:unknown}")
    private String msg;

    @GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>Saybbbbbbbbbbbbbaaaaa=: " + msg;
    }
    
    
    @GetMapping(value = "/bbb")
    public String printServiceBbb() {
        System.out.println("===========================微服务测试方案bbbb=============================");
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        System.out.println("===========================微服务测试方案bbbb====== bbsService.queryDemo=======================");
        System.out.println("===========================微服务测试方案bbbb====== bbsService.queryDemo====endddddd===================");
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")" + "===>222222222222222222222=: " + msg+"=======:"+bbsService.queryDemo("");
    }
}