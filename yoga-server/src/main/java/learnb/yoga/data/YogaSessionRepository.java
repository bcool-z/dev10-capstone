package learnb.yoga.data;

import learnb.yoga.models.AppUser;
import learnb.yoga.models.Location;
import learnb.yoga.models.YogaSession;

import java.time.LocalDate;
import java.util.List;

public interface YogaSessionRepository {

    int getEnrollmentCount(int sessionId);
    YogaSession findById(int id);

    List<YogaSession> findByDate(LocalDate date);

    List<YogaSession> findByInstructor(AppUser instructor);

    List<YogaSession> findByLocation(Location location);

    YogaSession add(YogaSession session);

    boolean update(YogaSession session);

    boolean deleteById(int id);
}
