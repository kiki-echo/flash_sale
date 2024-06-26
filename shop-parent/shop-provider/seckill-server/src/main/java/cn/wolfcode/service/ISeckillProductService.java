package cn.wolfcode.service;

import cn.wolfcode.domain.SeckillProduct;
import cn.wolfcode.domain.SeckillProductVo;

import java.util.List;


public interface ISeckillProductService {
    public List<SeckillProductVo> queryByTime(Integer time);
    public SeckillProductVo queryProductById(Integer time);
}
