package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.controller.admin
 * @className: CategoryController
 * @author: 姬紫衣
 * @description: 分类管理
 * @date: 2024/5/8 18:21
 * @version: 1.0
 */

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("新增分类");
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 菜品分类分页
     * @param categoryPageQueryDTO
     * @return
     */

    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO)
    {
        log.info("分类分页查询,参数为：{}" , categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }


    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result deleteById(Long id)
    {
        log.info("根据id删除分类");
        categoryService.deleteById(id);
        return Result.success();
    }


    @PutMapping
    @ApiOperation("修改分类")
    public Result update(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("修改分类");
        categoryService.update(categoryDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("启用，禁用员工账号")
    public Result startOrStop(@PathVariable Integer status , Long  id)
    {
        log.info("启用，禁用员工账号：{}" , status , id);
        categoryService.statusOrStop(status , id);
        return Result.success();
    }


    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> list(Integer type)
    {
        log.info("根据类型查询分类");
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }
}
