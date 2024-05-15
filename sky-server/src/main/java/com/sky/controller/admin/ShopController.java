package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: ShopController
 * @author: 姬紫衣
 * @description: TODO
 * @date: 2024/5/15 10:43
 * @version: 1.0
 */


@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
@Api(tags = "店铺相关接口")
public class ShopController {

    @Autowired
    private RedisTemplate redisTemplate;
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setShopStatus(@PathVariable Integer status)
    {
        log.info("设置店铺营业状态：{}" , status == 1 ? "店铺营业" : "店铺打样");
        //在redis中设置
        redisTemplate.opsForValue().set("SHOP_STATUS" , status);
        return Result.success();
    }


    /**
     * 获取店铺营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result<Integer> getShopStatus()
    {
        Integer status =(Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取店铺营业状态：{}" , status);
        return Result.success(status);
    }
}
