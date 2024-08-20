package com.wp.servicea.order.service;

import com.wp.servicea.order.dao.OrderDao;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Delegate
    private final OrderDao orderDao;
}
