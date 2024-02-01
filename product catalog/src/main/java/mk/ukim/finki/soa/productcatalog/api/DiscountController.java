package mk.ukim.finki.soa.productcatalog.api;

import mk.ukim.finki.soa.productcatalog.model.Discount;
import mk.ukim.finki.soa.productcatalog.model.dto.AddDiscount;
import mk.ukim.finki.soa.productcatalog.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/discount")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @PostMapping("/add")
    ResponseEntity<Discount> addDiscount(@RequestBody AddDiscount addDiscount) {
        return ResponseEntity.ok(discountService.addDiscount(addDiscount));
    }
}
