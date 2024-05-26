package com.sky.mapper;


import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper
{
    /**
     * 动态查询查询购物车信息
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 更新购物车信息
     * @param cart
     */
    void update(ShoppingCart cart);

    /**
     * 插入购物车信息
     * @param shoppingCart
     */
    void insert(ShoppingCart shoppingCart);

    List<ShoppingCart> listAll(Long currentId);

    /**
     * 根据用户id删除购物车信息
     * @param currentId
     */
    void deleteByUserId(Long currentId);
}

