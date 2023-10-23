package learnb.yoga.data;

import learnb.yoga.models.AppUser;
import learnb.yoga.models.Reservation;

import java.util.List;

public interface ReservationRepository {
    Reservation findById(int id);

    List<Reservation> findByStudent(AppUser student);

    Reservation add(Reservation reservation);

    boolean update(Reservation reservation);

    boolean deleteById(int id);
}
