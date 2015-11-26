/**
 * Created by admin on 11/11/15.
 * represents a single day event
 */

import definitions.HomeEventType;

import java.sql.Time;
import java.util.Date;
import java.util.concurrent.TimeUnit;




/* defines the type of events modelled in the house */
/* Some evetns are discrete (represent change of state), and some are continuous (an activity) - for discrete events ,
the duration will be 1 second  */


public class SimpleDailyEvent
{


    HomeEventType type;

    Date startDate; // day, hours, minute

    Date endDate;   // day, hours, minute

    String metaData;

    boolean mandatoryEvent = false;



    public void DayEvent(HomeEventType type, Date startDate, Date endDate, String data) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.metaData = data;
    }

    public HomeEventType getType() {
        return type;
    }

    public void setType(HomeEventType type) {
        this.type = type;
    }

    public void setType(String type) {


        // SLEEP , TOILET, NOTINHOME, DOVISITOR, DOEAT, DOPHONE, DOCOOK, DOMUSIC, DOTV,
        //         DOBOOK, DOPHYSICAL, DOSOCIAL
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

        System.out.println("ERROR: unidentified event type - " + type);
        System.exit(0);
    }



    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDateStr) {

        String tmp;
        startDate = new Date();


        if(startDateStr.length() != 5) {
            System.out.println("error in startDateStr format");
            System.exit(0);
        }

        tmp = startDateStr.substring(0,2);
        int hour = (int)Integer.parseInt(tmp);

        tmp = startDateStr.substring(3,5);

        int min = (int)Integer.parseInt(tmp);

        startDate.setHours(hour);
        startDate.setMinutes(min);

    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDateStr) {

        String tmp;
        endDate = new Date();

        if(endDateStr.length() != 5)
            System.out.println("error in endDataStr format");

        tmp = endDateStr.substring(0,2);
        int hour = (int)Integer.parseInt(tmp);

        tmp = endDateStr.substring(3,5);

        int min = (int)Integer.parseInt(tmp);

        endDate.setHours(hour);
        endDate.setMinutes(min);
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public void setMandatoryEvent(String mandatoryFlagStr) {
        if(mandatoryFlagStr.matches("YES")) {
            this.mandatoryEvent = true;
            return;
        }

        if(mandatoryFlagStr.matches("NO")) {
            this.mandatoryEvent = false;
            return;
        }

        System.out.println("Error in mandatory event flag parsing");
        System.exit(0);
    }

    /* get activity duration (in seconds) */
    long getDurationInSecs(){

        long duration  = endDate.getTime() - startDate.getTime();

        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        //long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        //long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

        return diffInSeconds;
    }

    @Override
    public String toString() {
        return  '\n' +  "SimpleDailyEvent{" +
                "type=" + type +
                ", startTime = " + startDate.getHours() + ":" + startDate.getMinutes() +
                ", endTime = " + endDate.getHours() + ":" + endDate.getMinutes() +
                ", meta = " + metaData +
                ", mandatroy='" + mandatoryEvent + '\'' +
                '}';
    }
}
