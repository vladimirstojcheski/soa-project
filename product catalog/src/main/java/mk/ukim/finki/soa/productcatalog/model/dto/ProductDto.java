package mk.ukim.finki.soa.productcatalog.model.dto;

import jakarta.persistence.*;
import lombok.Data;
import mk.ukim.finki.soa.productcatalog.model.Characteristic;
import mk.ukim.finki.soa.productcatalog.model.Discount;

import java.util.List;

@Data
public class ProductDto {
    private String id;
    private String productName;
    private String productDescription;
    private List<Characteristic> characteristics;
    private Double price;
    private Double discountedPrice;
    private List<Discount> discounts;
}
