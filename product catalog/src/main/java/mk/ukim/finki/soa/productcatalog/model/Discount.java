package mk.ukim.finki.soa.productcatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "discount_id")
    private String id;

    private LocalDate startDate;
    private LocalDate endDate;
    private Double discountPercentage;

    @ManyToMany(mappedBy = "discounts", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;
}
