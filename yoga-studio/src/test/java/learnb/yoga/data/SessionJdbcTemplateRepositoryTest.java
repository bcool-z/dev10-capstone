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

import static learnb.yoga.data.TestHelper.*;
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



        Session expected = SESSION_THREE;
        //
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

    @Test
    void updateAndFindByLocation() {

        Location location1 = makeLocation(1);

        List actual = repository.findByLocation(location1);
        assertEquals(1,actual.size());

        Session expected = SESSION_THREE;

        expected.setLocation(location1);

        assertTrue(repository.update(expected));

        actual = repository.findByLocation(location1);
        assertEquals(2,actual.size());

    }

}