package learnb.yoga.data.mappers;

import learnb.yoga.model.*;
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


        AppUser student = new AppUser();
        student.setUserId(resultSet.getInt("student_user_id"));
        student.setFirstName(resultSet.getString("student_first_name"));
        student.setLastName(resultSet.getString("student_last_name"));
        student.setDob(resultSet.getDate("student_dob").toLocalDate());
        student.setPhoneNumber(resultSet.getString("student_phone_number"));
        student.setEmailAddress(resultSet.getString("student_email_address"));
        student.setUserType(UserType.valueOf(resultSet.getString("student_user_type")));

        reservation.setSession(session);
        reservation.setStudent(student);

        return reservation;


    }
}
