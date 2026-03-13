package com.medihome.controller;

import com.medihome.dto.DrugDTO;
import com.medihome.dto.DrugQueryDTO;
import com.medihome.dto.DrugStockChangeDTO;
import com.medihome.entity.Drug;
import com.medihome.service.DrugService;
import com.medihome.utils.UserContext;
import com.medihome.vo.PageVO;
import com.medihome.vo.ResultVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 药品控制器
 */
@RestController
@RequestMapping("/api/drug")
public class DrugController {
    
    @Autowired
    private DrugService drugService;
    
    /**
     * 分页查询药品
     */
    @GetMapping("/page")
    public ResultVO<PageVO<Drug>> page(DrugQueryDTO queryDTO) {
        PageVO<Drug> page = drugService.pageQuery(UserContext.getUserId(), queryDTO);
        return ResultVO.success(page);
    }
    
    /**
     * 获取药品详情
     */
    @GetMapping("/{id}")
    public ResultVO<Drug> getById(@PathVariable Long id) {
        Drug drug = drugService.getDetail(id);
        return ResultVO.success(drug);
    }
    
    /**
     * 添加药品
     */
    @PostMapping
    public ResultVO<Void> add(@Valid @RequestBody DrugDTO drugDTO) {
        drugService.addDrug(UserContext.getUserId(), drugDTO);
        return ResultVO.success();
    }
    
    /**
     * 修改药品
     */
    @PutMapping("/{id}")
    public ResultVO<Void> update(@PathVariable Long id, @Valid @RequestBody DrugDTO drugDTO) {
        drugService.updateDrug(id, drugDTO);
        return ResultVO.success();
    }
    
    /**
     * 删除药品
     */
    @DeleteMapping("/{id}")
    public ResultVO<Void> delete(@PathVariable Long id) {
        drugService.deleteDrug(UserContext.getUserId(), id);
        return ResultVO.success();
    }
    
    /**
     * 收藏/取消收藏
     */
    @PutMapping("/{id}/favorite")
    public ResultVO<Void> toggleFavorite(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        drugService.toggleFavorite(UserContext.getUserId(), id, params.get("isFavorite"));
        return ResultVO.success();
    }
    
    /**
     * 归档药品
     */
    @PutMapping("/{id}/archive")
    public ResultVO<Void> archive(@PathVariable Long id) {
        drugService.archiveDrug(UserContext.getUserId(), id);
        return ResultVO.success();
    }
    
    /**
     * 调整库存
     */
    @PutMapping("/{id}/stock")
    public ResultVO<Void> changeStock(@PathVariable Long id, @Valid @RequestBody DrugStockChangeDTO changeDTO) {
        drugService.changeStock(UserContext.getUserId(), id, changeDTO);
        return ResultVO.success();
    }
    
    /**
     * 上传图片
     */
    @PostMapping("/upload")
    public ResultVO<String> upload(@RequestParam("file") MultipartFile file) {
        String url = drugService.uploadImage(file);
        return ResultVO.success(url);
    }
    
    /**
     * 获取即将过期药品
     */
    @GetMapping("/expiring")
    public ResultVO<List<Drug>> getExpiring(@RequestParam(defaultValue = "7") Integer days) {
        List<Drug> drugs = drugService.getExpiringDrugs(UserContext.getUserId(), days);
        return ResultVO.success(drugs);
    }
    
    /**
     * 获取已过期药品
     */
    @GetMapping("/expired")
    public ResultVO<List<Drug>> getExpired() {
        List<Drug> drugs = drugService.getExpiredDrugs(UserContext.getUserId());
        return ResultVO.success(drugs);
    }
    
    /**
     * 批量删除
     */
    @DeleteMapping("/batch")
    public ResultVO<Void> batchDelete(@RequestBody Map<String, List<Long>> params) {
        drugService.batchDelete(UserContext.getUserId(), params.get("ids"));
        return ResultVO.success();
    }
}