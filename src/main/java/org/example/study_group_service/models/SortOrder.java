package org.example.study_group_service.models;

import org.springframework.data.domain.Sort;

public enum SortOrder {
    ASC(Sort.Direction.ASC),
    DESC(Sort.Direction.DESC);

    Sort.Direction direction;

    SortOrder(Sort.Direction direction) {
        this.direction = direction;
    }

    public Sort.Order toSpringSortOrder(String property) {
        return direction == null ? null : new Sort.Order(this.direction, property);
    }

}