package mk.finki.ukim.soa.ordermanagement.service.impl;

import mk.finki.ukim.soa.ordermanagement.model.Order;
import mk.finki.ukim.soa.ordermanagement.repository.OrderRepository;
import mk.finki.ukim.soa.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order getOrder(String id) {
        return null;
    }

    @Override
    public Order makeOrder(Order order) {
        return null;
    }
}
