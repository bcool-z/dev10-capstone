package learnb.yoga.data;

import learnb.yoga.data.mappers.ReservationMapper;
import learnb.yoga.model.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

public Reservation findById(int id) {

        final String sql = """
                
                select r.reservation_id, s.session_id, s.start_time, s.end_time,
                s.capacity, i.app_user_id, i.first_name, i.last_name, i.dob, i.phone_number, 
                i.email_address, i.user_type, l.location_id, l.name, l.size, l.description,
                a.app_user_id student_user_id, a.first_name student_first_name, 
                a.last_name student_last_name, a.dob student_dob, a.phone_number student_phone_number,
                a.email_address student_email_address,
                a.user_type student_user_type
                from reservation r
                inner join session s on s.session_id = r.session_id
                inner join app_user i on i.app_user_id = s.instructor_id
                inner join location l on l.location_id = s.location_id
                inner join app_user a on a.app_user_id = r.student_id
                where reservation_id = ?
                
                """;

        return jdbcTemplate.query(sql, new ReservationMapper(), id).stream().findFirst().orElse(null);

}

}
