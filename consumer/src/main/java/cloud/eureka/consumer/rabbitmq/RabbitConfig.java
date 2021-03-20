package cloud.eureka.consumer.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:applicationContext-rabbit.xml") //引入mq 配置文件
public class RabbitConfig {
    @Bean
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange("cloud_direct").build();
    }

    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange("cloud_dead").build();
    }


}
