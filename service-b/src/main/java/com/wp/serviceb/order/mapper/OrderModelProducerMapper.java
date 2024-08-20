package com.wp.serviceb.order.mapper;

import com.wp.model.Order;
import com.wp.serviceb.order.model.OrderModel;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OrderModelProducerMapper {
    @Mapping(target = "meta.lrtIdentifier", source = "identifier")
    @Mapping(target = "meta.orderedAt", source = "orderedAt")
    @Mapping(target = "meta.category", source = "category")
    @Mapping(target = "payload.status", source = "status")
    @Mapping(target = "payload.unusedLong", source = "unusedLong")
    @Mapping(target = "payload.unusedString", source = "unusedString")
    Order map(final OrderModel data);



}
