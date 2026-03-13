package com.medihome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.medihome.dto.DrugDTO;
import com.medihome.dto.DrugQueryDTO;
import com.medihome.dto.DrugStockChangeDTO;
import com.medihome.entity.Drug;
import com.medihome.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 药品服务接口
 */
public interface DrugService extends IService<Drug> {
    
    /**
     * 分页查询药品
     */
    PageVO<Drug> pageQuery(Long userId, DrugQueryDTO queryDTO);
    
    /**
     * 获取药品详情
     */
    Drug getDetail(Long id);
    
    /**
     * 添加药品
     */
    void addDrug(Long userId, DrugDTO drugDTO);
    
    /**
     * 修改药品
     */
    void updateDrug(Long id, DrugDTO drugDTO);
    
    /**
     * 删除药品
     */
    void deleteDrug(Long userId, Long id);
    
    /**
     * 收藏/取消收藏
     */
    void toggleFavorite(Long userId, Long id, Integer isFavorite);
    
    /**
     * 归档药品
     */
    void archiveDrug(Long userId, Long id);
    
    /**
     * 调整库存
     */
    void changeStock(Long userId, Long id, DrugStockChangeDTO changeDTO);
    
    /**
     * 上传图片
     */
    String uploadImage(MultipartFile file);
    
    /**
     * 获取即将过期药品
     */
    List<Drug> getExpiringDrugs(Long userId, Integer days);
    
    /**
     * 获取已过期药品
     */
    List<Drug> getExpiredDrugs(Long userId);
    
    /**
     * 批量删除
     */
    void batchDelete(Long userId, List<Long> ids);
    
    /**
     * 检查并更新药品过期状态
     */
    void checkAndUpdateExpiryStatus(Long userId);
}
