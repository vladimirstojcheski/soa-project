package mk.ukim.finki.soa.usermanagement.service.impl;

import jakarta.ws.rs.core.Response;
import mk.ukim.finki.soa.usermanagement.service.KeycloakService;
import mk.ukim.finki.soa.usermanagement.web.KeycloakClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeycloakServiceImpl implements KeycloakService {

    @Autowired
    KeycloakClient keycloakClient;

    @Value("${keycloak.realm}")
    String realm;

    @Value("${keycloak.auth-server-url}")
    String authServerUrl;

    @Value("${keycloak.resource}")
    String client;

    @Value("${keycloak.credentials.secret}")
    String secret;


    @Override
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(authServerUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(client)
                .clientSecret(secret)
                .build();
    }

    private AuthzClient authzClientConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setRealm(realm);
        configuration.setAuthServerUrl(authServerUrl);
        configuration.setResource(client);
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("secret", secret);
        configuration.setCredentials(credentials);
        return AuthzClient.create(configuration);
    }

    @Override
    public AccessTokenResponse auth(String username, String password) {
        AuthzClient authzClient = authzClientConfiguration();
        return authzClient.obtainAccessToken(username, password);
    }

    @Override
    public void addUser(String username, String password, String firstName, String lastName) {
        Keycloak keycloak = getInstance();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(username + "@soa.finki.ukim.mk");
        user.setFirstName(firstName);
        user.setLastName(lastName);
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);
        user.setCredentials(Collections.singletonList(credentialRepresentation));
        user.setEnabled(true);
        Response response = keycloak.realm(realm).users().create(user);
        if (response.getStatus() == 409) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        return credentialRepresentation;
    }

    private UsersResource getKeycloakUserResource() {
        Keycloak kc = getInstance();
        RealmResource realmResource = kc.realm(realm);
        return realmResource.users();
    }

    @Override
    public UserRepresentation getUserRepresentation(String email) {
        return getKeycloakUserResource().search(email).get(0);
    }

    @Override
    public String getCurrentUserToken() {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String role) {
        Keycloak kc = getInstance();
        UsersResource usersResource = getKeycloakUserResource();
        UserRepresentation userRepresentation = getUserRepresentation(username);
        UserResource user = usersResource.get(userRepresentation.getId());
        RealmResource realmResource = kc.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(client).get(0);
        RoleRepresentation userRole = realmResource.clients().get(clientRepresentation.getId()).roles().get(role).toRepresentation();
        List<RoleRepresentation> rl = new ArrayList<>();
        rl.add(userRole);
        user.roles().clientLevel(clientRepresentation.getId()).add(rl);
    }

    @Override
    public AccessToken getCurrentUserTokenInfo() {
        return null;
    }

    @Override
    public List<String> getClaimsForUser(String username) {
        Keycloak kc = getInstance();
        UsersResource usersResource = getKeycloakUserResource();
        UserRepresentation userRepresentation = getUserRepresentation(username);
        UserResource user = usersResource.get(userRepresentation.getId());
        RealmResource realmResource = kc.realm(realm);
        ClientRepresentation clientRepresentation = realmResource.clients().findByClientId(client).get(0);
        return user.roles().clientLevel(clientRepresentation.getId()).listAll().stream()
                .map(RoleRepresentation::getName).collect(Collectors.toList());
    }

    @Override
    public void changePassword(String username, String password) {
        UsersResource usersResource = getKeycloakUserResource();
        usersResource.get(getUserRepresentation(username).getId()).resetPassword(createPasswordCredentials(password));
    }

    @Override
    public void logout() {
        
    }

    @Override
    public void removeTemporaryPasswordTime(String username) {

    }

    @Override
    public AccessTokenResponse refreshToken(String refreshToken) {
        return null;
    }

    @Override
    public void unlockUserAccount(String username) {
        UsersResource usersResource = getKeycloakUserResource();
        UserRepresentation userRepresentation = getUserRepresentation(username);
        userRepresentation.setEnabled(true);
        usersResource.get(userRepresentation.getId()).update(userRepresentation);
    }

    @Override
    public void SetAttribute(String username, String attribute, String value) {

    }

    @Override
    public void removeAttribute(String username, String attribute) {

    }

    @Override
    public String getUserAttribute(String username, String attribute) {
        return null;
    }
}
