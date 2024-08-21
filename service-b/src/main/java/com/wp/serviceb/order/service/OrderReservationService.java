package com.wp.serviceb.order.service;

import com.wp.serviceb.order.model.OrderModel;

import java.util.Optional;
import java.util.UUID;

public interface OrderReservationService {

    Optional<OrderModel> prepareData(final UUID identifier);
}
