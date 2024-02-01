package mk.ukim.finki.soa.productcatalog.api;

import mk.ukim.finki.soa.productcatalog.model.Product;
import mk.ukim.finki.soa.productcatalog.model.dto.AddDiscountToProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.CharacteristicDto;
import mk.ukim.finki.soa.productcatalog.model.dto.AddProduct;
import mk.ukim.finki.soa.productcatalog.model.dto.ProductDto;
import mk.ukim.finki.soa.productcatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    @GetMapping("/all")
    ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    @GetMapping("/{id}")
    ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    @PostMapping("/add")
    ResponseEntity<Product> addProduct(@RequestBody AddProduct addProduct) {
        return ResponseEntity.ok(productService.addProduct(addProduct));
    }
    @PatchMapping("/characteristic/{id}")
    ResponseEntity<Product> addCharacteristicsToProduct(@PathVariable String id, @RequestBody List<CharacteristicDto> characteristics) {
        return ResponseEntity.ok(productService.addCharacteristics(id, characteristics));
    }
    @PatchMapping("/discount/{id}")
    ResponseEntity<Product> addDiscountToProduct(@PathVariable String id, @RequestBody AddDiscountToProduct addDiscountToProduct) {
        return  ResponseEntity.ok(productService.addDiscount(id, addDiscountToProduct));
    }

}
