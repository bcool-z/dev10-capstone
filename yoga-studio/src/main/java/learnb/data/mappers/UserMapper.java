package learnb.data.mappers;

import learnb.model.User;
import learnb.model.UserType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
public User mapRow(ResultSet resultSet, int i) throws SQLException  {

    User user = new User();

    user.setUser_id(resultSet.getInt("user_id"));
    user.setFirst_name(resultSet.getString("first_name"));
    user.setLast_name(resultSet.getString("last_name"));
    user.setDob(resultSet.getDate("dob").toLocalDate());
    user.setPhone_number(resultSet.getString("phone_number"));
    user.setEmail_address(resultSet.getString("email_address"));
    user.setType(UserType.valueOf(resultSet.getString("type")));

    return user;

}

}
