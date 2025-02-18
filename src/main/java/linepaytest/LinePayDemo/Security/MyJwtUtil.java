package linepaytest.LinePayDemo.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.security.Key;

import org.springframework.stereotype.Component;

@Component
public class MyJwtUtil {
    
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000;

    // generateToken 方法會回傳一個 JWT 字串，這個 JWT 字串是用來驗證使用者身份的
    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SECRET_KEY)
            .compact();
    }

    // getEmailFromToken 方法會回傳一個使用者的 email，這個 email 是透過 JWT 字串解析出來的
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SECRET_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    // validateToken 方法會回傳一個布林值，這個布林值是用來判斷 JWT 字串是否有效的
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token);
                return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    // extracToken 方法會回傳一個 JWT 字串，這個 JWT 字串是從 HTTP 請求中取出來的
    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
