package com.medihome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medihome.dto.FamilyMemberDTO;
import com.medihome.entity.FamilyMember;
import com.medihome.mapper.FamilyMemberMapper;
import com.medihome.service.FamilyMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 家庭成员服务实现类
 */
@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember> 
    implements FamilyMemberService {
    
    @Override
    public List<FamilyMember> getList(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
    
    @Override
    @Transactional
    public void addMember(Long userId, FamilyMemberDTO memberDTO) {
        FamilyMember member = new FamilyMember();
        BeanUtils.copyProperties(memberDTO, member);
        member.setUserId(userId);
        member.setCreateTime(LocalDateTime.now());
        
        // 如果设置为默认，清除其他默认
        if (member.getIsDefault() != null && member.getIsDefault() == 1) {
            baseMapper.clearDefaultByUserId(userId);
        }
        
        baseMapper.insert(member);
    }
    
    @Override
    @Transactional
    public void updateMember(Long userId, Long id, FamilyMemberDTO memberDTO) {
        FamilyMember member = baseMapper.selectById(id);
        if (member == null || !member.getUserId().equals(userId)) {
            throw new RuntimeException("成员不存在或无权限");
        }
        
        BeanUtils.copyProperties(memberDTO, member);
        member.setId(id);
        
        // 如果设置为默认，清除其他默认
        if (member.getIsDefault() != null && member.getIsDefault() == 1) {
            baseMapper.clearDefaultByUserId(userId);
        }
        
        baseMapper.updateById(member);
    }
    
    @Override
    @Transactional
    public void deleteMember(Long userId, Long id) {
        FamilyMember member = baseMapper.selectById(id);
        if (member == null || !member.getUserId().equals(userId)) {
            throw new RuntimeException("成员不存在或无权限");
        }
        baseMapper.deleteById(id);
    }
    
    @Override
    @Transactional
    public void setDefault(Long userId, Long id) {
        FamilyMember member = baseMapper.selectById(id);
        if (member == null || !member.getUserId().equals(userId)) {
            throw new RuntimeException("成员不存在或无权限");
        }
        
        baseMapper.clearDefaultByUserId(userId);
        
        member.setIsDefault(1);
        baseMapper.updateById(member);
    }
}
