package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medihome.entity.DrugStockLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 库存变动记录Mapper
 */
@Mapper
public interface DrugStockLogMapper extends BaseMapper<DrugStockLog> {
    
    Page<DrugStockLog> selectLogPage(Page<DrugStockLog> page, 
                                      @Param("drugId") Long drugId);
}
