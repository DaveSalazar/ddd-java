package com.application.apps.administration.utils;

import com.application.shared.infrastructure.config.Parameter;
import com.application.shared.infrastructure.config.ParameterNotExist;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final Parameter param;

    public JwtUtil(Parameter param) {
        this.param = param;
    }

    public String extractUsername(String token) throws ParameterNotExist {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) throws ParameterNotExist {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws ParameterNotExist {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) throws ParameterNotExist {
        return Jwts.parser().setSigningKey(param.get("ADMINISTRATION_JWT_SECRET")).parseClaimsJws(token).getBody();
    }

    public boolean validateVersion(String token) throws ParameterNotExist {
        Claims claim = extractAllClaims(token);
        String tokenVersion = claim.get("app_version") != null ? claim.get("app_version").toString() : "";
        String appVersion = tokenVersion != null ? param.get("ADMINISTRATION_FRONTEND_VERSION") : "";
        return tokenVersion.equals(appVersion);
    }

    public String getClaim(String token, String claimName) throws ParameterNotExist {
        Claims claim = extractAllClaims(token);
        String claimValue = null;
        if( claim.get(claimName) != null) {
            claimValue = claim.get(claimName).toString();
        }
        return claimValue;
    }

    public Claims getAllClaims(String token) throws ParameterNotExist {
        return Jwts.parser().setSigningKey(param.get("ADMINISTRATION_JWT_SECRET")).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) throws ParameterNotExist {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String userEmail, String userId, String appVersion) throws ParameterNotExist {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        claims.put("app_version", appVersion);
        return createToken(claims, userEmail);
    }

    public String generateRefreshToken(String userEmail, String userId, String appVersion) throws ParameterNotExist {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userId);
        claims.put("app_version", appVersion);
        return createRefreshToken(claims, userEmail);
    }

    private Key getSigningKey() throws ParameterNotExist {
        byte[] keyBytes = Decoders.BASE64.decode(param.get("ADMINISTRATION_JWT_SECRET"));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Map<String, Object> claims, String subject) throws ParameterNotExist {

        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *60 *10))
                .signWith(getSigningKey()).compact();
    }


    public String createRefreshToken(Map<String, Object> claims, String subject) throws ParameterNotExist {
        LocalDateTime ldt = LocalDateTime.now().plusDays(7);

        return Jwts.builder().setClaims(claims).setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(Timestamp.valueOf(ldt))
            .signWith(getSigningKey()).compact();
    }


    public Boolean validateToken(String token, String user) throws ParameterNotExist {
        final String username = extractUsername(token);
        return (username.equals(user) && !isTokenExpired((token)) );
    }

    public Optional<Authentication> createAuthentication(String token) {

        Jws<Claims> jwsClaims = validateToken(token);
        if (jwsClaims == null) {
            return Optional.empty();
        }

        Claims claims = jwsClaims.getBody();

        List<GrantedAuthority> authorities = new ArrayList<>();
        String subject = claims.getSubject();
        User principal = new User(subject, "", authorities);

        return Optional.of(new UsernamePasswordAuthenticationToken(principal, token, authorities));
    }

    private Jws<Claims> validateToken(String authToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(param.get("ADMINISTRATION_JWT_SECRET"))
                    .parseClaimsJws(authToken);
            return claims;
        } catch (Exception | ParameterNotExist e) {
            e.printStackTrace();
            return null;
        }
    }
}
