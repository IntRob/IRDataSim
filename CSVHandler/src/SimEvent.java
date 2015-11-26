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

    // event time stamp - only yy-mm-dd, hh-mm . seconds and below is not in use
    Timestamp timeStamp;

    // who generated the event. The elderly or the robot
    ActivityInitiator initiaor;

    // event type for person initiated events
    HomeEventType personActivityType = HomeEventType.NONE;

    // event type for kYou initiated events
    kYouActivities kYouActivityType = kYouActivities.NONE;

    // meta-data - for now an int, later will be extended
    int metaData;

    public SimEvent() {
    }

    public SimEvent(int personID, Timestamp timeStamp, ActivityInitiator initiaor, HomeEventType personActivityType, kYouActivities kYouActivityType, int metaData) {
        this.personID = personID;
        this.timeStamp = timeStamp;
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

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
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

    @Override
    public String toString() {
        return "SimEvent{" +
                "personID=" + personID +
                ", timeStamp=" + timeStamp +
                ", initiaor=" + initiaor +
                ", personActivityType=" + personActivityType +
                ", kYouActivityType=" + kYouActivityType +
                ", metaData=" + metaData +
                '}';
    }
}
