package mk.finki.ukim.soa.ordermanagement.model.dao;

import lombok.Data;

@Data
public class ContactDto {
    private AddressDto address;
    private String phoneNumber;
    private String email;
}
