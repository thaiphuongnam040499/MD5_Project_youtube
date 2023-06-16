package rikkei.academy.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rikkei.academy.security.userPrincipal.UserPrincipal;

import java.util.Date;


@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static final String JWT_SECRET_KEY = "thaiphuongnam";
    private static final long JWT_EXPIRATION_TIME = 86400000;
    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+JWT_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,JWT_SECRET_KEY)
                .compact();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            logger.error("Invalid JWT token");
            return false;
        }
    }
    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(JWT_SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
