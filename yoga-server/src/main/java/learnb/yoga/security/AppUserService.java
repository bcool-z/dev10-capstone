package learnb.yoga.security;

import learnb.yoga.data.AppUserJdbcTemplateRepository;
import learnb.yoga.domain.ActionStatus;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.UserType;
import learnb.yoga.validation.Result;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserJdbcTemplateRepository repository;

    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserJdbcTemplateRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    public AppUser findByEmail(String email){

        return repository.findByEmail(email);
    }


    public AppUser findById(int id){

        return repository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found", username));
        }
        return user;
    }

    public Result<AppUser> add(String username, String password) {
        Result<AppUser> result = validate(username, password);
        if (!result.isSuccess()) {
            return result;
        }

        password = passwordEncoder.encode(password);

        AppUser appUser = new AppUser(0, username, "0", "0", LocalDate.now().minusYears(100),"0",UserType.STUDENT,password);
//        AppUser appUser = new AppUser();
//        appUser.setUsername(username);
//        appUser.setPassword(password);
//        appUser.setUserType(UserType.STUDENT);

        result.setPayload(repository.add(appUser));

        return result;
    }

    public Result<AppUser> update(AppUser appUser){
        Result result = new Result();
       if(!repository.update(appUser)){
           result.addMessage(ActionStatus.INVALID, "update unsuccessful");
            return result;
       }
       result.setPayload(appUser);
       return result;

    }

    private Result<AppUser> validate(String username, String password) {
        Result<AppUser> result = new Result<>();

        if (username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "username is required");
        }

        if (password == null || password.isBlank()) {
            result.addMessage(ActionStatus.INVALID,"password is required");
        }

        if (!result.isSuccess()) {
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ActionStatus.INVALID,"username must be 50 characters max");
        }

        if (!validatePassword(password)) {
            result.addMessage(ActionStatus.INVALID,"password must be at least 8 character and contain a digit, a letter, and a non-digit/non-letter");
        }

        if (!result.isSuccess()) {
            return result;
        }

        try {
            if (loadUserByUsername(username) != null) {
                result.addMessage(ActionStatus.INVALID,"the provided username already exists");
            }
        } catch (UsernameNotFoundException e) {
            // good!
        }

        return result;
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }

}
