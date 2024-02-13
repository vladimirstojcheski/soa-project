package mk.finki.ukim.soa.ordermanagement.service.mapper;

import mk.finki.ukim.soa.ordermanagement.model.Order;
import mk.finki.ukim.soa.ordermanagement.model.dao.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto mapOrder(Order order);
}
