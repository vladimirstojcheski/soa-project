package mk.ukim.finki.soa.productcatalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String id;

    private String productName;
    private String productDescription;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_characteristic",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id", referencedColumnName = "characteristic_id"))
    private List<Characteristic> characteristics;

    private Double price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_discount",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "discount_id", referencedColumnName = "discount_id"))
    private List<Discount> discounts;

    private Integer quantity;

}
