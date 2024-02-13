package mk.finki.ukim.soa.ordermanagement.service;

import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;

public interface ProductCatalogService {
    ProductDto getProductById(String id);
}
