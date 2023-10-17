package learnb.model;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class User {

    private int user_id;
    private String first_name;
    private String last_name;

    private LocalDate dob;
    private String phone_number;

    private String email_address;
    private UserType type;


    public User() {
    }
    public User(int user_id, String first_name, String last_name, LocalDate dob,
                String phone_number, String email_address, UserType type) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.phone_number = phone_number;
        this.email_address = email_address;
        this.type = type;
    }
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }
}
