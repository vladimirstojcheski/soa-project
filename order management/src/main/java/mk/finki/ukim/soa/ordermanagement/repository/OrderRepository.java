package mk.finki.ukim.soa.ordermanagement.repository;

import mk.finki.ukim.soa.ordermanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
