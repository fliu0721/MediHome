package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medihome.entity.SysMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 系统消息Mapper
 */
@Mapper
public interface SysMessageMapper extends BaseMapper<SysMessage> {
    
    Page<SysMessage> selectMessagePage(Page<SysMessage> page,
                                        @Param("userId") Long userId,
                                        @Param("isRead") Integer isRead);
    
    @Select("SELECT COUNT(*) FROM sys_message WHERE user_id = #{userId} AND is_read = 0")
    Long selectUnreadCount(Long userId);
    
    @Update("UPDATE sys_message SET is_read = 1 WHERE user_id = #{userId} AND is_read = 0")
    void markAllAsRead(Long userId);
}
