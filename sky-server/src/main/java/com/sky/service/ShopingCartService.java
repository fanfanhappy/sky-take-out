package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service
 * @className: ShopingCartService
 * @author: 姬紫衣
 * @description: TODO
 * @date: 2024/5/26 18:59
 * @version: 1.0
 */
public interface ShopingCartService {

    void addShopingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> shouwShoppingCart();

    /**
     * 清空购物车
     */
    void clean();

    /**
     * 删除购物车中一个商品
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);

}
