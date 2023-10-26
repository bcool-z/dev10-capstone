package learnb.yoga.controllers;

import learnb.yoga.models.AppUser;
import learnb.yoga.security.AppUserService;
import learnb.yoga.security.JwtConverter;
import learnb.yoga.models.AppUser;
import learnb.yoga.security.JwtConverter;
import learnb.yoga.validation.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final AppUserService appUserService;
    private final AuthenticationManager manager;
    private final JwtConverter converter;

    public AuthController(AppUserService appUserService, AuthenticationManager manager, JwtConverter converter) {
        this.appUserService = appUserService;
        this.manager = manager;
        this.converter = converter;
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AppUser user) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword());

        try {
            Authentication authentication = manager.authenticate(token);
            if (authentication.isAuthenticated()) {
                AppUser authenticatedUser = (AppUser) authentication.getPrincipal();
                String jwt = converter.userToToken(authenticatedUser);
                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwt);
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (AuthenticationException ex) {
            System.out.println(ex.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> credentials) {

        Result<AppUser> result = appUserService.add(
                credentials.get("username"), credentials.get("password"));
        if (result.isSuccess()) {
            Map<String, Integer> userId = new HashMap<>();
            userId.put("user_id", result.getPayload().getAppUserId());
            return new ResponseEntity<>(userId, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal AppUser user){
        String jwt = converter.userToToken(user);
        HashMap<String, String> map = new HashMap<>();
        map.put("jwt_token", jwt);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
