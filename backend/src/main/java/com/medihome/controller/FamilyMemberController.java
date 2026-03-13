package com.medihome.controller;

import com.medihome.dto.FamilyMemberDTO;
import com.medihome.entity.FamilyMember;
import com.medihome.service.FamilyMemberService;
import com.medihome.utils.UserContext;
import com.medihome.vo.ResultVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 家庭成员控制器
 */
@RestController
@RequestMapping("/api/family-member")
public class FamilyMemberController {
    
    @Autowired
    private FamilyMemberService memberService;
    
    /**
     * 获取家庭成员列表
     */
    @GetMapping("/list")
    public ResultVO<List<FamilyMember>> list() {
        List<FamilyMember> list = memberService.getList(UserContext.getUserId());
        return ResultVO.success(list);
    }
    
    /**
     * 添加家庭成员
     */
    @PostMapping
    public ResultVO<Void> add(@Valid @RequestBody FamilyMemberDTO memberDTO) {
        memberService.addMember(UserContext.getUserId(), memberDTO);
        return ResultVO.success();
    }
    
    /**
     * 修改家庭成员
     */
    @PutMapping("/{id}")
    public ResultVO<Void> update(@PathVariable Long id, @Valid @RequestBody FamilyMemberDTO memberDTO) {
        memberService.updateMember(UserContext.getUserId(), id, memberDTO);
        return ResultVO.success();
    }
    
    /**
     * 删除家庭成员
     */
    @DeleteMapping("/{id}")
    public ResultVO<Void> delete(@PathVariable Long id) {
        memberService.deleteMember(UserContext.getUserId(), id);
        return ResultVO.success();
    }
    
    /**
     * 设置默认成员
     */
    @PutMapping("/{id}/default")
    public ResultVO<Void> setDefault(@PathVariable Long id) {
        memberService.setDefault(UserContext.getUserId(), id);
        return ResultVO.success();
    }
}
