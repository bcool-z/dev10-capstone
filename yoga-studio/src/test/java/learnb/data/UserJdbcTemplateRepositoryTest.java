package learnb.data;

import learnb.model.User;
import learnb.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {jdbcTemplate.execute("call set_known_good_state();"); }

    @Test
    void shouldFindById_2() {

        User expected = new User("Jane", "Smith", LocalDate.of(1985,02,20),
                "555-555-5556", "jane.smith@email.com", UserType.INSTRUCTOR);


    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByLastName() {
    }

    @Test
    void add() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}