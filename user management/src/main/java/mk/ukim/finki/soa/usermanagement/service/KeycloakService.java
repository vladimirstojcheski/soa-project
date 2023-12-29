package mk.ukim.finki.soa.usermanagement.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakService {
    Keycloak getInstance();
    AccessTokenResponse auth(String username, String password);
    void addUser(String username, String password, String firstName, String lastName);
    UserRepresentation getUserRepresentation(String email);
    String getCurrentUserToken();
    void addRoleToUser(String username, String role);
    AccessToken getCurrentUserTokenInfo();
    List<String> getClaimsForUser(String username);
    void changePassword(String username, String password);
    void logout();
    void removeTemporaryPasswordTime(String username);
    AccessTokenResponse refreshToken(String refreshToken);
    void unlockUserAccount(String username);
    void SetAttribute(String username, String attribute, String value);
    void removeAttribute(String username, String attribute);
    String getUserAttribute(String username, String attribute);

}
