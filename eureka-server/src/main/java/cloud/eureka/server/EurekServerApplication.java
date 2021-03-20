package cloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * spring cloud  服务与注册中心
 * eureka-server
 */

@SpringBootApplication
@EnableEurekaServer
public class EurekServerApplication {


    public static void main(String[] args) {
        SpringApplication.run(EurekServerApplication.class, args);
    }
}
