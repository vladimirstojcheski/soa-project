package mk.finki.ukim.soa.ordermanagement.model.dao;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String id;
    private ContactDto contact;
    private List<ItemDto> items;
    private Boolean toDeliver;
    private Double totalPrice;
}
