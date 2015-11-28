import definitions.*;

import java.sql.Timestamp;

/**
 * Created by admin on 11/26/15.
 *
 * Represnet the raw event generated but the synthetic data generator and used as input to the machine learning algorithm
 */
public class SimEvent {

    // the ID of kYou user
    int personID;

    // event time stamp - dd-hh-mm . seconds and below is not in use
    int dayNum;
    int hour;
    int minute;


    // who generated the event. The elderly or the robot
    ActivityInitiator initiaor;

    // event type for person initiated events
    HomeEventType personActivityType = HomeEventType.UNKNOWN;

    // event type for kYou initiated events
    kYouActivities kYouActivityType = kYouActivities.NONE;

    // meta-data - for now an int, later will be extended
    int metaData;

    public SimEvent() {
    }

    public SimEvent(int personID, int dayNum, int hour, int minute, ActivityInitiator initiaor, HomeEventType personActivityType, kYouActivities kYouActivityType, int metaData) {
        this.personID = personID;
        this.dayNum = dayNum;
        this.hour = hour;
        this.minute = minute;
        this.initiaor = initiaor;
        this.personActivityType = personActivityType;
        this.kYouActivityType = kYouActivityType;
        this.metaData = metaData;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public ActivityInitiator getInitiaor() {
        return initiaor;
    }

    public void setInitiaor(ActivityInitiator initiaor) {
        this.initiaor = initiaor;
    }

    public HomeEventType getPersonActivityType() {
        return personActivityType;
    }

    public void setPersonActivityType(HomeEventType personActivityType) {
        this.personActivityType = personActivityType;
    }

    public kYouActivities getkYouActivityType() {
        return kYouActivityType;
    }

    public void setkYouActivityType(kYouActivities kYouActivityType) {
        this.kYouActivityType = kYouActivityType;
    }

    public int getMetaData() {
        return metaData;
    }

    public void setMetaData(int metaData) {
        this.metaData = metaData;
    }

    public int getDayNum() {
        return dayNum;
    }

    public void setDayNum(int dayNum) {
        this.dayNum = dayNum;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "SimEvent{" +
                "personID= " + personID +
                ", timestamp (dd:hh:mm)= " + dayNum +
                ":" + hour +
                ":" + minute +
                ", initiaor=" + initiaor +
                ", personActivityType=" + personActivityType +
                ", kYouActivityType=" + kYouActivityType +
                ", metaData=" + metaData +
                '}';
    }

    public String toCSVLine(){

        String initiatorStr = ActivityInitiator.PERSON.toString();
        String activityTypeStr = personActivityType.toString();

        if(kYouActivityType != kYouActivities.NONE) {
            initiatorStr = ActivityInitiator.KYOU.toString();
            activityTypeStr = kYouActivityType.toString();
        }

        String csvString =
                personID +
                "," + dayNum + ":" + hour + ":" + minute +
                "," + initiatorStr +
                "," + activityTypeStr +
                        "," + metaData + "\n";

        return csvString;
    }
}
