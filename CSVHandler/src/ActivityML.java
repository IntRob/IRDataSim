/**
 * Created by itaimendelsohn on 11/26/15.
 * represents the data set for a specific activity type. Holds the "rows" and generate the data to be used in the ML system
 */

import definitions.kYouActivities;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ActivityML {

    // event type that defines the "type" of ML
    kYouActivities kYouActivityType = kYouActivities.NONE;

    // list of events for this activity
    List <MLEvent> events;

    public ActivityML(kYouActivities kYouActivityType) {
        this.kYouActivityType = kYouActivityType;
        events = new ArrayList<>();
    }

    public List<MLEvent> getEvents() {
        return events;
    }

    // dump content into a CSV file
    public void dumpToCSV(File fName)
    {
        // TODO
    }
    // add an event to the list
    public void addEvent(MLEvent event)
    {
        events.add(event);
    }


}
