package com.vb.api.dao.impl;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class LightPage<T> {

    private final List<T> content;
    private final Integer countPages;
    private final Long total;
    private final Integer pageNumber;
    private final Integer pageSize;

    public LightPage(List<T> content, int pageNumber, int pageSize, long total) {
        this.content = content;
        this.countPages = (int) Math.ceil((double) total / (double) pageSize);
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
    }

    public LightPage(List<T> content, Pageable pageable, long total) {
        this(content, pageable.getPageNumber() + 1, pageable.getPageSize(), total);
    }

    public List<T> getContent() {
        return content;
    }

    public Long getTotal() {
        return total;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getCountPages() {
        return countPages;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }
}
