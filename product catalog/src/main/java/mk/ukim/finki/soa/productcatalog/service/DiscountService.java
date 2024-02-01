package mk.ukim.finki.soa.productcatalog.service;

import mk.ukim.finki.soa.productcatalog.model.Discount;
import mk.ukim.finki.soa.productcatalog.model.dto.AddDiscount;

public interface DiscountService {
    Discount addDiscount(AddDiscount addDiscount);
    Discount findById(String id);
}
