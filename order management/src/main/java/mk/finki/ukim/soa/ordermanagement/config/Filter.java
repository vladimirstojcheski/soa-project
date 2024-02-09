package mk.finki.ukim.soa.ordermanagement.config;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Component
public class Filter extends OncePerRequestFilter {

    String publicKey;

    @Autowired
    public Filter(Environment env) {
        publicKey = env.getProperty("public.key");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestTokenHeader = request.getHeader("Authorization");
        JWTClaimsSet claimsSet;
        String msg = "";
        if (!request.getMethod().equals("OPTIONS")) {
            if (requestTokenHeader == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Request header Authorization");
            } else {
                requestTokenHeader = requestTokenHeader.replace("Bearer ", "");
                try {
                    RSAPublicKey publicKey = parse(this.publicKey);
                    SignedJWT signedJWT = SignedJWT.parse(requestTokenHeader);
                    JWSVerifier verifier = new RSASSAVerifier(publicKey);
                    if (signedJWT.verify(verifier)) {
                        claimsSet = signedJWT.getJWTClaimsSet();
                        Date expirationTime = claimsSet.getExpirationTime();
                        Date now = new Date();
                        if (expirationTime != null && expirationTime.before(now)) {
                            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token is expired");
                        }
                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token signature verification failed");
                    }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            requestTokenHeader, null, new ArrayList<>());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                catch (ResponseStatusException e) {
                    msg = e.getReason();
                }
                catch (Exception e) {
                    msg = e.getMessage();
                }

                if (msg != null && !msg.isEmpty()) {
                    String sb = "{ " +
                            "\"error\": \"Unauthorized\"," +
                            "\"message\": " + "\"" + msg + "\"" + "," +
                            "\"path\": \"" +
                            request.getRequestURL() +
                            "\"" +
                            "} ";
                    response.setContentType("application/json");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write(sb);
                    return;
                }
                filterChain.doFilter(request, response);
            }
        }
    }

    private RSAPublicKey parse(String publicKeyString) throws Exception {
        String publicKeyPEM = publicKeyString
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\n", "");

        byte[] encoded = Base64.getDecoder().decode(publicKeyPEM);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }
}