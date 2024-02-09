package mk.finki.ukim.soa.ordermanagement.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contact_id")
    private String id;

    @OneToOne
    private Address address;

    @OneToOne(mappedBy = "contact")
    private Order order;

    private String phoneNumber;

    private String email;
}
