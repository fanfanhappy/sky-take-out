package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
    @Autowired
    private SetmealDishMapper setmealDishMapper;

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


    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(dishPageQueryDTO.getPage() , dishPageQueryDTO.getPageSize());

        //获取分页查询对象
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);

        //
        long total = page.getTotal();
        List<DishVO> result = page.getResult();

        return new PageResult(total , result);
    }

    /**
     * 菜品的批量删除
     * @param ids
     */
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断当前菜品能否被删除--是否存在起售中的菜品
        for (Long id : ids) {
            //查找出当前菜品
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus() == StatusConstant.ENABLE)
            {
                //抛出异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //判断当前菜品能否被删除--是否被套餐关联
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds !=null && setmealIds.size() > 0)
        {
            //不能删除
            throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ENABLE_FAILED);
        }
        //删除菜品表在的菜品数据
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            //删除当前菜品关联的口味数据
//            dishFlavorMapper.deleteByDistId(id);
//        }

        //批量删除优化
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByIds(ids);
    }

    /**
     * 菜品的起售，禁售
     * @param status
     * @param id
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        //封装为实体类
        Dish dish = new Dish();
        dish.setId(id);
        dish.setStatus(status);

        dishMapper.update(dish);
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Override
    public DishVO getByIdWithFlavor(Long id) {
        DishVO dishVO = new DishVO();
        //根据id查询 dish
        Dish dish = dishMapper.getById(id);
        //根据dishId查询口味
        List<DishFlavor> dishFlavors = dishFlavorMapper.getFlavorById(id);

        //封装数据
        BeanUtils.copyProperties(dish , dishVO);
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }
}
