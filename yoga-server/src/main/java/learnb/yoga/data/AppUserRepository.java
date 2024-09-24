package learnb.yoga.data;

import learnb.yoga.models.AppUser;

import java.util.List;

public interface AppUserRepository {
    AppUser findById(int id);

    AppUser findByEmail(String emailAddress);

    List<AppUser> searchByEmail(String query);
    List<AppUser> searchByPhone(String query);
    List<AppUser> searchByName(String[] query);

    AppUser add(AppUser user);

    boolean update(AppUser user);

    boolean deleteById(int id);
}
