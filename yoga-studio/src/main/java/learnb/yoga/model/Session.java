package learnb.yoga.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Session {

    private int id;

    private LocalDateTime start;

    private LocalDateTime end;

    private int capacity;

    private AppUser instructor;

    private Location location;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public AppUser getInstructor() {
        return instructor;
    }

    public void setInstructor(AppUser instructor) {
        this.instructor = instructor;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return id == session.id && capacity == session.capacity && Objects.equals(start, session.start) && Objects.equals(end, session.end) && Objects.equals(instructor, session.instructor) && Objects.equals(location, session.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, start, end, capacity, instructor, location);
    }
}
