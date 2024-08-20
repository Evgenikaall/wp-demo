package com.wp.servicea.order.service;

import com.wp.servicea.order.model.OrderModel;
import com.wp.servicea.order.model.TransportStates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LogisticServiceImpl implements LogisticService {

    @Override
    public void execute(final OrderModel orderModel) {
        log.info("Start to process Order: {}", orderModel.getIdentifier());

        orderModel.setTransportStatus(TransportStates.PROCESSED_BY_LOGISTIC_SERVICE);

        log.info("Order: {} is PROCESSED_BY_LOGISTIC_SERVICE", orderModel.getIdentifier());
    }
}
