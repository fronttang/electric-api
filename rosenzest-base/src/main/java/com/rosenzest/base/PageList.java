package com.rosenzest.base;

import java.io.Serializable;

import lombok.Data;

@Data
public class PageList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3288728405314169194L;

    private int pageNum;

    private int pageSize;

    private String orderBy;

    private long totalNum;

    public PageList(int pageNum, int pageSize, String orderBy) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
    }

    public PageList(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
