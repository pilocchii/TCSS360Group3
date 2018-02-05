package urbanparks;

import java.util.Date;
import java.util.HashMap;

/**
* User classes
* @author Alyssa Ingersoll
*/


/*
The class signature and hierarchy is ordered this way because our design has other 
classes that inherit from User. Uncalled method signatures that differ between 
classes not shown.
*/

protected class User {

    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;

    protected User(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

public class ParkManager implements User {
    public ParkManager(String firstName, String lastName, String email, String phoneNumber) {
        super(String firstName, String lastName, String email, String phoneNumber);
    }
}


