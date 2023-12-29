package mk.ukim.finki.soa.productcatalog.config;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;

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
        if (!request.getMethod().equals("OPTIONS")) {
            if (requestTokenHeader == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Request header Authorization");
            } else {
                requestTokenHeader = requestTokenHeader.replace("Bearer ", "");
                try {
                    RSAPublicKey publicKey = parse(this.publicKey);
                    // Parse the token
                    SignedJWT signedJWT = SignedJWT.parse(requestTokenHeader);
                    // Verify the signature
                    JWSVerifier verifier = new RSASSAVerifier(publicKey);
                    if (signedJWT.verify(verifier)) {
                        // Signature is valid, now you can extract claims from the token
                        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                    } else {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token signature verification failed");
                    }
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            requestTokenHeader, null, new ArrayList<>());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);

                } catch (Exception e) {
//                    LOGGER.error(e.getMessage());
                    System.out.println("Error");
                }
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
