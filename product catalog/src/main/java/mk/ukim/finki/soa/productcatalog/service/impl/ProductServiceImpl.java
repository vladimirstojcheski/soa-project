package mk.ukim.finki.soa.productcatalog.service.impl;

import mk.ukim.finki.soa.productcatalog.model.Product;
import mk.ukim.finki.soa.productcatalog.repository.ProductRepository;
import mk.ukim.finki.soa.productcatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        productRepository.save(product);
        return product;
    }
}
