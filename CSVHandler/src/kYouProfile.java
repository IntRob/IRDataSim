/**
 * Created by admin on 11/11/15.
 *
 *
 * Defines kYou operational profile
 *
 */


public class kYouProfile {
    private int startHourOfOperation;
    private int endHourOfOperation;
    private int maxNumberOfDailyInterventions;


    public  kYouProfile(){
        this.startHourOfOperation = 6;
        this.endHourOfOperation = 23;
        this.maxNumberOfDailyInterventions = 50;
    }

    public kYouProfile(int startHourOfOperation,
                       int endHourOfOperation,
                       int maxNumberOfInterventions) {

        this.startHourOfOperation = startHourOfOperation;
        this.endHourOfOperation = endHourOfOperation;
        this.maxNumberOfDailyInterventions = maxNumberOfInterventions;
    }


    public int getStartHourOfOperation() {
        return startHourOfOperation;
    }

    public void setStartHourOfOperation(int startHourOfOperation) {
        this.startHourOfOperation = startHourOfOperation;
    }

    public int getEndHourOfOperation() {
        return endHourOfOperation;
    }

    public void setEndHourOfOperation(int endHourOfOperation) {
        this.endHourOfOperation = endHourOfOperation;
    }



}
