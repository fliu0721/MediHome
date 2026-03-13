package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medihome.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统配置Mapper
 */
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfig> {
    
    @Select("SELECT * FROM sys_config WHERE user_id = 0 OR user_id = #{userId}")
    List<SysConfig> selectByUserId(Long userId);
    
    @Select("SELECT config_value FROM sys_config WHERE (user_id = 0 OR user_id = #{userId}) AND config_key = #{key} " +
            "ORDER BY user_id DESC LIMIT 1")
    String selectValueByKey(@Param("userId") Long userId, @Param("key") String key);
}
