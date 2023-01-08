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
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final EmployeeService employeeService;

    private int tokenLifespan = 1;

    private String issuerName = "comExampleCompany";

    private String secretKey = "temporarySecretKeyThatHasToBeStoredInAppPropsFowNowBetterASecuredPlace";

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
            Jws<Claims> jws = null;
            jws = Jwts.parserBuilder()
                    .setSigningKey(generateSecreteKey())
                    .build()
                    .parseClaimsJws(jwtAuthToken);

            if ((jws.getBody().getIssuer().equals(issuerName))
                    && (jws.getBody().getExpiration().compareTo(new Date()) > 0)
                    && (jws.getBody().getNotBefore().compareTo(new Date()) < 0)) {

                return true;
            }

            return false;
        } catch (JwtException e) {
            return false;
        }
        catch (Exception e) {
            throw new AuthenticationException("Invalid JWT token signature");
        }

    }

    // Generate JWS token
    AuthResponse generateJWSToken(AuthRequest request) throws IOException {

        AuthResponse response = AuthResponse.builder().build();


        // Build payload

        // Generate token id.
        String tokenId = "Time_Tracking_" + UUID.randomUUID().toString();
        // Generate payload subject using application related sensitive values.
        String tokenPayload = populatePayload(request);
        // Generate token issued date.
        Calendar cal = Calendar.getInstance();
        Date tokenIssuedAt = cal.getTime();
        // Generate token expiration time.
        cal.add(Calendar.SECOND, tokenLifespan);
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
        JSONObject subjJson = new JSONObject();
        subjJson.put("USER-EMAIL", request.getEmail());
        return subjJson.toString();
    }

    private SecretKey generateSecreteKey() {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return key;

    }

}
