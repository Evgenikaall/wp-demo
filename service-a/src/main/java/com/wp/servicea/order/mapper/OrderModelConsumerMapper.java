package com.wp.servicea.order.mapper;

import com.wp.model.Order;
import com.wp.servicea.order.model.OrderModel;
import jdk.jfr.Name;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface OrderModelConsumerMapper {


    @Mapping(source = "payload.status", target = "orderStatus")
    @Mapping(source = "meta.lrtIdentifier", target = "identifier")
    @Mapping(source = "meta.orderedAt", target = "orderedAt")
    @Mapping(source = ".", target = "originalData", qualifiedByName = "mapOrder") // by default avroGenerated.toString returns jsonPayload
    OrderModel map(final Order order);

    @Named("mapOrder")
    static Object mapOrder(final Order order) {
        return order.toString();
    }

}
