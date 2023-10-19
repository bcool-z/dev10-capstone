package learnb.yoga.data;

import learnb.yoga.data.mappers.SessionMapper;
import learnb.yoga.model.AppUser;
import learnb.yoga.model.Location;
import learnb.yoga.model.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Repository
public class SessionJdbcTemplateRepository {

    private final JdbcTemplate jdbcTemplate;

    public SessionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private String SELECT = """
            select s.session_id, s.start_time, s.end_time, s.capacity,
                a.app_user_id, a.first_name, a.last_name, a.dob, a.phone_number, 
                a.email_address, a.user_type, l.location_id, l.name, l.size, 
                l.description
                from
                session s
                inner join app_user a on s.instructor_id = a.app_user_id
                inner join location l on s.location_id = l.location_id
""";

public Session findById(int id) {

        final String sql = SELECT + """
                where session_id = ?
                """;

        return jdbcTemplate.query(sql, new SessionMapper(), id).stream().findFirst().orElse(null);

}

public List<Session> findByDate(LocalDate date) {

    final String sql = SELECT + """
                where DATE(start_time) = ?
                """;

    return jdbcTemplate.query(sql,new SessionMapper(),date);

}

public List<Session> findByInstructor(AppUser instructor){

    final String sql = SELECT + """
            
            where instructor_id = ?
            
            """;

    return jdbcTemplate.query(sql,new SessionMapper(), instructor.getUserId());

}

public List<Session> findByLocation(Location location){

    final String sql = SELECT + """
            
            where l.location_id = ?
            """;

    return jdbcTemplate.query(sql, new SessionMapper(), location.getId());

}

public Session add(Session session){

    // SimpleJdbcInsert

    SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("session")
            .usingGeneratedKeyColumns("session_id");

    HashMap<String,Object> args = new HashMap<>();
    args.put("start_time",session.getStart());
    args.put("end_time",session.getEnd());
    args.put("capacity",session.getCapacity());
    args.put("instructor_id",session.getInstructor().getUserId());
    args.put("location_id",session.getLocation().getId());

    int id = insert.executeAndReturnKey(args).intValue();
    session.setId(id);

    return session;
}

public boolean update(Session session){

    final String sql = """
            update session set
            start_time = ?,
            end_time = ?,
            capacity = ?,
            instructor_id = ?,
            location_id = ?
            where session_id = ?
            """;

    return jdbcTemplate.update(sql,
            session.getStart(),
            session.getEnd(),
            session.getCapacity(),
            session.getInstructor().getUserId(),
            session.getLocation().getId(),
            session.getId())>0;

}

public boolean deleteById(int id){

    final String sql = """
            delete from session where session_id = ?
            """;

    return jdbcTemplate.update(sql,id) > 0;

}


}
