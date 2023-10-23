package learnb.yoga.security;

import learnb.yoga.data.AppUserJdbcTemplateRepository;
import learnb.yoga.models.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserJdbcTemplateRepository repository;

    public AppUserService(AppUserJdbcTemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s not found", username));
        }
        return user;
    }
}
