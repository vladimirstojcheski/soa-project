package mk.finki.ukim.soa.ordermanagement.model.dao;

import lombok.Data;

@Data
public class ItemDto {
    private String catalog_id;
    private Double price;
    private Integer quantity;
}
