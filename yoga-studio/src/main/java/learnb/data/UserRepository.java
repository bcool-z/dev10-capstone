package learnb.data;

import learnb.model.User;

import java.util.List;

public interface UserRepository {
    User findById(int id);

    User findByEmail(String email_address);

    List<User> findByLastName(String prefix);

    User add(User user);

    boolean update(User user);

    boolean deleteById(int id);
}
