package learnb.yoga.data;

import learnb.yoga.models.Location;
import learnb.yoga.models.Size;

import java.util.List;

public interface LocationRepository {
    Location findById(int id);

    List<Location> findAll();

    List<Location> findBySize(Size size);

    Location add(Location location);

    boolean update(Location location);

    boolean deleteById(int id);
}
