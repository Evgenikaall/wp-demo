package com.wp.serviceb.order.service;

import com.wp.serviceb.order.model.OrderModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderReservationServiceImpl implements OrderReservationService {

    @Override
    // ignore this
    public Optional<OrderModel> prepareData(final UUID identifier) {
        return Optional.ofNullable(OrderModel.builder()
                .identifier(identifier)
                .category("EVENT")
                .status("INITIATED")
                .unusedLong(1L)
                .unusedString("SSSS")
                .build());
    }
}
