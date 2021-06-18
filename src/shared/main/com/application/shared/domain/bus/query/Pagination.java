package com.application.shared.domain.bus.query;

import java.util.Optional;

public abstract class Pagination {

    private final Optional<Integer> limit;
    private final Optional<Integer> offset;
    private final Optional<Integer> totalRecords;

    public Pagination(Optional<Integer> limit, Optional<Integer> offset, Optional<Integer> totalRecords) {
        this.limit = limit;
        this.offset = offset;
        this.totalRecords = totalRecords;
    }

    public Optional<Integer> limit() {
        return limit;
    }

    public Optional<Integer> offset() {
        return offset;
    }

    public Optional<Integer> totalRecords() {
        return totalRecords;
    }
}
