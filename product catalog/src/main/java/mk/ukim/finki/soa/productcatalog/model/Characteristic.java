package mk.ukim.finki.soa.productcatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "characteristic")
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "characteristic_id")
    private String id;
    private String name;
    private String value;

    @ManyToMany(mappedBy = "characteristics", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;
}
