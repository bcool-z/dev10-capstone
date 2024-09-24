package learnb.yoga.data.mappers;

import learnb.yoga.models.AppUser;
import learnb.yoga.models.UserType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AppUserMapperSecure implements RowMapper<AppUser> {

    @Override
    public AppUser mapRow(ResultSet resultSet, int rowNum) throws SQLException  {

        AppUser appUser = new AppUser();

        appUser.setAppUserId(resultSet.getInt("app_user_id"));
        appUser.setFirstName(resultSet.getString("first_name"));
        appUser.setLastName(resultSet.getString("last_name"));
        appUser.setDob(resultSet.getDate("dob").toLocalDate());
        appUser.setPhoneNumber(resultSet.getString("phone_number"));
        appUser.setEmailAddress(resultSet.getString("email_address"));
        appUser.setUserType(UserType.valueOf(resultSet.getString("user_type")));
        appUser.setPassword("0");

        return appUser;

    }

}
