package learnb.yoga.data;

import learnb.yoga.model.Location;
import learnb.yoga.model.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learnb.yoga.data.TestHelper.makeLocation;
import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest
class LocationJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    LocationJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {jdbcTemplate.execute("call set_known_good_state();"); }

    @Test
    void findById3() {

        Location expected = makeLocation(3);

        Location actual = repository.findById(3);
        assertEquals(actual,expected);

    }

    @Test
    void shouldNotFindyId() {

        Location actual = repository.findById(12);
        assertNull(actual);
    }

    @Test
    void findAll() {

        List<Location> actual = repository.findAll();

        assertEquals(actual.size(), 7);

    }

    @Test
    void findBySize() {
        List<Location> actualSMALL = repository.findBySize(Size.SMALL);
        assertEquals(actualSMALL.size(),3);
        List<Location> actualLARGE = repository.findBySize(Size.LARGE);
        assertEquals(actualLARGE,2);


    }

    @Test
    void shouldAdd() {

        List<Location> expectedList = repository.findAll();
        assertEquals(7,expectedList.size());

        Location location = makeLocation(10);
        location.setId(0);

        repository.add(location);
        assertEquals(repository.findAll().size(),8);

        location.setId(8);

        location.setId(8);
        Location actual = repository.findById(8);

        assertEquals(actual, location);


    }

    @Test
    void shouldUpdate(){

    Location location = makeLocation(10);
    location.setId(7);

    assertTrue(repository.update(location));

    assertEquals(location, repository.findById(7));

    }
}