package mk.finki.ukim.soa.ordermanagement.service.impl;

import mk.finki.ukim.soa.ordermanagement.service.SecurityLayerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityLayerServiceImpl implements SecurityLayerService {
    @Override
    public String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
