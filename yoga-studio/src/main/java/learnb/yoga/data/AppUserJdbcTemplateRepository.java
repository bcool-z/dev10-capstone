package learnb.yoga.data;

import learnb.yoga.data.mappers.AppUserMapper;
import learnb.yoga.model.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {


    private final JdbcTemplate jdbcTemplate;


    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

@Override
public AppUser findById(int id) {

        final String sql = """
                select app_user_id, first_name, last_name, dob, phone_number,
                email_address, user_type from app_user where app_user_id = ?;
                """;


            return jdbcTemplate.query(sql, new AppUserMapper(), id).stream().findFirst().orElse(null);


}

@Override
public AppUser findByEmail(String email_address) {

        final String sql = """
                
                select app_user_id, first_name, last_name, dob, phone_number, 
                email_address, user_type from app_user where email_address = ?;
                """;

    return jdbcTemplate.query(sql, new AppUserMapper(), email_address).stream().findFirst().orElse(null);
}

//@Override
//public List<AppUser> findByName(String fnPrefix, String lnPrefix) {
//
//        final String sql = """
//
//                select app_user_id, first_name, last_name, dob, phone_number,
//                email_address, user_type from app_user where app_user_id like ?;
//
//                """;
//
//        return jdbcTemplate.query(sql, new UserMapper(), prefix + "%");
//
//}

@Override
public AppUser add(AppUser appUser){

    final String sql = """ 
         insert into app_user
         (first_name, last_name, dob, phone_number, email_address, user_type)
         values (?,?,?,?,?,?)
""";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    int rowsAffected = jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, appUser.getFirstName());
        ps.setString(2, appUser.getLastName());
        ps.setDate(3, Date.valueOf(appUser.getDob()));
        ps.setString(4, appUser.getPhoneNumber());
        ps.setString(5, appUser.getEmailAddress());
        ps.setString(6, appUser.getUserType().getLabel());
        return ps;
    },keyHolder);

    if (rowsAffected <= 0) {
        return null;
    }
    appUser.setUserId(keyHolder.getKey().intValue());
    return appUser;
}

@Override
public boolean update(AppUser appUser) {

    final String sql = """
            update app_user set
            first_name = ?,
            last_name = ?,
            dob = ?,
            phone_number = ?,
            email_address = ?,
            user_type = ?,
            where user_id = ?;
            """;

    return jdbcTemplate.update(sql,
            appUser.getFirstName(),
            appUser.getLastName(),
            appUser.getDob(),
            appUser.getPhoneNumber(),
            appUser.getEmailAddress(),
            appUser.getUserType(),
            appUser.getUserId()) > 0;
}

@Override
public boolean deleteById(int id) {

        final String sql = """
                delete from app_user where app_user_id = ?
                """;
        return jdbcTemplate.update(sql, id) > 0;
}
}







