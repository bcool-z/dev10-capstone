package learnb.yoga.models;

import java.util.Objects;

public class Reservation {

    private int id;

    private YogaSession yogaSession;

    private AppUser student;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public YogaSession getYogaSession() {
        return yogaSession;
    }

    public void setYogaSession(YogaSession yogaSession) {
        this.yogaSession = yogaSession;
    }

    public AppUser getStudent() {
        return student;
    }

    public void setStudent(AppUser student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(yogaSession, that.yogaSession) && Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, yogaSession, student);
    }
}
