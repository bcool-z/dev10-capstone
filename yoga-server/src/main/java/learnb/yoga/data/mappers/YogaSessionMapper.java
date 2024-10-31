package learnb.yoga.data.mappers;

import learnb.yoga.models.AppUser;
import learnb.yoga.models.Location;
import learnb.yoga.models.YogaSession;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class YogaSessionMapper implements RowMapper<YogaSession> {

    @Override
    public YogaSession mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        YogaSession yogaSession = new YogaSession();
        yogaSession.setId(resultSet.getInt("yoga_session_id"));
        yogaSession.setStart(resultSet.getTime("start_time").toLocalTime()
                .atDate(resultSet.getDate("start_time").toLocalDate()));
        yogaSession.setEnd(resultSet.getTime("end_time").toLocalTime()
                .atDate(resultSet.getDate("end_time").toLocalDate()));
        yogaSession.setCapacity(resultSet.getInt("capacity"));

        AppUserMapper appUserMapper = new AppUserMapper();
        AppUser instructor = appUserMapper.mapRow(resultSet,rowNum);

        LocationMapper locationMapper = new LocationMapper();
        Location location = locationMapper.mapRow(resultSet,rowNum);

        yogaSession.setInstructor(instructor);
        yogaSession.setLocation(location);

        return yogaSession;
    }

}
