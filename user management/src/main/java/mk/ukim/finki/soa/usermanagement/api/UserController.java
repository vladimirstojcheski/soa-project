package mk.ukim.finki.soa.usermanagement.api;

import mk.ukim.finki.soa.usermanagement.dto.LoginRequest;
import mk.ukim.finki.soa.usermanagement.dto.RegisterRequest;
import mk.ukim.finki.soa.usermanagement.service.KeycloakService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    KeycloakService keycloakService;

    @PostMapping("/login")
    ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(keycloakService.auth(loginRequest.getUsername(), loginRequest.getPassword()));
    }

    @PostMapping("/register")
    void register(@RequestBody RegisterRequest registerRequest) {
        keycloakService.addUser(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getFirstName(), registerRequest.getLastName());
    }
}
