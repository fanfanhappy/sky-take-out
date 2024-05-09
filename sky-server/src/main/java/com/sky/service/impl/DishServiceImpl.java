package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: DishServiceImpl
 * @author: 姬紫衣
 * @description: 菜品相关接口
 * @date: 2024/5/9 15:59
 * @version: 1.0
 */

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        //属性拷贝
        BeanUtils.copyProperties(dishDTO , dish);
        //插入菜品数据
        dishMapper.insert(dish);

        //获取insert插入后的主键值
        Long id = dish.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0)
        {
            //将菜品的主键值赋给口味
            for (DishFlavor dishFlavor :
                    flavors) {
                dishFlavor.setDishId(id);
            }

            dishFlavorMapper.insertBatch(flavors);
        }
    }
}
