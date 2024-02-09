package mk.finki.ukim.soa.ordermanagement.service;

import mk.finki.ukim.soa.ordermanagement.model.Order;

public interface OrderService {
    Order getOrder(String id);
    Order makeOrder(Order order);
}
