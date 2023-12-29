package mk.ukim.finki.soa.productcatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double discountPercentage;
}
