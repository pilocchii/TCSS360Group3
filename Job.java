package urbanparks

import java.util.Date;
import java.util.HashMap;
import 

/**
* Job Class
* @author Alyssa Ingersoll
*/


public class Job {

    private Date startDate;
    private Date endDate;
    private HashMap<String, Integer> workLoad; 
    private String description;
    private int minVolunteers;
    private ParkManager manager;

    public Job(Date startDate, Date endDate, HashMap<String, Integer> workLoad, 
        String description, int minVolunteers, ParkManager manager) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.workLoad = workLoad; 
        this.description = description;
        this.minVolunteers = minVolunteers;
        this.manager = manager;
    }
}