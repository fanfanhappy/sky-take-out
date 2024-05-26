package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShopingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.user
 * @className: ShoppingCarController
 * @author: 姬紫衣
 * @description: TODO
 * @date: 2024/5/26 16:03
 * @version: 1.0
 */

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车相关接口")
@Slf4j
public class ShoppingCarController {

    @Autowired
    public ShopingCartService shopingCartService;
    @PostMapping("/add")
    @ApiOperation("添加购物车")
    private Result add(@RequestBody ShoppingCartDTO shoppingCartDTO)
    {
        log.info("添加购物车，{}" ,shoppingCartDTO);
        shopingCartService.addShopingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> list()
    {
        log.info("展示购物车");
        List<ShoppingCart> list = shopingCartService.shouwShoppingCart();
        return Result.success(list);
    }

    @DeleteMapping("/clean")
    @ApiOperation("清空购物车")
    public Result clean()
    {
        log.info("清空购物车");
        shopingCartService.clean();
        return Result.success();
    }
}
