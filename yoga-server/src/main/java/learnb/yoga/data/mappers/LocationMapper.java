package learnb.yoga.data.mappers;

import learnb.yoga.model.Location;
import learnb.yoga.model.Size;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {

@Override
    public Location mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Location location = new Location();

        location.setId(resultSet.getInt("location_id"));
        location.setName(resultSet.getString("name"));
        location.setSize(Size.valueOf(resultSet.getString("size")));
        location.setDescription(resultSet.getString("description"));

        return location;
}
}
