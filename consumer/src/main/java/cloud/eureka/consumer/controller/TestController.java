package cloud.eureka.consumer.controller;

import cloud.eureka.consumer.service.InvokeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用  feign  restemplate调用接口案例
 */
@RestController
public class TestController {
    @Autowired
    private InvokeService invoke;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/getmessage")
    public Map<String, Object> getUserInfo(int id) {
        Map<String, Object> result = invoke.getMessage(id);
        return result;
    }

    @RequestMapping("/testMq")
    public String getUserInfo1(String type, String sec) {
        String a;
        if ("0".equals(type)) {

            amqpTemplate.convertAndSend("cloud_direct", "demo.queue", a = "这是一个普通测试队列");
        } else {
            amqpTemplate.convertAndSend("cloud_direct", "demo.delay", a = "这是一个延时测试队列", message -> {
                message.getMessageProperties().setExpiration(sec);
                return message;
            });
            //延时60秒
        }
        return a;
    }

    /**
     * hystrix 服务降级处理
     *
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "messageFallback")
    @RequestMapping("/getmessage1")
    public Map<String, Object> getUserInfo1(Integer id) {
        String url = "http://producer/test/{id}";//producer应用名位置不能写IP地址
        System.out.println(id);
        Map stringMap = new HashMap<>();
        stringMap.put("id", id);

        Map result = restTemplate.getForObject(url, Map.class, stringMap);
        return result;
    }


    //服务器降级处理方法
    public Map<String, Object> messageFallback(Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "您请求的服务正繁忙，请稍后重试");
        result.put("code", 502);
        return result;
    }
}
