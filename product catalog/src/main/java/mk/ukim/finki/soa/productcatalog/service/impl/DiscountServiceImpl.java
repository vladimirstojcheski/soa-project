package mk.ukim.finki.soa.productcatalog.service.impl;

import mk.ukim.finki.soa.productcatalog.model.Discount;
import mk.ukim.finki.soa.productcatalog.model.dto.AddDiscount;
import mk.ukim.finki.soa.productcatalog.repository.DiscountRepository;
import mk.ukim.finki.soa.productcatalog.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    DiscountRepository discountRepository;

    @Override
    public Discount addDiscount(AddDiscount addDiscount) {
        Discount discount = new Discount();
        discount.setDiscountPercentage(addDiscount.getDiscountPercentage());
        discount.setStartDate(addDiscount.getStartDate());
        discount.setEndDate(addDiscount.getEndDate());
        if (!checkDiscountDateRange(discount)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Wrong dates");
        }
        return discountRepository.save(discount);
    }

    @Override
    public Discount findById(String id) {
        return discountRepository.findById(id).orElse(null);
    }

    private boolean checkDiscountDateRange(Discount discount) {
        return discount.getEndDate().isAfter(discount.getStartDate()) && discount.getEndDate().isAfter(LocalDate.now());
    }
}
