package mk.ukim.finki.soa.productcatalog.service.mapper;

import mk.ukim.finki.soa.productcatalog.model.Discount;
import mk.ukim.finki.soa.productcatalog.model.Product;
import mk.ukim.finki.soa.productcatalog.model.dto.AddProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "productDescription", target = "productDescription")
    @Mapping(source = "price", target = "price")
    Product toProduct(AddProduct addProduct);

    @Mapping(source = "discounts", target = "discountedPrice", qualifiedByName = "getDiscountedPrice")
    ProductDto mapProductDto(Product product, @Context Double price);

    @Named("getDiscountedPrice")
    static Double mapDiscountedPrice(List<Discount> discounts, @Context Double price) {
        LocalDate now = LocalDate.now();
        Discount discount = discounts.stream().filter(d -> now.isAfter(d.getStartDate()) && now.isBefore(d.getEndDate()))
                .findFirst().orElse(null);
        if (discount != null) {
            price -= (price * discount.getDiscountPercentage()) / 100;
            return price;
        }
        return price;
    }
}
