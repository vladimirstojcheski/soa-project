package mk.finki.ukim.soa.ordermanagement.service;

import mk.finki.ukim.soa.ordermanagement.model.Order;
import mk.finki.ukim.soa.ordermanagement.model.dao.OrderDto;

public interface OrderService {
    OrderDto getOrder(String id);
    OrderDto makeOrder(Order order);
}
