package mk.finki.ukim.soa.ordermanagement.api;

import mk.finki.ukim.soa.ordermanagement.model.Order;
import mk.finki.ukim.soa.ordermanagement.model.dao.OrderDto;
import mk.finki.ukim.soa.ordermanagement.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @PostMapping("/add")
    public ResponseEntity<OrderDto> makeOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderService.makeOrder(order));
    }
}
