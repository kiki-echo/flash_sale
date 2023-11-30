package cn.wolfcode.fegin;

import cn.wolfcode.common.web.Result;
import cn.wolfcode.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@Service
//@Component
//@FeignClient:微服务客户端注解,value:指定微服务的名字,这样就可以使Feign客户端直接找到对应的微服务
@FeignClient(value = "product-service")
public interface ProductFegin {

    @RequestMapping("/product/queryProductByIds")
    Result<List<Product>> queryProductByIds(@RequestParam List<Long> ids);

}
