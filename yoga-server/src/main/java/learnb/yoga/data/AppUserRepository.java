package learnb.yoga.data;

import learnb.yoga.models.AppUser;

public interface AppUserRepository {
    AppUser findById(int id);

    AppUser findByEmail(String email_address);

//    List<AppUser> findByLastName(String prefix);

    AppUser add(AppUser user);

    boolean update(AppUser user);

    boolean deleteById(int id);
}
