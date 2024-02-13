package mk.finki.ukim.soa.ordermanagement.service.impl;

import mk.finki.ukim.soa.ordermanagement.model.Item;
import mk.finki.ukim.soa.ordermanagement.model.Order;
import mk.finki.ukim.soa.ordermanagement.model.dao.OrderDto;
import mk.finki.ukim.soa.ordermanagement.repository.OrderRepository;
import mk.finki.ukim.soa.ordermanagement.service.OrderService;
import mk.finki.ukim.soa.ordermanagement.service.ProductCatalogService;
import mk.finki.ukim.soa.ordermanagement.service.mapper.OrderMapper;
import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductCatalogService productCatalogService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderMapper orderMapper;

    @Override
    public OrderDto getOrder(String id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return orderMapper.mapOrder(order);
    }

    @Override
    public OrderDto makeOrder(Order order) {
        Double price = 0D;
        for (Item item : order.getItems()) {
            ProductDto productDto = productCatalogService.getProductById(item.getCatalog_id());
            price += productDto.getDiscountedPrice();
            item.setPrice(productDto.getDiscountedPrice());
            item.setOrder(order);
        }
        order.setTotalPrice(price);
        orderRepository.save(order);
        return orderMapper.mapOrder(order);
    }
}
