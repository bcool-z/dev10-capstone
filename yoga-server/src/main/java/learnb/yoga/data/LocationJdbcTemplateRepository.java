package learnb.yoga.data;

import learnb.yoga.data.mappers.LocationMapper;
import learnb.yoga.models.Location;
import learnb.yoga.models.Size;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class LocationJdbcTemplateRepository implements LocationRepository {

private final JdbcTemplate jdbcTemplate;

    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Location findById(int id) {

        final String sql = """
                select location_id, `name`, size, description
                from location where location_id = ?;
                """;

        return jdbcTemplate.query(sql,new LocationMapper(),id).stream().findFirst().orElse(null);

    }

    @Override
    public List<Location> findAll() {

        final String sql = """
                select location_id, name, size, description
                from location;
                """;
        return jdbcTemplate.query(sql, new LocationMapper());
    }

    @Override
    public List<Location> findBySize(Size size) {

        final String sql = """
                select location_id, name, size, description
                from location where size = ?;
                """;

        return jdbcTemplate.query(sql,new LocationMapper(),size.toString());

    }

@Override
    public Location add(Location location) {

        // SimpleJdbcInsert

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("location")
                .usingGeneratedKeyColumns("location_id");

        HashMap<String,Object> args = new HashMap<>();
        args.put("name",location.getName());
        args.put("size", location.getSize().toString());
        args.put("description", location.getDescription());

        int id = insert.executeAndReturnKey(args).intValue();
        location.setId(id);

        return location;

    }

    @Override
    public boolean update(Location location) {

        final String sql = """
                update location set
                `name` = ?,
                size = ?,
                `description` = ?
                where location_id = ?
                """;

        return jdbcTemplate.update(sql,
                location.getName(),
                location.getSize().toString(),
                location.getDescription(),
                location.getId()) > 0;

    }

    @Override
    public boolean deleteById(int id) {

        final String sql = "delete from location where location_id = ?";
        return jdbcTemplate.update(sql,id) > 0;
    }

}
