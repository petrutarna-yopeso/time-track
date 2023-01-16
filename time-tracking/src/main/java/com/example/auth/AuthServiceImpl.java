package com.example.auth;

import com.example.employee.EmployeeEntity;
import com.example.employee.EmployeeService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.security.sasl.AuthenticationException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final EmployeeService employeeService;

    private final String issuerName = "comExampleCompany";

    @Override
    public AuthResponse authorize(AuthRequest req) throws Exception {
        Optional<EmployeeEntity> employee = employeeService.findByEmail(req.getEmail());
        if(employee.isPresent()){
            return generateJWSToken(req);
        }
       throw new Exception();
    }

    @Override
    public Boolean validateJwtToken(String jwtAuthToken) throws AuthenticationException {

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(generateSecreteKey())
                    .build()
                    .parseClaimsJws(jwtAuthToken);

            return (jws.getBody().getIssuer().equals(issuerName))
                    && (jws.getBody().getExpiration().compareTo(new Date()) > 0)
                    && (jws.getBody().getNotBefore().compareTo(new Date()) < 0);
        } catch (JwtException e) {
            return false;
        }
        catch (Exception e) {
            throw new AuthenticationException("Invalid JWT token signature");
        }

    }

    // Generate JWS token
    AuthResponse generateJWSToken(AuthRequest request) {

        AuthResponse response = AuthResponse.builder().build();


        // Build payload

        // Generate token id.
        String tokenId = "Time_Tracking_" + UUID.randomUUID();
        // Generate payload subject using application related sensitive values.
        String tokenPayload = populatePayload(request);
        // Generate token issued date.
        Calendar cal = Calendar.getInstance();
        Date tokenIssuedAt = cal.getTime();
        // Generate token expiration time.
        int tokenLifespanInSeconds = 360;
        cal.add(Calendar.SECOND, tokenLifespanInSeconds);
        Date tokenExpirationAt = cal.getTime();

        String jws = Jwts.builder().setId(tokenId).setSubject(tokenPayload).setIssuer(issuerName)
                .setIssuedAt(tokenIssuedAt).setNotBefore(tokenIssuedAt).setExpiration(tokenExpirationAt)
                .signWith(generateSecreteKey())
                .compact();
        response.setJwtToken(jws);

        return response;

    }

    // Populate 'subject' field using application sensitive data.
    private String populatePayload(AuthRequest request) {
        HashMap<String, String> subjJson = new HashMap<>();
        subjJson.put("USER-EMAIL", request.getEmail());
        return subjJson.toString();
    }

    private SecretKey generateSecreteKey() {

        String secretKey = "temporarySecretKeyThatHasToBeStoredInAppPropsFowNowBetterASecuredPlace";

        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    }

}
