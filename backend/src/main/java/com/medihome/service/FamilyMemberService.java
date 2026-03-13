package com.medihome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medihome.dto.FamilyMemberDTO;
import com.medihome.entity.FamilyMember;

import java.util.List;

/**
 * 家庭成员服务接口
 */
public interface FamilyMemberService extends IService<FamilyMember> {
    
    /**
     * 获取家庭成员列表
     */
    List<FamilyMember> getList(Long userId);
    
    /**
     * 添加家庭成员
     */
    void addMember(Long userId, FamilyMemberDTO memberDTO);
    
    /**
     * 修改家庭成员
     */
    void updateMember(Long userId, Long id, FamilyMemberDTO memberDTO);
    
    /**
     * 删除家庭成员
     */
    void deleteMember(Long userId, Long id);
    
    /**
     * 设置默认成员
     */
    void setDefault(Long userId, Long id);
}
