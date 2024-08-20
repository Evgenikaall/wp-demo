package com.wp.servicea.order.dao;

import com.wp.servicea.order.model.OrderModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderDao {

    void save(final OrderModel orderModel);

    void update(final OrderModel orderModel);
}
