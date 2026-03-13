package com.medihome.service;

import com.medihome.dto.DrugDTO;
import com.medihome.dto.DrugStockChangeDTO;
import com.medihome.entity.Drug;
import com.medihome.entity.DrugStockLog;
import com.medihome.mapper.DrugMapper;
import com.medihome.mapper.DrugStockLogMapper;
import com.medihome.mapper.SysMessageMapper;
import com.medihome.service.impl.DrugServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 药品服务单元测试
 */
@ExtendWith(MockitoExtension.class)
public class DrugServiceTest {

    @Mock
    private DrugMapper drugMapper;

    @Mock
    private DrugStockLogMapper stockLogMapper;

    @Mock
    private SysMessageMapper messageMapper;

    @InjectMocks
    private DrugServiceImpl drugService;

    @Test
    void testAddDrug_Normal() {
        DrugDTO drugDTO = new DrugDTO();
        drugDTO.setName("阿莫西林胶囊");
        drugDTO.setExpiryDate(LocalDate.now().plusMonths(6));
        drugDTO.setStockQuantity(10);

        drugService.addDrug(1L, drugDTO);

        verify(drugMapper).insert(any(Drug.class));
    }

    @Test
    void testChangeStock_Increase() {
        Long drugId = 1L;
        Long userId = 1L;

        Drug drug = new Drug();
        drug.setId(drugId);
        drug.setUserId(userId);
        drug.setStockQuantity(10);

        DrugStockChangeDTO changeDTO = new DrugStockChangeDTO();
        changeDTO.setChangeType(1); // 增加
        changeDTO.setChangeQuantity(5);
        changeDTO.setReason("采购入库");

        when(drugMapper.selectById(drugId)).thenReturn(drug);

        drugService.changeStock(userId, drugId, changeDTO);

        assertEquals(15, drug.getStockQuantity());
        verify(drugMapper).updateById(drug);
        verify(stockLogMapper).insert(any(DrugStockLog.class));
    }

    @Test
    void testChangeStock_Decrease() {
        Long drugId = 1L;
        Long userId = 1L;

        Drug drug = new Drug();
        drug.setId(drugId);
        drug.setUserId(userId);
        drug.setStockQuantity(10);

        DrugStockChangeDTO changeDTO = new DrugStockChangeDTO();
        changeDTO.setChangeType(2); // 减少
        changeDTO.setChangeQuantity(3);
        changeDTO.setReason("服用");

        when(drugMapper.selectById(drugId)).thenReturn(drug);

        drugService.changeStock(userId, drugId, changeDTO);

        assertEquals(7, drug.getStockQuantity());
        verify(drugMapper).updateById(drug);
        verify(stockLogMapper).insert(any(DrugStockLog.class));
    }

    @Test
    void testChangeStock_InsufficientStock() {
        Long drugId = 1L;
        Long userId = 1L;

        Drug drug = new Drug();
        drug.setId(drugId);
        drug.setUserId(userId);
        drug.setStockQuantity(5);

        DrugStockChangeDTO changeDTO = new DrugStockChangeDTO();
        changeDTO.setChangeType(2); // 减少
        changeDTO.setChangeQuantity(10);

        when(drugMapper.selectById(drugId)).thenReturn(drug);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            drugService.changeStock(userId, drugId, changeDTO);
        });
        assertEquals("库存不足", exception.getMessage());
    }

    @Test
    void testToggleFavorite() {
        Long drugId = 1L;
        Long userId = 1L;

        Drug drug = new Drug();
        drug.setId(drugId);
        drug.setUserId(userId);
        drug.setIsFavorite(0);

        when(drugMapper.selectById(drugId)).thenReturn(drug);

        drugService.toggleFavorite(userId, drugId, 1);

        assertEquals(1, drug.getIsFavorite());
        verify(drugMapper).updateById(drug);
    }

    @Test
    void testDeleteDrug_NoPermission() {
        Long drugId = 1L;
        Long userId = 1L;

        Drug drug = new Drug();
        drug.setId(drugId);
        drug.setUserId(2L); // 不同的用户ID

        when(drugMapper.selectById(drugId)).thenReturn(drug);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            drugService.deleteDrug(userId, drugId);
        });
        assertEquals("药品不存在或无权限", exception.getMessage());
    }
}
