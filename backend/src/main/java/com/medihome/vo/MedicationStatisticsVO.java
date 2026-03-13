package com.medihome.vo;

import lombok.Data;

import java.util.List;

/**
 * 用药统计VO
 */
@Data
public class MedicationStatisticsVO {
    
    private Long totalCount;
    private List<MemberStatVO> memberStats;
    private List<DrugStatVO> drugStats;
    
    @Data
    public static class MemberStatVO {
        private Long memberId;
        private String memberName;
        private Long count;
    }
    
    @Data
    public static class DrugStatVO {
        private Long drugId;
        private String drugName;
        private Long count;
    }
}
