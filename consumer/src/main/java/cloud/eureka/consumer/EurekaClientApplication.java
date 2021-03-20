package cloud.eureka.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * eureka-client
 * ribbion 负载均衡 使用restemplate
 * 若使用 eureka-client jar包 无需添加ribbion jar包  添加上 netflix-ribbion 也没有什么影响
 * <p>
 * 使用http：//微服务名/path  url路径  不能使用ip地址加端口号 ribbion 不能识别
 */

@EnableRabbit
@SpringBootApplication
@EnableEurekaClient //开启eureka客户端注解
@EnableFeignClients //开启feign注解
@EnableHystrix  //开启请求熔断注解
public class EurekaClientApplication {

    @Bean
    @LoadBalanced
    public RestTemplate getRestemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class);
    }
}
