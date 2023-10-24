package learnb.yoga.data;

import learnb.yoga.models.AppUser;
import learnb.yoga.models.Location;
import learnb.yoga.models.Session;

import java.time.LocalDate;
import java.util.List;

public interface SessionRepository {

    int getEnrollmentCount(int sessionId);
    Session findById(int id);

    List<Session> findByDate(LocalDate date);

    List<Session> findByInstructor(AppUser instructor);

    List<Session> findByLocation(Location location);

    Session add(Session session);

    boolean update(Session session);

    boolean deleteById(int id);
}
