package UrbanParks;

/**
 * Unit tests for business rule 1.a:
 * "A volunteer cannot sign up for more than one job that extends across any particular day"
 */
public class VolunteerTest {
    /**
     * 1.a.i
     * Volunteer has no jobs already signed up for
     */
    @Test
    public void isSameDay_VolunteerNoJobsYet_false {
        Job condidateJob = new Job(1517800001000, 1517800002000);
        Volunteer volunteerNoJobs = new Volunteer();
        assertFalse(volunteerNoJobs.isSameDay(condidateJob));
    }
    
    /**
     * 1.a.ii
     * Volunteer has jobs already signed up for, 
     * this job does not extend across any days as jobs already signed up for
     */
    @Test
    public void isSameDay_VolunteerHasJobsNoOverlap_false {
        Job signedUpJob1 = new Job(1517800001000, 1517800002000);
        Job signedUpJob2 = new Job(1517800003000, 1517800004000);
        Job signedUpJob3 = new Job(1517800005000, 1517800006000);
        Job condidateJob = new Job(1517800007000, 1517800008000);
        
        Volunteer volunteerWithJobs = new Volunteer();
        volunteerWithJobs.signUpForJob(signedUpJob1);
        volunteerWithJobs.signUpForJob(signedUpJob2);
        volunteerWithJobs.signUpForJob(signedUpJob3);
        
        assertFalse(volunteerWithJobs.isSameDay(condidateJob));
    }
    /**
     * 1.a.iii
     * Volunteer has jobs already signed up for, 
     * this job starts the same day as the end of some job already signed up for
     */
    @Test
    public void isSameDay_CurrentJobEndOverlapsNewJobStart_true {
        Job signedUpJob1 = new Job(1517800001000, 1517800002000);
        Job signedUpJob2 = new Job(1517800003000, 1517800004000);
        Job signedUpJob3 = new Job(1517800005000, 1517800006000);
        Job condidateJob = new Job(1517800006003, 1517800007000);
        
        Volunteer volunteerWithJobs = new Volunteer();
        volunteerWithJobs.signUpForJob(signedUpJob1);
        volunteerWithJobs.signUpForJob(signedUpJob2);
        volunteerWithJobs.signUpForJob(signedUpJob3);
        
        assertTrue(volunteerWithJobs.isSameDay(condidateJob));
    }
    
    
    /**
     * 1.a.iv
     * Volunteer has jobs already signed up for, 
     * this job ends the same day as the start of some job already signed up for
     */
    @Test
    public void isSameDay_CurrentJobStartOverlapsNewJobEnd_true {
        Job signedUpJob1 = new Job(1517800001000, 1517800002000);
        Job signedUpJob2 = new Job(1517800003000, 1517800004000);
        Job signedUpJob3 = new Job(1517800009000, 1517800010000);
        Job condidateJob = new Job(1517800008000, 1517800009009);
        
        Volunteer volunteerWithJobs = new Volunteer();
        volunteerWithJobs.signUpForJob(signedUpJob1);
        volunteerWithJobs.signUpForJob(signedUpJob2);
        volunteerWithJobs.signUpForJob(signedUpJob3);
        
        assertTrue(volunteerWithJobs.isSameDay(condidateJob));
    }
}