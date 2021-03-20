package cloud.eureka.producer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper jacksonObject;
    @GetMapping("/test/{id}")
    public Map<String,Object> getMessage(@PathVariable("id") int id){
        Map<String,Object> result=new HashMap<>();
        String message="";
        switch (id){
            case 0 :message="这是测试服务";break;
            case 1 :message="上帝之子卡卡";break;
            case 2 :message="世界足球先生";break;
            case 3 :message="金球奖得主";break;
        }
        result.put("data",message);
        result.put("code",200);
        result.put("tootal",1);
        return result;
    }
    @RequestMapping("ceshi")
    public String testQueue() throws JsonProcessingException {
        String flag="测试队列使用";
        Map<String,Object>mq=new HashMap<>();
        mq.put("test","This is a test queue");
        String mess=jacksonObject.writeValueAsString(mq);
        amqpTemplate.convertAndSend("TestTopicExchange","Test",mess,message -> {
            //message.getMessageProperties().setContentType("application/json");
            message.getMessageProperties().setHeader("test","practice");
            message.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            message.getMessageProperties().setDelay(60);
            return message;
        });
        return  flag;
    }
}
