/**
 * Created by admin on 11/11/15.
 * Describe the highlevel daily routine of a person
 */

import definitions.HomeEventType;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;


public class DailyTemplate {

    // template name
    private int id;

    // template description string
    private String description;

    // list of the daily template events
    private List<SimpleDailyEvent> events;


    public DailyTemplate() {
        events = new ArrayList<SimpleDailyEvent>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SimpleDailyEvent> getEvents() {
        return events;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addEvent(SimpleDailyEvent event){
        events.add(event);
        //System.out.println("Event added: ");// event.toString());

    }

    public SimpleDailyEvent isEventStartTime(List<SimpleDailyEvent> inEvents, int minStamp){
        for(int i = 0 ; i < inEvents.size() ; i++){
            SimpleDailyEvent event = inEvents.get(i);
            if(event.getStartMinuteOfDay() == minStamp)
                return event;
        }

        return null;
    }

    public List<SimpleDailyEvent> duplicateEvents(){

        List eventList = new ArrayList<SimpleDailyEvent>();

        for(int i=0 ; i < events.size() ; i++){
            SimpleDailyEvent newEvent = new SimpleDailyEvent();
            SimpleDailyEvent event    = events.get(i);

            HomeEventType type = event.getType();
            int startMin = event.getStartMinuteOfDay();
            int endMin = event.getEndMinuteOfDay();
            double probabilty = event.getProbability();
            int metaData = event.getMetaData();

            newEvent.setType(type);
            newEvent.setStartMinInTheDay(startMin);
            newEvent.setEndMindInTheDay(endMin);
            newEvent.setProbability(probabilty);
            newEvent.setMetaData(metaData);

            eventList.add(i,(SimpleDailyEvent)newEvent);

        }

        return eventList;
    }

    @Override
    public String toString() {
        return "DailyTemplate{" +
                "id=" + this.id +
                ", description='" + this.description + '\'' +
                ", events=" + this.events +
                '}';
    }
}
