package learnb.yoga.data;

import learnb.yoga.data.mappers.YogaSessionMapper;
import learnb.yoga.models.AppUser;
import learnb.yoga.models.Location;
import learnb.yoga.models.YogaSession;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public class YogaSessionJdbcTemplateRepository implements YogaSessionRepository {

    private final JdbcTemplate jdbcTemplate;

    public YogaSessionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String SELECT = """
            select s.yoga_session_id, s.start_time, s.end_time, s.capacity,
                a.app_user_id, a.first_name, a.last_name, a.dob, a.phone_number, 
                a.email_address, a.user_type, a.password_hash, l.location_id, l.name, l.size, 
                l.description
                from
                yoga_session s
                inner join app_user a on s.instructor_id = a.app_user_id
                inner join location l on s.location_id = l.location_id
""";

@Override
public YogaSession findById(int id) {

        final String sql = SELECT + """
                where yoga_session_id = ?
                """;

        return jdbcTemplate.query(sql, new YogaSessionMapper(), id).stream().findFirst().orElse(null);

}

@Override
public int getEnrollmentCount(int yogaSessionId){

    final String sql = """
            select count(reservation_id)
            from reservation 
            where yoga_session_id = ?
            """;

    return jdbcTemplate.queryForObject(sql, Integer.class, yogaSessionId);
}

@Override
public List<YogaSession> findByDate(LocalDate date) {

    final String sql = SELECT + """
                where DATE(start_time) = ?
                """;

    return jdbcTemplate.query(sql,new YogaSessionMapper(),date);

}

@Override
public List<YogaSession> findByInstructor(AppUser instructor){

    final String sql = SELECT + """
            
            where instructor_id = ?
            
            """;

    return jdbcTemplate.query(sql,new YogaSessionMapper(), instructor.getAppUserId());

}

@Override
public List<YogaSession> findByLocation(Location location){

    final String sql = SELECT + """
            
            where l.location_id = ?
            """;

    return jdbcTemplate.query(sql, new YogaSessionMapper(), location.getId());

}

@Override
public YogaSession add(YogaSession yogaSession){

    // SimpleJdbcInsert

    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("yoga_session")
            .usingGeneratedKeyColumns("yoga_session_id");

    HashMap<String,Object> args = new HashMap<>();
    args.put("start_time", Timestamp.valueOf(yogaSession.getStart()));
    args.put("end_time", Timestamp.valueOf(yogaSession.getEnd()));
    args.put("capacity",yogaSession.getCapacity());
    args.put("instructor_id",yogaSession.getInstructor().getAppUserId());
    args.put("location_id",yogaSession.getLocation().getId());

    int id = insert.executeAndReturnKey(args).intValue();
    yogaSession.setId(id);

    return yogaSession;
}

@Override
public boolean update(YogaSession session){

    final String sql = """
            update yoga_session set
            start_time = ?,
            end_time = ?,
            capacity = ?,
            instructor_id = ?,
            location_id = ?
            where yoga_session_id = ?
            """;

    return jdbcTemplate.update(sql,
            session.getStart(),
            session.getEnd(),
            session.getCapacity(),
            session.getInstructor().getAppUserId(),
            session.getLocation().getId(),
            session.getId())>0;

}

@Override
public boolean deleteById(int id){

    final String sql = """
            delete from yoga_session where yoga_session_id = ?
            """;

    return jdbcTemplate.update(sql,id) > 0;

}


}
