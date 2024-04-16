package cn.wolfcode.service.impl;

import cn.wolfcode.common.web.Result;
import cn.wolfcode.domain.Product;
import cn.wolfcode.domain.SeckillProduct;
import cn.wolfcode.domain.SeckillProductVo;
import cn.wolfcode.fegin.ProductFegin;
import cn.wolfcode.mapper.SeckillProductMapper;
import cn.wolfcode.service.ISeckillProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class SeckillProductServiceImpl implements ISeckillProductService {
    private final Logger log= LogManager.getLogger(this.getClass());
    @Autowired
    private SeckillProductMapper seckillProductMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    @Autowired
    private ProductFegin productFegin;

    @Override
    public List<SeckillProductVo> queryByTime(Integer time) {
        List<SeckillProduct> seckillProducts = seckillProductMapper.queryCurrentlySeckillProduct(time);
        List<Long> ids= seckillProducts.stream().map(m->m.getProductId()).collect(Collectors.toList());
        log.info("成功");
        Result result = productFegin.queryProductByIds(ids);
        List<Product>  products;
        if ( result.getData()!=null){
             products= (List<Product>) result.getData();
//            BeanUtils.copyProperties(result.getData(),products);
        } else {
             return (List<SeckillProductVo>) Result.defaultError();
        }
        List<SeckillProductVo> seckillProductVos= new ArrayList<>();
        Map<Long,Product> productMap = new HashMap<>();
        try{
                products.stream().forEach(product -> {
            productMap.put(product.getId(),product);

        });
        }catch (Exception e){
            System.out.println(e);
        }

        seckillProducts.stream().forEach(seckillProduct -> {
            SeckillProductVo seckillProductVo =new SeckillProductVo();
            BeanUtils.copyProperties(productMap.get(seckillProduct.getProductId()),seckillProductVo);
            BeanUtils.copyProperties(seckillProduct,seckillProductVo);
            seckillProductVo.setCurrentCount(seckillProductVo.getCurrentCount());
            seckillProductVos.add(seckillProductVo);
        });


        return seckillProductVos;
    }

    @Override
    public SeckillProductVo queryProductById(Integer id) {
        List<Long> ids=new ArrayList<>(id);
        try{
            List<Product> data = productFegin.queryProductByIds(ids).getData();
            if (data==null){
                return (SeckillProductVo) Result.defaultError();
            }
            SeckillProductVo seckillProductVo =new SeckillProductVo();
            BeanUtils.copyProperties(seckillProduct,seckillProductVo);
            seckillProductVo.setCurrentCount(seckillProductVo.getCurrentCount());
            return seckillProductVo;
        }catch (Exception e){
            log.error("查询商品失败>>>>>>>>>>"+e);
        }




    }
}
