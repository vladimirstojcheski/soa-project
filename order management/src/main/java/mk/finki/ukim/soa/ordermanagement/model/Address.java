package mk.finki.ukim.soa.ordermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "address_id")
    private String id;

    private String country;
    private String city;
    private String street;
    private String postCode;
}
