package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

/**
 * @projectName: sky-take-out
 * @package: com.sky.mapper
 * @className: DishMapper
 * @author: 姬紫衣
 * @description: TODO
 * @date: 2024/5/9 16:13
 * @version: 1.0
 */

@Mapper
public interface DishMapper {
    /**
     * 插入菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);
}
