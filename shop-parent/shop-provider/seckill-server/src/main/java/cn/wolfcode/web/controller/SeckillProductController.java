package cn.wolfcode.web.controller;

import cn.wolfcode.common.web.Result;
import cn.wolfcode.domain.SeckillProduct;
import cn.wolfcode.domain.SeckillProductVo;
import cn.wolfcode.service.ISeckillProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/seckillProduct")
@Slf4j
@Api(tags = "秒杀接口类")
public class SeckillProductController {
    @Autowired
    private ISeckillProductService seckillProductService;

    @ApiOperation("根据查词time查询")
    @RequestMapping("queryByTime")
    public Result queryByTime(Integer time) {
        List<SeckillProductVo> seckillProductVos = seckillProductService.queryByTime(time);

        return Result.success(seckillProductVos);
    }

    @GetMapping("queryProductById")
    public Result queryProductById(Long id) {

        SeckillProductVo seckillProductVo = seckillProductService.queryProductById(id);
        return Result.success(seckillProductVo);
    }
}
