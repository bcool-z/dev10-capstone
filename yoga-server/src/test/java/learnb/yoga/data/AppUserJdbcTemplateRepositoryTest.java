package learnb.yoga.data;

import learnb.yoga.models.AppUser;
import learnb.yoga.security.JwtConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.ArrayList;
import java.util.List;

import static learnb.yoga.data.TestHelper.makeUser;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

//    @MockBean
//    private AuthenticationManager authenticationManager;
//
//    @MockBean
//    private JwtConverter jwtConverter;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @BeforeEach
    void setup() {jdbcTemplate.execute("call set_known_good_state();"); }

    @Test
    void shouldFindById_2() {

        AppUser expected = makeUser(2);

        AppUser actual = repository.findById(2);
        expected.setPassword("123");
        assertEquals(expected,actual);
    }

    @Test
    void findByEmail() {

       AppUser expected = makeUser(5);
AppUser actual = repository.findByEmail("fn.ln5@email.com");
assertEquals(expected,actual);
    }

    @Test
    void shouldAdd() {

        assertNull(repository.findById(8));

        AppUser appUser = makeUser(8);
        appUser.setAppUserId(0);
        repository.add(appUser);

        assertEquals(makeUser(8), repository.findById(8));




    }

    @Test
    void searchByEmail() {

        List<AppUser> expected = new ArrayList<>();
        expected.add(makeUser(4));
        assertEquals(expected,repository.searchByEmail("fn.ln4@email.com"));


    }

    @Test
    void searchByPhone() {
        List<AppUser> result = repository.searchByPhone("555-555-5553");
        System.out.println("Result: " + result);  // Debugging step

        List<AppUser> expected = new ArrayList<>();
        expected.add(makeUser(3));
        assertEquals(expected, result);
    }

    @Test
    void searchByName() {

        String[] query = {"lastnam","firstname"};
        assertEquals(7, repository.searchByName(query).size());

    }

    @Test
    void searchByName1() {

        String[] query = {"lastname6","firstname"};

        List<AppUser> expected = new ArrayList<>();
        expected.add(makeUser(6));

        assertEquals(expected,repository.searchByName(query));
    }
}