package com.wp.servicea.order.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

import static com.wp.servicea.order.model.TransportStates.RECEIVED;

@Data
public class OrderModel {

    private final UUID identifier;

    private TransportStates transportStatus = RECEIVED;

    private final String orderStatus;

    private final Instant orderedAt;

    // Represents initial received payload
    // Can be created copy of initial object, but not mandatory for this example
    private final Object originalData;

}
