package learnb.yoga.data;

import learnb.yoga.data.mappers.ReservationMapper;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ReservationJdbcTemplateRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String SELECT = """
             select r.reservation_id, s.yoga_session_id, s.start_time, s.end_time,
                s.capacity, i.app_user_id, i.first_name, i.last_name, i.dob, i.phone_number, 
                i.email_address, i.user_type, i.password_hash, l.location_id, l.name, l.size, l.description,
                a.app_user_id student_user_id, a.first_name student_first_name, 
                a.last_name student_last_name, a.dob student_dob, a.phone_number student_phone_number,
                a.email_address student_email_address,
                a.user_type student_user_type
                from reservation r
                inner join yoga_session s on s.yoga_session_id = r.yoga_session_id
                inner join app_user i on i.app_user_id = s.instructor_id
                inner join location l on l.location_id = s.location_id
                inner join app_user a on a.app_user_id = r.student_id
            """;
@Override
public Reservation findById(int id) {

        final String sql = SELECT + """
                
                where reservation_id = ?
                
                """;

        return jdbcTemplate.query(sql, new ReservationMapper(), id).stream().findFirst().orElse(null);

}

@Override
public List<Reservation> findByStudent(AppUser student){

    final String sql = SELECT + """
            where a.app_user_id = ?
            """;
    return jdbcTemplate.query(sql, new ReservationMapper(), student.getAppUserId());
}

@Override
public Reservation add(Reservation reservation){

    final String sql = """ 
         insert into reservation
         (session_id, student_id)
         values (?,?)
""";

    GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
    int rowsAffected = jdbcTemplate.update(connection -> {
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1,reservation.getYogaSession().getId());
        ps.setInt(2,reservation.getStudent().getAppUserId());
        return ps;},keyHolder);

    if (rowsAffected <= 0) {
        return null;
    }

    reservation.setId(keyHolder.getKey().intValue());
    return reservation;

}

@Override
public boolean update(Reservation reservation){

    final String sql = """
            update reservation set
            session_id = ?,
            student_id = ?
            where reservation_id = ?
            """;

    return  jdbcTemplate.update(sql,
            reservation.getYogaSession().getId(),
            reservation.getStudent().getAppUserId(),reservation.getId()) > 0;
}

@Override
public boolean deleteById(int id){

    final String sql = """
            delete from reservation where reservation_id = ?
            """;

    return jdbcTemplate.update(sql,id) > 0 ;
}

}
