package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medihome.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    @Select("SELECT * FROM sys_user WHERE phone = #{phone} LIMIT 1")
    User selectByPhone(String phone);
}
