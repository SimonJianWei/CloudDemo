//package cloud.eureka.consumer.rabbitmq;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.utils.SerializationUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component("aaaa")
//public class RabbitmqConsumer {
//
//    @RabbitListener(queues = "test_queue")
//    public void getMessage(Message message){
//        Map<String,Object>result=(Map)SerializationUtils.deserialize(message.getBody());
//    }
//}
