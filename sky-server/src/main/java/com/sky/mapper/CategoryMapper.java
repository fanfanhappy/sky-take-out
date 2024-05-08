package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {


    /**
     * 新增分类
     * @param category
     */
    @Insert("insert into category (id, type, name, sort, status, create_time, update_time, create_user, update_user) values " +
            "(#{id} , #{type} , #{name} , #{sort} , #{status} ,#{createTime}  ,#{updateTime} ,#{createUser} ,#{updateUser})")
    void save(Category category);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 修改分类
     * @param category
     */
    void update(Category category);

    @Select("select *  from category where type = #{type}")
    List<Category> list(Integer type);
}
