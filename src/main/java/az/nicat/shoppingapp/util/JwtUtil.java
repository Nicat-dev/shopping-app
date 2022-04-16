package az.nicat.shoppingapp.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
public class JwtUtil {

    private static Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private final Clock clock = DefaultClock.INSTANCE;

    @Value("${secret-key}")
    private String secretKey;

    @Value("${expiration-time}")
    private long expirationTime;

    private <T> T getClaimForToken(String token, Function<Claims, T> claimsResolver) {
        Claims claims = getAllClaimsForToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsForToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String[] roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toArray(String[]::new);
        claims.put("roles", roles);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        Date createdDate = clock.now();// indiki vaxt
        Date expirationDate = new Date(System.currentTimeMillis() + expirationTime);// indiki vaxt + icaze vaxti

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(HS512, secretKey)
                .compact();
    }

    public String refreshToken(String token) {
        Claims claims = getAllClaimsForToken(token);
        String username = getUsernameFromToken(token);
        return doGenerateToken(claims,username);
    }

    public boolean canTokenBeRefreshed(String token) {
        return isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return !expiration.before(clock.now());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimForToken(token, Claims::getExpiration);
    }

    public String getUsernameFromToken(String token) {
        return getClaimForToken(token, Claims::getSubject);
    }

    public void validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
    }
}
