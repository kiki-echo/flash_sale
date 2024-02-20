package cn.wolfcode.web.controller;

import cn.wolfcode.common.web.Result;
import cn.wolfcode.domain.Product;
import cn.wolfcode.service.IProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/product")
@Api(tags = "用户接口类")
public class ProductController {
    @Autowired
    private IProductService productService;

    @ApiOperation("商品查询")
    @RequestMapping("queryProductByIds")
    public Result<List<Product>> queryProductByIds(@RequestParam List<Long> ids) {


        return Result.success(productService.queryProductByIds(ids));

    }
}
