package learnb.yoga.data.mappers;

import learnb.yoga.models.AppUser;
import learnb.yoga.models.Location;
import learnb.yoga.models.Session;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionMapper implements RowMapper<Session> {

    @Override
    public Session mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Session session = new Session();
        session.setId(resultSet.getInt("session_id"));
        session.setStart(resultSet.getTime("start_time").toLocalTime()
                .atDate(resultSet.getDate("start_time").toLocalDate()));
        session.setEnd(resultSet.getTime("end_time").toLocalTime()
                .atDate(resultSet.getDate("end_time").toLocalDate()));
        session.setCapacity(resultSet.getInt("capacity"));

        AppUserMapper appUserMapper = new AppUserMapper();
        AppUser instructor = appUserMapper.mapRow(resultSet,rowNum);

        LocationMapper locationMapper = new LocationMapper();
        Location location = locationMapper.mapRow(resultSet,rowNum);

        session.setInstructor(instructor);
        session.setLocation(location);

        return session;
    }

}
