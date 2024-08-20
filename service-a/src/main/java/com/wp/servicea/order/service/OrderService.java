package com.wp.servicea.order.service;

import com.wp.servicea.order.model.OrderModel;

public interface OrderService {

    void save(final OrderModel orderModel);

    void update(final OrderModel orderModel);

}
