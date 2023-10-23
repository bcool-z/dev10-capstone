package learnb.yoga.data;

import learnb.yoga.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static learnb.yoga.data.TestHelper.SESSION_ONE;
import static learnb.yoga.data.TestHelper.makeUser;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {jdbcTemplate.execute("call set_known_good_state();"); }

    @Test
    void findById() {

        Reservation expected = new Reservation();

        expected.setId(1);
        expected.setSession(SESSION_ONE);
        expected.setStudent(makeUser(1));

        Reservation actual = repository.findById(1);
        assertEquals(expected,actual);
    }
}