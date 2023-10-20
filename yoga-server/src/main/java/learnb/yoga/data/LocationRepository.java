package learnb.yoga.data;

import learnb.yoga.model.Location;
import learnb.yoga.model.Size;

import java.util.List;

public interface LocationRepository {
    Location findById(int id);

    List<Location> findAll();

    List<Location> findBySize(Size size);

    Location add(Location location);

    boolean update(Location location);

    Boolean deleteById(int id);
}
