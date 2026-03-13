package com.medihome.vo;

import lombok.Data;

import java.util.List;

/**
 * 分页响应VO
 */
@Data
public class PageVO<T> {
    
    private List<T> records;
    private Long total;
    private Long size;
    private Long current;
    private Long pages;
    
    public static <T> PageVO<T> of(List<T> records, Long total, Long size, Long current) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(records);
        pageVO.setTotal(total);
        pageVO.setSize(size);
        pageVO.setCurrent(current);
        pageVO.setPages((total + size - 1) / size);
        return pageVO;
    }
}
