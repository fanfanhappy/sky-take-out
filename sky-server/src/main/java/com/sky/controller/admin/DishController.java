package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO)
    {
        log.info("新增菜品");
        dishService.saveWithFlavor(dishDTO);
        return null;
    }
}
