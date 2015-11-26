/**
 * Created by admin on 11/11/15.
 * Describe the highlevel daily routine of a person
 */

import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;


public class DailyTemplate {

    /* no more than 100 events in a daily template */
    // private List <SimpleDailyEvent> events = new List`();

    // template name
    private int id;

    // template description string
    private String description;

    // list of the daily template events
    private List<SimpleDailyEvent> events;


    public DailyTemplate() {
        events = new ArrayList<SimpleDailyEvent>();
    }

    /*
    public DailyTemplate(ArrayList inputEvents) {
        this.events = inputEvents;
    }

    public ArrayList getEvents() {
        return events;
    }

    */
    /* events are added one after the other */
    /*
    public void addEvent(SimpleDailyEvent event){
        events.add(events.size(),event);
    }

    public int size(){
        return events.size();
    }
*/
    /*
    public void print() {

        ListIterator i = events.listIterator();
        for (int i = 0; i < events.size(); i++) {
            SimpleDailyEvent event =
            System.out.println(event)
        }
    }
   */

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

    @Override
    public String toString() {
        return "DailyTemplate{" +
                "id=" + this.id +
                ", description='" + this.description + '\'' +
                ", events=" + this.events +
                '}';
    }
}
