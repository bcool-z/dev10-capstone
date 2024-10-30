package learnb.yoga.data;

import learnb.yoga.models.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestHelper {

    static public AppUser makeUser(int index){

        AppUser appUser = new AppUser();
        appUser.setAppUserId(index);
        appUser.setFirstName("FirstName"+index);
        appUser.setLastName("LastName"+index);
        appUser.setDob(LocalDate.of(1990+index, index, index));
        appUser.setPhoneNumber("555-555-555"+index);
        appUser.setEmailAddress("fn.ln"+index+"@email.com");
        if(index%2==0) appUser.setUserType(UserType.INSTRUCTOR);
        else appUser.setUserType(UserType.STUDENT);
        appUser.setPassword("0");

        return appUser;
    }

    static public Location makeLocation(int index){

        Location location = new Location();

        location.setId(index);
        location.setName("Location"+index);
        if((index)%3==1) location.setSize(Size.SMALL);
        else if(index%3==2) location.setSize(Size.MEDIUM);
        else location.setSize(Size.LARGE);
        location.setDescription("Description"+index);

        return location;
    }

    static public Session makeSession(int index){
      Session session  = new Session();
      session.setId(0);
      session.setStart(LocalDateTime.of(3000+index,1,10,index,30,0));
      session.setEnd(LocalDateTime.of(3000+index,1,10,index+1,30,0));
      session.setCapacity(10);
      session.setInstructor(makeUser(4));
      session.setLocation(makeLocation(1));
        return session;
    }

    public static final Session SESSION_ONE = new Session(1, LocalDateTime.of(3024, 2,22,13, 0, 0),
            LocalDateTime.of(3024, 2,22,14, 0, 0),
            3,makeUser(2),makeLocation(1));

    public static final Session SESSION_TWO = new Session(2, LocalDateTime.of(3024, 2,22,14, 0, 0),
            LocalDateTime.of(3024, 2,22,15, 0, 0),
            10,makeUser(2),makeLocation(2));

    public static final Session SESSION_THREE = new Session(3, LocalDateTime.of(3024, 2,23,10, 0, 0),
            LocalDateTime.of(3024, 2,23,11, 0, 0),
            12,makeUser(4),makeLocation(3));


}
