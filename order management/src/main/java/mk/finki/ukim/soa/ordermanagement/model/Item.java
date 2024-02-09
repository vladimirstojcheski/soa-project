package mk.finki.ukim.soa.ordermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    private String id;

    private String catalog_id;
    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
