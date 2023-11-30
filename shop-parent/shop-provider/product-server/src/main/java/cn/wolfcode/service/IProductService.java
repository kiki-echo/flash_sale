package cn.wolfcode.service;

import cn.wolfcode.domain.Product;

import java.util.List;


public interface IProductService {
    public  List<Product> queryProductByIds(List<Long> ids);
}
