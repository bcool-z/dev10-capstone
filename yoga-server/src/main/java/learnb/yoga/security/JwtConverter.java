package learnb.yoga.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.UserType;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class JwtConverter {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final String ISSUER = "yoga-server";
    private static final int EXPIRATION_MINUTES = 15;
    private static final int EXPIRATION_MILLISECONDS = EXPIRATION_MINUTES * 60 * 1000;

    public String userToToken(AppUser user) {

        List<String> authorities = user.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList();

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                .claim("appUserId", user.getAppUserId())
                .claim("userType",user.getUserType())
                .claim("firstName",user.getFirstName())
                .claim("lastName",user.getLastName())
                .claim("phoneNumber",user.getPhoneNumber())
                .claim("dob",user.getDob().toString())
                .claim("authorities", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLISECONDS))
                .signWith(key)
                .compact();
    }

    public AppUser tokenToUser(String token) { // Bearer eyJu...

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();
            int appUserId = jws.getBody().get("appUserId", Integer.class);
           UserType userType = UserType.valueOf(jws.getBody().get("userType", String.class));
           String firstName = jws.getBody().get("firstName",String.class);
            String lastName = jws.getBody().get("lastName",String.class);
            String phoneNumber = jws.getBody().get("phoneNumber",String.class);
            LocalDate dob = LocalDate.parse(jws.getBody().get("dob", String.class));

            AppUser user = new AppUser();
            user.setEmailAddress(username);
            user.setAppUserId(appUserId);
            user.setUserType(userType);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setDob(dob);
            return user;

        } catch (JwtException ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }
}
