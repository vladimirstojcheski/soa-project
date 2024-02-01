package mk.ukim.finki.soa.productcatalog.service.impl;

import mk.ukim.finki.soa.productcatalog.model.Characteristic;
import mk.ukim.finki.soa.productcatalog.model.Discount;
import mk.ukim.finki.soa.productcatalog.model.Product;
import mk.ukim.finki.soa.productcatalog.model.dto.AddDiscountToProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.CharacteristicDto;
import mk.ukim.finki.soa.productcatalog.model.dto.AddProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;
import mk.ukim.finki.soa.productcatalog.repository.ProductRepository;
import mk.ukim.finki.soa.productcatalog.service.CharacteristicService;
import mk.ukim.finki.soa.productcatalog.service.DiscountService;
import mk.ukim.finki.soa.productcatalog.service.ProductService;
import mk.ukim.finki.soa.productcatalog.service.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CharacteristicService characteristicService;

    @Autowired
    DiscountService discountService;

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductDto> products = new ArrayList<>();
        productRepository.findAll().forEach(p -> {
            ProductDto productDto = productMapper.mapProductDto(p, p.getPrice());
            products.add(productDto);
        });
        return products;
    }

    @Override
    public ProductDto getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return productMapper.mapProductDto(product, product.getPrice());
    }

    @Override
    public Product addProduct(AddProduct addProduct) {
        Product product = productMapper.toProduct(addProduct);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product addCharacteristics(String productId, List<CharacteristicDto> characteristics) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        characteristics.forEach(c -> {
            Characteristic characteristic = characteristicService.getCharacteristic(c);
            if (product.getCharacteristics().stream().noneMatch(x -> x.getId().equals(characteristic.getId()))) {
                product.getCharacteristics().add(characteristic);
            }
        });
        return productRepository.save(product);
    }

    @Override
    public Product addDiscount(String productId, AddDiscountToProduct addDiscountToProduct) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        Discount discount = discountService.findById(addDiscountToProduct.getId());
        if (discount == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discount not found");
        }
        if (!checkDiscountData(product, discount)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "There is another discount in the timeframe");
        }
        product.getDiscounts().add(discount);
        return productRepository.save(product);
    }
    private boolean checkDiscountData(Product product, Discount discount) {
        for (Discount d : product.getDiscounts()) {
            if  ((discount.getStartDate().isAfter(d.getStartDate()) && discount.getEndDate().isBefore(d.getEndDate())) ||
                    (discount.getStartDate().isBefore(d.getStartDate()) && discount.getEndDate().isBefore(d.getEndDate()) && discount.getEndDate().isAfter(d.getStartDate())) ||
                    (discount.getStartDate().isAfter(d.getStartDate()) && discount.getEndDate().isAfter(d.getEndDate()) && discount.getStartDate().isBefore(d.getEndDate())) ||
                    (discount.getStartDate().isEqual(d.getStartDate()) || discount.getEndDate().isEqual(d.getEndDate()))) {
                return false;
            }
        }
        return true;
    }

}
