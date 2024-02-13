package mk.finki.ukim.soa.ordermanagement.model.dao;

import lombok.Data;
@Data
public class AddressDto {
    private String country;
    private String city;
    private String street;
    private String postCode;
}
