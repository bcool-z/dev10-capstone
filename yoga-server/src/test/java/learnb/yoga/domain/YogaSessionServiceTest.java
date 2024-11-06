package learnb.yoga.domain;

import learnb.yoga.data.YogaSessionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

class YogaSessionServiceTest {

    @MockBean
    YogaSessionRepository repository;

    @Autowired
    YogaSessionService service;

    @Test
    void shouldAddDiffLocationDiffInstructor(){


    }

    @Test
    void getEnrolled() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByDate() {
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