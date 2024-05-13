package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: SetmealServiceImpl
 * @author: 姬紫衣
 * @description: TODO
 * @date: 2024/5/13 16:18
 * @version: 1.0
 *
 *
 */

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        //开启分页查询
        PageHelper.startPage(setmealPageQueryDTO.getPage() , setmealPageQueryDTO.getPageSize());
        //获取分页查询对象
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        //获取数据
        long total = page.getTotal();
        List<SetmealVO> result = page.getResult();

        return new PageResult(total , result);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDTO setmealDTO) {
        //新增套餐信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO , setmeal);

        setmealMapper.insert(setmeal);
        //新增套餐中的菜品信息
        Long id = setmeal.getId();//获取插入套餐后的主键

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0)
        {
            //将套餐id赋给套餐菜品单
            for (SetmealDish setmealDish : setmealDishes)
            {
                setmealDish.setSetmealId(id);
            }
            //批量插入套餐菜品
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }

    /**
     * 修改套餐状态
     * @param status
     * @param id
     */
    @Override
    @Transactional
    public void startOrStop(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getSetmealById(Long id) {
        SetmealVO setmealVO = new SetmealVO();
        //查询setmeal
        Setmeal setmeal = setmealMapper.getById(id);
        //根据id查询setmealdish
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetDishsById(id);
        BeanUtils.copyProperties(setmeal , setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断套餐状态，如果是起售则不能删除
        for(Long id : ids)
        {
            //获取当前套餐
            Setmeal setmeal = setmealMapper.getById(id);
            if(setmeal.getStatus() == StatusConstant.ENABLE)
            {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //可以删除
        //1.删除套餐setmeal信息
            setmealMapper.deleteByIds(ids);
        //2.删除关联setmealdish信息
        setmealDishMapper.deleteCDByIds(ids);
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        //先更新当前detmeal实体信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO , setmeal);
        setmealMapper.update(setmeal);

       //删除当前套餐关联的dish
        setmealDishMapper.deleteById(setmeal.getId());

        //获取当前套餐的id
        Long id = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0)
        {
            for(SetmealDish setmealDish : setmealDishes)
            {
                setmealDish.setSetmealId(id);
            }
            //批量插入
            setmealDishMapper.insertBatch(setmealDishes);
        }
    }
}
