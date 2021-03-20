//package cloud.eureka.consumer.service;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.utils.SerializationUtils;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//
//@Component
//public class RabbitmqConsumer {
//
//    @RabbitListener(queues = "test_queue")
//    public Map<String,Object> getMessage(Message message){
//        Map<String,Object>result=(Map)SerializationUtils.deserialize(message.getBody());
//        return result;
//    }
//}
