package mk.ukim.finki.soa.productcatalog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String productName;
    private String productDescription;
    @OneToMany
    private List<Characteristic> characteristics;
    private Double price;

}
