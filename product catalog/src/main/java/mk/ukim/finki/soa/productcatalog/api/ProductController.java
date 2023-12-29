package mk.ukim.finki.soa.productcatalog.api;

import mk.ukim.finki.soa.productcatalog.model.Product;
import mk.ukim.finki.soa.productcatalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/all")
    ResponseEntity<String> getAll() {
        productService.addProduct(new Product());
        return ResponseEntity.ok("Test");
    }

}
