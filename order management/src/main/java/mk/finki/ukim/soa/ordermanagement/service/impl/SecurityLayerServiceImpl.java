package mk.finki.ukim.soa.ordermanagement.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import mk.finki.ukim.soa.ordermanagement.service.SecurityLayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SecurityLayerServiceImpl implements SecurityLayerService {
    @Override
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public String getUsername() {
        JWT jwt = new JWT();
        DecodedJWT decodedJWT = jwt.decodeJwt(getToken());
        Map<String, Claim> map = decodedJWT.getClaims();
        return map.get("preferred_username").asString();
    }
}
