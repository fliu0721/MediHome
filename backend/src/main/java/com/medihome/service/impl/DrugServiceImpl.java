package com.medihome.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medihome.dto.DrugDTO;
import com.medihome.dto.DrugQueryDTO;
import com.medihome.dto.DrugStockChangeDTO;
import com.medihome.entity.Drug;
import com.medihome.entity.DrugStockLog;
import com.medihome.entity.SysMessage;
import com.medihome.mapper.DrugMapper;
import com.medihome.mapper.DrugStockLogMapper;
import com.medihome.mapper.SysMessageMapper;
import com.medihome.service.DrugService;
import com.medihome.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * 药品服务实现类
 */
@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug> implements DrugService {
    
    @Autowired
    private DrugStockLogMapper stockLogMapper;
    
    @Autowired
    private SysMessageMapper messageMapper;
    
    @Value("${upload.path}")
    private String uploadPath;
    
    @Override
    public PageVO<Drug> pageQuery(Long userId, DrugQueryDTO queryDTO) {
        Page<Drug> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Drug> resultPage = baseMapper.selectDrugPage(
            page, userId, queryDTO.getKeyword(), queryDTO.getCategoryId(),
            queryDTO.getStatus(), queryDTO.getLocation(), queryDTO.getIsFavorite()
        );
        return PageVO.of(resultPage.getRecords(), resultPage.getTotal(), 
                        resultPage.getSize(), resultPage.getCurrent());
    }
    
    @Override
    public Drug getDetail(Long id) {
        return baseMapper.selectDrugDetail(id);
    }
    
    @Override
    @Transactional
    public void addDrug(Long userId, DrugDTO drugDTO) {
        Drug drug = new Drug();
        BeanUtils.copyProperties(drugDTO, drug);
        drug.setUserId(userId);
        
        // 检查过期状态
        Integer status = checkExpiryStatus(drug.getExpiryDate());
        drug.setStatus(status);
        drug.setIsFavorite(0);
        drug.setCreateTime(LocalDateTime.now());
        drug.setUpdateTime(LocalDateTime.now());
        
        baseMapper.insert(drug);
        
        // 如果即将过期或已过期，发送消息
        if (status == 1 || status == 2) {
            sendExpiryMessage(userId, drug);
        }
    }
    
    @Override
    @Transactional
    public void updateDrug(Long id, DrugDTO drugDTO) {
        Drug drug = baseMapper.selectById(id);
        if (drug == null) {
            throw new RuntimeException("药品不存在");
        }
        
        BeanUtils.copyProperties(drugDTO, drug);
        drug.setId(id);
        
        // 重新检查过期状态
        Integer status = checkExpiryStatus(drug.getExpiryDate());
        drug.setStatus(status);
        drug.setUpdateTime(LocalDateTime.now());
        
        baseMapper.updateById(drug);
    }
    
    @Override
    @Transactional
    public void deleteDrug(Long userId, Long id) {
        Drug drug = baseMapper.selectById(id);
        if (drug == null || !drug.getUserId().equals(userId)) {
            throw new RuntimeException("药品不存在或无权限");
        }
        baseMapper.deleteById(id);
    }
    
    @Override
    @Transactional
    public void toggleFavorite(Long userId, Long id, Integer isFavorite) {
        Drug drug = baseMapper.selectById(id);
        if (drug == null || !drug.getUserId().equals(userId)) {
            throw new RuntimeException("药品不存在或无权限");
        }
        drug.setIsFavorite(isFavorite);
        drug.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(drug);
    }
    
    @Override
    @Transactional
    public void archiveDrug(Long userId, Long id) {
        Drug drug = baseMapper.selectById(id);
        if (drug == null || !drug.getUserId().equals(userId)) {
            throw new RuntimeException("药品不存在或无权限");
        }
        drug.setStatus(3); // 已归档
        drug.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(drug);
    }
    
    @Override
    @Transactional
    public void changeStock(Long userId, Long id, DrugStockChangeDTO changeDTO) {
        Drug drug = baseMapper.selectById(id);
        if (drug == null || !drug.getUserId().equals(userId)) {
            throw new RuntimeException("药品不存在或无权限");
        }
        
        Integer beforeQuantity = drug.getStockQuantity();
        Integer changeQuantity = changeDTO.getChangeQuantity();
        
        // 计算变动后的数量
        Integer afterQuantity;
        if (changeDTO.getChangeType() == 1) {
            // 增加
            afterQuantity = beforeQuantity + changeQuantity;
        } else if (changeDTO.getChangeType() == 2) {
            // 减少
            afterQuantity = beforeQuantity - changeQuantity;
            if (afterQuantity < 0) {
                throw new RuntimeException("库存不足");
            }
        } else {
            // 盘点调整
            afterQuantity = changeQuantity;
        }
        
        // 更新药品库存
        drug.setStockQuantity(afterQuantity);
        drug.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(drug);
        
        // 记录库存变动日志
        DrugStockLog log = new DrugStockLog();
        log.setDrugId(id);
        log.setChangeType(changeDTO.getChangeType());
        log.setChangeQuantity(changeDTO.getChangeType() == 2 ? -changeQuantity : changeQuantity);
        log.setBeforeQuantity(beforeQuantity);
        log.setAfterQuantity(afterQuantity);
        log.setReason(changeDTO.getReason());
        log.setOperatorId(userId);
        log.setCreateTime(LocalDateTime.now());
        stockLogMapper.insert(log);
    }
    
    @Override
    public String uploadImage(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件为空");
        }
        
        // 生成文件名
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + ext;
        
        // 按日期创建目录
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String relativePath = "/drugs/" + datePath + "/" + newFilename;
        String fullPath = uploadPath + relativePath;
        
        // 创建目录
        File destFile = new File(fullPath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        }
        
        return "/uploads" + relativePath;
    }
    
    @Override
    public List<Drug> getExpiringDrugs(Long userId, Integer days) {
        LocalDate date = LocalDate.now().plusDays(days);
        return baseMapper.selectExpiringDrugs(userId, date);
    }
    
    @Override
    public List<Drug> getExpiredDrugs(Long userId) {
        return baseMapper.selectExpiredDrugs(userId);
    }
    
    @Override
    @Transactional
    public void batchDelete(Long userId, List<Long> ids) {
        for (Long id : ids) {
            deleteDrug(userId, id);
        }
    }
    
    @Override
    public void checkAndUpdateExpiryStatus(Long userId) {
        List<Drug> drugs = baseMapper.selectList(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Drug>()
                .eq(Drug::getUserId, userId)
                .ne(Drug::getStatus, 3) // 排除已归档
        );
        
        for (Drug drug : drugs) {
            Integer newStatus = checkExpiryStatus(drug.getExpiryDate());
            if (!newStatus.equals(drug.getStatus())) {
                drug.setStatus(newStatus);
                drug.setUpdateTime(LocalDateTime.now());
                baseMapper.updateById(drug);
                
                if (newStatus == 1 || newStatus == 2) {
                    sendExpiryMessage(userId, drug);
                }
            }
        }
    }
    
    /**
     * 检查过期状态
     */
    private Integer checkExpiryStatus(LocalDate expiryDate) {
        LocalDate now = LocalDate.now();
        long daysUntilExpiry = ChronoUnit.DAYS.between(now, expiryDate);
        
        if (daysUntilExpiry < 0) {
            return 2; // 已过期
        } else if (daysUntilExpiry <= 7) {
            return 1; // 即将过期
        } else {
            return 0; // 正常
        }
    }
    
    /**
     * 发送过期提醒消息
     */
    private void sendExpiryMessage(Long userId, Drug drug) {
        SysMessage message = new SysMessage();
        message.setUserId(userId);
        message.setType(1); // 过期提醒
        message.setTitle(drug.getStatus() == 2 ? "药品已过期" : "药品即将过期");
        message.setContent(String.format("%s%s，请及时处理", 
            drug.getName(), 
            drug.getStatus() == 2 ? "已过期" : "将于7天内过期"));
        message.setRelatedId(drug.getId());
        message.setIsRead(0);
        message.setCreateTime(LocalDateTime.now());
        messageMapper.insert(message);
    }
}
