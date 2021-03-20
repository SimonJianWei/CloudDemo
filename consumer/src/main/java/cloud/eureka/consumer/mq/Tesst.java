package cloud.eureka.consumer.mq;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "demo.queue")
public class Tesst {
    @RabbitHandler
    public void process(String json, Channel channel, Message message) throws IOException {
        try {
            // 配置文件 设置手动确认
            System.out.println(json);
            //确保  执行确认消息的代码
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);//不可少
        } catch (Exception e) {
            //最后一个参数是：是否重回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);// 不可少
        }
    }
}
