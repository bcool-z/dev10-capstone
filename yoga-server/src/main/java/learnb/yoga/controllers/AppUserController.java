package learnb.yoga.controllers;

import learnb.yoga.models.AppUser;
import learnb.yoga.security.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private final AppUserService service;

    public AppUserController(AppUserService service) {
        this.service = service;
    }

    @GetMapping("/email/{email}")
    ResponseEntity<AppUser> findByEmail(@PathVariable String email){

        AppUser appUser = service.findByEmail(email);
        if(appUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appUser,HttpStatus.OK);
    }

}
