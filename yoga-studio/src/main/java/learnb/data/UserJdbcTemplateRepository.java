package learnb.data;

import learnb.data.mappers.UserMapper;
import learnb.model.User;
import learnb.model.UserType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {


    private final JdbcTemplate jdbcTemplate;


    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

@Override
public User findById(int id) {

        final String sql = """
                
                select user_id, first_name, last_name, dob, phone_number, 
                email_address, type from users where user_id = ?; 
                """;


            return jdbcTemplate.query(sql, new UserMapper(), id).stream().findFirst().orElse(null);


}

@Override
public User findByEmail(String email_address) {

        final String sql = """
                
                select user_id, first_name, last_name, dob, phone_number, 
                email_address, type from users where email_address = ?;
                """;

    return jdbcTemplate.query(sql, new UserMapper(), email_address).stream().findFirst().orElse(null);
}

@Override
public List<User> findByLastName(String prefix) {

        final String sql = """
                
                select user_id, first_name, last_name, dob, phone_number, 
                email_address, type from users where user_id like ?;
                
                """;

        return jdbcTemplate.query(sql, new UserMapper(), prefix + "%");

}

@Override
public User add(User user){

    final String sql = """ 
         insert into users
         (first_name, last_name, dob, phone_number, email_address, type)
         values (?,?,?,?,?)
""";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    int rowsAffected = jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1,user.getFirst_name());
        ps.setString(2, user.getLast_name());
        ps.setDate(3, Date.valueOf(user.getDob()));
        ps.setString(4, user.getPhone_number());
        ps.setString(5,user.getEmail_address());
        ps.setString(6,user.getType().getLabel());
        return ps;
    },keyHolder);

    if (rowsAffected <= 0) {
        return null;
    }
    user.setUser_id(keyHolder.getKey().intValue());
    return user;
}

@Override
public boolean update(User user) {

    final String sql = """
            update users set
            first_name = ?,
            last_name = ?,
            dob = ?,
            phone_number = ?,
            email_address = ?,
            type = ?,
            where user_id = ?;
            """;

    return jdbcTemplate.update(sql,
            user.getFirst_name(),
            user.getLast_name(),
            user.getDob(),
            user.getPhone_number(),
            user.getEmail_address(),
            user.getType(),
            user.getUser_id()) > 0;
}

@Override
public boolean deleteById(int id) {

        final String sql = """
                delete from users where user_id = ?
                """;
        return jdbcTemplate.update(sql, id) > 0;
}
}







