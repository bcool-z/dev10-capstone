package learnb.yoga.data.mappers;

import learnb.yoga.model.AppUser;
import learnb.yoga.model.Location;
import learnb.yoga.model.Reservation;
import learnb.yoga.model.Session;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationMapper implements RowMapper<Reservation> {

    public Reservation mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        Reservation reservation = new Reservation();

        reservation.setId(resultSet.getInt("reservation_id"));

//        Session session = new Session();
//        session.setId(resultSet.getInt("session_id"));
//        session.setStart(resultSet.getTime("start_time").toLocalTime()
//                .atDate(resultSet.getDate("start_time").toLocalDate()));
//        session.setEnd(resultSet.getTime("end_time").toLocalTime()
//                .atDate(resultSet.getDate("end_time").toLocalDate()));
//        session.setCapacity(resultSet.getInt("capacity"));
//
//        AppUserMapper appUserMapper = new AppUserMapper();
//        AppUser instructor = appUserMapper.mapRow(resultSet,rowNum);
//
//        LocationMapper locationMapper = new LocationMapper();
//        Location location = locationMapper.mapRow(resultSet,rowNum);
//
//        session.setInstructor(instructor);
//        session.setLocation(location);

        SessionMapper sessionMapper = new SessionMapper();
        Session session = sessionMapper.mapRow(resultSet,rowNum);

        AppUserMapper appUserMapper = new AppUserMapper();
        AppUser student = appUserMapper.mapRow(resultSet,rowNum);

        reservation.setSession(session);
        reservation.setStudent(student);

        return reservation;


    }
}
