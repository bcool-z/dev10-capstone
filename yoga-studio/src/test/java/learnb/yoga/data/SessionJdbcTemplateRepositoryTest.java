package learnb.yoga.data;

import learnb.yoga.App;
import learnb.yoga.model.AppUser;
import learnb.yoga.model.Location;
import learnb.yoga.model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static learnb.yoga.data.TestHelper.makeLocation;
import static learnb.yoga.data.TestHelper.makeUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SessionJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SessionJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {jdbcTemplate.execute("call set_known_good_state();"); }


    @Test
    void findById() {

        AppUser instructor = makeUser(4);
        Location location = makeLocation(3);

        Session expected = new Session();

        expected.setId(3);
        expected.setStart(LocalDateTime.of(2024,02,23,10,00));
        expected.setEnd(LocalDateTime.of(2024,02,23,11,00));
        expected.setCapacity(12);
        expected.setInstructor(instructor);
        expected.setLocation(location);

        Session actual = repository.findById(3);

        assertEquals(expected,actual);
    }

    @Test
    void findByDate() {

        List<Session> actual = repository.findByDate(LocalDate.of(2024,02,22));

        assertEquals(2,actual.size());

    }

    @Test
    void findByInstructor() {

        AppUser instructor2 = makeUser(2);
        assertEquals(2,repository.findByInstructor(instructor2).size());
        AppUser instructor4 = makeUser(4);
        assertEquals(1,repository.findByInstructor(instructor4).size());
    }
}