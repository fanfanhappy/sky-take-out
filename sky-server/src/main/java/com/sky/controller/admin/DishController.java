package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: DishController
 * @author: 姬紫衣
 * @description: 菜品相关接口
 * @date: 2024/5/9 15:55
 * @version: 1.0
 */

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api("菜品相关接口")
public class DishController {


    @Autowired
    private DishService dishService;
    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO)
    {
        log.info("新 增菜品");
        dishService.saveWithFlavor(dishDTO);
        return null;
    }

    @GetMapping("/page")
    @ApiOperation("菜品分 页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO)
    {
        log.info("菜品分页查询");
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids)
    {
        log.info("菜品的批量删除 :{}" ,ids);
        dishService.deleteBatch(ids);
        //忘了给前端返 回结果！！！！
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("菜品的起售和禁售")
    public Result startOrStop(@PathVariable Integer status , Long id)
    {
        log.info("菜品的起售，禁售 ：{}" ,status , id);
        dishService.startOrStop(status , id);
        return Result.success();
    }


    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id)
    {
        log.info("根据id查询菜品");
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
}
