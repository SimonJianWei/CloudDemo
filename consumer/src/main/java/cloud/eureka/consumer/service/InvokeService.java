package cloud.eureka.consumer.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * feign案例
 */
@FeignClient(value = "producer",fallback = FallBackService.class)//value 为服务名称
public interface InvokeService {

    @RequestMapping(value = "/test/{id}",method = RequestMethod.GET)
    Map<String,Object> getMessage(@PathVariable(value = "id")int id );
}
