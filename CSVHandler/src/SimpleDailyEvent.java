/**
 * Created by admin on 11/11/15.
 * represents a single day event
 */

import definitions.ActivityInitiator;
import definitions.HomeEventType;
import java.util.Date;




/* defines the type of events modelled in the house */
/* Some evetns are discrete (represent change of state), and some are continuous (an activity) - for discrete events ,
the duration will be 1 second  */


public class SimpleDailyEvent
{


    HomeEventType type;

    int startMinInTheDay; // minute

    int endMindInTheDay;   // minute

    int metaData; // additional meta data about the event (for now only in int format)

    double probability; // probability for the event to happen

    public void DayEvent(HomeEventType type, int startMinInTheDay, int endMindInTheDay, int metaData, double probability) {
        this.type = type;
        this.startMinInTheDay = startMinInTheDay;
        this.endMindInTheDay = endMindInTheDay;
        this.metaData = metaData;
        this.probability = probability;
    }

    public HomeEventType getType() {
        return type;
    }

    public void setType(HomeEventType type) {
        this.type = type;
    }

    public void setType(String type) {


        // SLEEP , TOILET, NOTINHOME, VISITOR, EAT, PHONE, COOK, MUSIC, TV, BOOK, PHYSICAL, SOCIAL, PLAY, MOOD, UNKNOWN
        if (type.equals("SLEEP")) {
            this.type = HomeEventType.SLEEP;
            return;
        }

        if (type.equals("TOILET")) {
            this.type = HomeEventType.TOILET;
            return;
        }
        if (type.equals("NOTINHOME")) {
            this.type = HomeEventType.NOTINHOME;
            return;
        }
        if (type.equals("VISITOR")) {
            this.type = HomeEventType.VISITOR;
            return;
        }
        if (type.equals("EAT")) {
            this.type = HomeEventType.EAT;
            return;
        }
        if (type.equals("PHONE")) {
            this.type = HomeEventType.PHONE;
            return;
        }
        if (type.equals("COOK")) {
            this.type = HomeEventType.COOK;
            return;
        }
        if (type.equals("MUSIC")) {
            this.type = HomeEventType.MUSIC;
            return;
        }
        if (type.equals("TV")) {
            this.type = HomeEventType.TV;
            return;
        }
        if (type.equals("BOOK")) {
            this.type = HomeEventType.BOOK;
            return;
        }
        if (type.equals("PHYSICAL")) {
            this.type = HomeEventType.PHYSICAL;
            return;
        }
        if (type.equals("SOCIAL")) {
            this.type = HomeEventType.SOCIAL;
            return;
        }
        if (type.equals("PLAY")) {
            this.type = HomeEventType.PLAY;
            return;
        }

        if (type.equals("MOOD")) {
            this.type = HomeEventType.MOOD;
            return;
        }

        if (type.equals("UNKNOWN")) {
            this.type = HomeEventType.UNKNOWN;
            return;
        }

        System.out.println("ERROR: unidentified event type - " + type);
        System.exit(1);
    }



    public int getStartDate() {
        return startMinInTheDay;
    }

    public void setStartDate(String startDateStr) {

        String tmp;

        if(startDateStr.length() != 5) {
            System.out.println("error in startDateStr format");
            System.exit(0);
        }

        tmp = startDateStr.substring(0,2);
        int hour = (int)Integer.parseInt(tmp);

        tmp = startDateStr.substring(3,5);

        int min = (int)Integer.parseInt(tmp);

        startMinInTheDay = (hour * 60) + min;

    }

    public void setStartMinInTheDay(int min){
        startMinInTheDay = min;
    }


    public void setEndDate(String endDateStr) {

        String tmp;

        if(endDateStr.length() != 5)
            System.out.println("error in endDataStr format");

        tmp = endDateStr.substring(0,2);
        int hour = (int)Integer.parseInt(tmp);

        tmp = endDateStr.substring(3,5);

        int min = (int)Integer.parseInt(tmp);

        endMindInTheDay = (hour * 60) + min;

    }

    public void setEndMindInTheDay(int min){
        endMindInTheDay = min;
    }

    public int getStartMinuteOfDay() {

        return startMinInTheDay;
    }

    public int getEndMinuteOfDay() {

        return endMindInTheDay;
    }


    public void setMetaData(int metaData) {
        this.metaData = metaData;
    }

    public int getMetaData() {
        return metaData;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }



    @Override
    public String toString() {
        return  '\n' +  "SimpleDailyEvent{" +
                "type=" + type +
                ", startTime = " + (int)(startMinInTheDay / 60) + ":" + (startMinInTheDay % 60) +
                ", endTime = " + (int)(endMindInTheDay / 60) + ":" + (endMindInTheDay % 60) +
                ", meta = " + metaData +
                ", probability='" + probability + '\'' +
                '}';
    }
}
