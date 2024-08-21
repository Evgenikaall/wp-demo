package com.wp.servicea.order.processor;

import com.wp.servicea.order.model.OrderModel;
import com.wp.servicea.order.model.TransportStates;
import com.wp.servicea.order.service.LogisticService;
import com.wp.servicea.order.service.OrderService;
import com.wp.servicea.order.service.SuperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProcessorImpl implements OrderProcessor {

    private final OrderService orderService;

    private final LogisticService logisticService;

    private final SuperService superService;


    @Override
    // very stupid example
    public void process(final OrderModel orderModel) {
        log.info("Save new instance of order with UUID: {}", orderModel.getIdentifier());

        orderService.save(orderModel);

        logisticService.execute(orderModel);

        orderService.update(orderModel);

        superService.execute(orderModel);

        orderService.update(orderModel);

        log.info("Initiate finish of processing Order : {}", orderModel.getIdentifier());

        orderModel.setTransportStatus(TransportStates.SUCCESS);

        orderService.update(orderModel);

        log.info("Order: {} is SUCCESS", orderModel.getIdentifier());

    }
}
