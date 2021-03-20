package cloud.eureka.consumer.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * hystrix 服务降级
 */
@Component
public class FallBackService implements InvokeService {

    @Override
    public Map<String, Object> getMessage(int id) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "您请求的服务正繁忙，请稍后重试");
        result.put("code", 502);
        return result;
    }
}
