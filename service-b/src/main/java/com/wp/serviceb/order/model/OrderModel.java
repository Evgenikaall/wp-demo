package com.wp.serviceb.order.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class OrderModel {

    private final UUID identifier;

    private String status;
    private Long unusedLong;
    private String category;
    private String unusedString;
    private final Instant orderedAt = Instant.now();
}
