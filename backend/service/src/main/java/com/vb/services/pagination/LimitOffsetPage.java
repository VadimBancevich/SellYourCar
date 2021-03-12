package com.vb.services.pagination;

import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

@EqualsAndHashCode
public class LimitOffsetPage implements Pageable {

    private final int limit;
    private final int offset;
    private final Sort sort;

    protected LimitOffsetPage(int limit, int offset, Sort sort) {
        if (limit < 1)
            throw new IllegalArgumentException("Limit can`t be less than 1");
        if (offset < 0)
            throw new IllegalArgumentException("Offset can`t be less than 0");

        Assert.notNull(sort, "Sort can`t be null");

        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public static LimitOffsetPage of(int limit, int offset, Sort sort) {
        return new LimitOffsetPage(limit, offset, sort);
    }

    public static LimitOffsetPage of(int limit, int offset) {
        return of(limit, offset, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        return offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @NonNull
    @Override
    public Sort getSort() {
        return sort;
    }

    @NonNull
    @Override
    public Pageable next() {
        return new LimitOffsetPage(limit, offset + limit, sort);
    }

    @NonNull
    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? new LimitOffsetPage(limit, offset - limit, sort) : first();
    }

    @NonNull
    @Override
    public Pageable first() {
        return new LimitOffsetPage(limit, 0, sort);
    }

    @Override
    public boolean hasPrevious() {
        return offset - limit > 0;
    }

}

