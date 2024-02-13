package mk.finki.ukim.soa.ordermanagement.service.impl;

import mk.finki.ukim.soa.ordermanagement.integration.ProductCatalogClient;
import mk.finki.ukim.soa.ordermanagement.service.ProductCatalogService;
import mk.finki.ukim.soa.ordermanagement.service.SecurityLayerService;
import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    @Autowired
    ProductCatalogClient productCatalogClient;

    @Autowired
    SecurityLayerService securityLayerService;

    @Override
    public ProductDto getProductById(String id) {
        String token = "Bearer " + securityLayerService.getToken();
        ResponseEntity<ProductDto> productResponseEntity = productCatalogClient.getProduct(id, token);
        return productResponseEntity.getBody();
    }
}
