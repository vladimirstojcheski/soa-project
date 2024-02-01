package mk.ukim.finki.soa.productcatalog.service;

import mk.ukim.finki.soa.productcatalog.model.Product;
import mk.ukim.finki.soa.productcatalog.model.dto.AddDiscountToProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.CharacteristicDto;
import mk.ukim.finki.soa.productcatalog.model.dto.AddProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    ProductDto getProductById(String id);
    Product addProduct(AddProduct product);
    Product addCharacteristics(String productId, List<CharacteristicDto> characteristics);
    Product addDiscount(String productId, AddDiscountToProduct addDiscountToProduct);
}
