package mk.ukim.finki.soa.usermanagement.web;

import feign.Headers;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloakClient", url = "${keycloakClient.ribbon.listOfServers}")
public interface KeycloakClient {

    @PostMapping(value = "auth/realms/master/protocol/openid-connect-token",
    consumes = "application/x-www-form-urlencoded")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    AccessTokenResponse refreshToken(@RequestBody String credentials);
}
