package com.sky.mapper;


import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    User getUserByOpenid(String openid);

    /**
     * 插入user
     * @param newUser
     */
    void insert(User newUser);
}
