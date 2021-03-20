package cloud.eureka.producer.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    //队列 起名：TestTopicQueue
    @Bean
    public Queue TestTopicQueue() {
        return new Queue("TestQueue", false);
    }

    //topic交换机 起名：TestDirectExchange
    @Bean
    TopicExchange TestTopicExchange() {
        return new TopicExchange("TestTopicExchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestTopicQueue()).to(TestTopicExchange()).with("Test");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        // 消息返回, yml需要配置 publisher-returns: true
        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            System.out.println("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}"+correlationId+ replyCode+replyText+ exchange+ routingKey);
        });

        //消息确认机制  yml 需要配置  publisher-confirms: true
         template.setConfirmCallback((correlationData, ack, cause) -> {
             if(ack){
                 System.out.println("消息发送成功");
             }else{
                 System.out.println(correlationData.getReturnedMessage());
             }
         });
        return template;
    }
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//rabbitmq 代码配置优先级高
        return factory;
    }


}
