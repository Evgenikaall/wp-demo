package com.wp.servicea.order.processor;

import com.wp.servicea.order.model.OrderModel;

public interface OrderProcessor {

    void process(final OrderModel orderModel);
}
