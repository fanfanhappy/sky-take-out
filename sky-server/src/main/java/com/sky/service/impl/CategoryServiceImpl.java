package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @projectName: sky-take-out
 * @package: com.sky.service.impl
 * @className: CategoryServiceImpl
 * @author: 姬紫衣
 * @description: TODO
 * @date: 2024/5/8 19:04
 * @version: 1.0
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void save(CategoryDTO categoryDTO) {
        //创建分类实体类
        Category category = new Category();
        //对象属性的拷贝
        BeanUtils.copyProperties(categoryDTO ,  category);

        //设置其他属性
        //设置默认分类状态，默认0为禁用
        category.setStatus(0);
        //设置分类管理的创建和更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());

        //设置创建和更新人的id
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        //新增
        categoryMapper.save(category);

    }

    /**
     * 分页分类查询
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(categoryPageQueryDTO.getPage() , categoryPageQueryDTO.getPageSize());

        //开始查询，获取分页信息对象
        Page<Category> page =  categoryMapper.pageQuery(categoryPageQueryDTO);

        //返回对象
        long total = page.getTotal();
        List<Category> result = page.getResult();

        return new PageResult(total , result);
    }

    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    /**
     * 修改分类
     * @param categoryDTO
     */
    @Override
    public void update(CategoryDTO categoryDTO) {
        //创建实体类
        Category category = new Category();

        //对象属性的拷贝
        BeanUtils.copyProperties(categoryDTO  , category);

        //设置其他属性
        //设置更新时间
        category.setUpdateTime(LocalDateTime.now());
        //设置更新操作人id
        category.setUpdateUser(BaseContext.getCurrentId());

        //更新
        categoryMapper.update(category);
    }

    /**
     * 启用，禁用员工账号
     * @param status
     * @param id
     */
    @Override
    public void statusOrStop(Integer status, Long id) {
        //封装为实体类
        Category category = new Category();
        category.setStatus(status);
        category.setId(id);

        categoryMapper.update(category);
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public List<Category> list(Integer type) {
        List<Category> categoryList = categoryMapper.list(type);
        return categoryList;
    }
}
