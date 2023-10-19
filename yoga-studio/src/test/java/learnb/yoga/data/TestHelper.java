package learnb.yoga.data;

import learnb.yoga.model.AppUser;
import learnb.yoga.model.Location;
import learnb.yoga.model.Size;
import learnb.yoga.model.UserType;

import java.time.LocalDate;

public class TestHelper {

    static public AppUser makeUser(int index){

        AppUser appUser = new AppUser();
        appUser.setUserId(index);
        appUser.setFirstName("FirstName"+index);
        appUser.setLastName("LastName"+index);
        appUser.setDob(LocalDate.of(1990+index,00+index,00+index));
        appUser.setPhoneNumber("555-555-555"+index);
        appUser.setEmailAddress("fn.ln"+index+"@email.com");
        if(index%2==0) appUser.setUserType(UserType.INSTRUCTOR);
        else appUser.setUserType(UserType.STUDENT);
//        appUser.setPassword("12345");

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

}
