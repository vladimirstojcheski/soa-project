package mk.finki.ukim.soa.ordermanagement.integration;

import mk.finki.ukim.soa.ordermanagement.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "productCatalogClient", url = "${productCatalogClient.url}")
public interface ProductCatalogClient {
    @GetMapping(value = "/product/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Order> getProduct(@PathVariable("id") String id,
                                     @RequestHeader("Authorization") String token);
}
