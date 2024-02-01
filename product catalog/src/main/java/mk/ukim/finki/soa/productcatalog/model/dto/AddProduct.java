package mk.ukim.finki.soa.productcatalog.model.dto;

import lombok.Data;

@Data
public class AddProduct {
    private String productName;
    private String productDescription;
    private Double price;
}
