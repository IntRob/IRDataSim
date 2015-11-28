import definitions.HomeEventType;
import definitions.Moods;
import definitions.kYouActivities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

/**
 * Created by itaimendelsohn on 11/26/15.
 * Holds the different ML (per activity) objects, generates them (including the logic to do so).
 */
public class MLHolder {

    // set of thresholds for the state analysis
    private static int ATE_THRESHOLD = 2;
    private static int WALK_THRESHOLD = 2;
    private static int MUSIC_THRESHOLD = 2;
    private static int SPOKE_THRESHOLD = 2;
    private static int ACTIVE_THRESHOLD = 2;

    // convert the enums to int for own usage
    private static final int MOOD_HAPPY = 0;//Moods.HAPPY.ordinal(); TODO: fix this ugly thing
    private static final int MOOD_SAD = 1;//Moods.SAD.ordinal();
    private static final int MOOD_ANGRY = 2;//Moods.ANGRY.ordinal();



    // the raw data of all the events that happened
    private ArrayList <SimEvent> rawEvents;

    // the ML for the walks suggestions activity
    private ActivityML suggestWalkML;

    // the ML for the music suggestions activity TODO" add more MLs
    private ActivityML suggestMusicML;

    public MLHolder(ArrayList<SimEvent> rawEvents) {
        this.rawEvents = rawEvents;
        suggestWalkML = new ActivityML(kYouActivities.SUGGESTWALK);
        suggestMusicML = new ActivityML(kYouActivities.SUGGESTMUSIC);
    }

    //Fills all the MLs in the object (kind of a 'main")
    public void init()
    {
        intiWalkML();
        initMusicML();
    }

    // Here is all the logic to create the relevant events
    private void intiWalkML()
    {
        ListIterator<SimEvent> iterator = rawEvents.listIterator();
        while (iterator.hasNext()) {
            SimEvent rawEvent = iterator.next();

            if (rawEvent.getkYouActivityType() == kYouActivities.SUGGESTWALK) // looks for all walk activities in the raw data
            {
                MLEvent mlEvent = new MLEvent(rawEvent.getPersonID(),rawEvent.getDayNum(),rawEvent.getHour(),rawEvent.getMinute(),rawEvent.getkYouActivityType(),rawEvent.getMetaData());
                mlEvent.setAteState(analyzeAteState(iterator.nextIndex()));
                mlEvent.setMoodState(analyzeMoodState(iterator.nextIndex()));
                mlEvent.setSpokeState(analyzeSpokeState(iterator.nextIndex()));
                mlEvent.setWasActiveState(analyzeWasActiveState(iterator.nextIndex()));
                // temp code!! TODO: fix with Roy
                Random rand = new Random();
                boolean accepted = rand.nextBoolean();
                mlEvent.setAccepted(accepted);
                suggestWalkML.addEvent(mlEvent);

                System.out.println("WalkML" + mlEvent);
            }

        }
    }

    // Here is all the logic to create the relevant events
    private void initMusicML()
    {
        ListIterator<SimEvent> iterator = rawEvents.listIterator();
        while (iterator.hasNext()) {
            SimEvent rawEvent = iterator.next();

            if (rawEvent.getkYouActivityType() == kYouActivities.SUGGESTMUSIC) // looks for all walk activities in the raw data
            {
                MLEvent mlEvent = new MLEvent(rawEvent.getPersonID(),rawEvent.getDayNum(),rawEvent.getHour(),rawEvent.getMinute(),rawEvent.getkYouActivityType(),rawEvent.getMetaData());
                mlEvent.setAteState(analyzeAteState(iterator.nextIndex()));
                mlEvent.setMoodState(analyzeMoodState(iterator.nextIndex()));
                mlEvent.setSpokeState(analyzeSpokeState(iterator.nextIndex()));
                mlEvent.setWasActiveState(analyzeWasActiveState(iterator.nextIndex()));
                // temp code!! TODO: fix with Roy
                Random rand = new Random();
                boolean accepted = rand.nextBoolean();
                mlEvent.setAccepted(accepted);

                suggestMusicML.addEvent(mlEvent);

                System.out.println("MusicML" + mlEvent);
            }
        }
    }

    // dump content into a CSV file
    public void dumpToCSV(SimOutputManager outputer)
    {
        suggestWalkML.dumpToCSV(outputer);
        suggestMusicML.dumpToCSV(outputer);
    }

    // helper to define how many event to analyze back when defining a state
    // returns the index to start from
    private int indexForPeriodToAnalyze(int currIndex)
    {
        int count = 12; // We have 5 min interval and we want an hour back (for now)
        if ( count > currIndex)
            return  0; // we have less events than the desired count
        else
            return (currIndex - count); // we start to look based on count
    }

    private boolean analyzeSpokeState(int currIndex)
    {
        int  i = indexForPeriodToAnalyze(currIndex);
        int spoke = 0;

        for (; i < currIndex ; i++) { // go over all events till the current index
            SimEvent rawEvent = rawEvents.get(i);
            // We are trying naivelly count events of social interaction (spoke to someone)
            if ( rawEvent.getPersonActivityType() == HomeEventType.PHONE || rawEvent.getPersonActivityType() == HomeEventType.SOCIAL
                    || rawEvent.getPersonActivityType() == HomeEventType.NOTINHOME || rawEvent.getPersonActivityType() == HomeEventType.VISITOR)
                spoke++;
        }

        if ( spoke >= SPOKE_THRESHOLD )
            return true;
        else
            return false;
    }

    private boolean analyzeAteState(int currIndex)
    {
        int i = indexForPeriodToAnalyze(currIndex);
        int ate = 0;

        for (; i < currIndex ; i++) { // go over all events till the current index
            SimEvent rawEvent = rawEvents.get(i);
            if ( rawEvent.getPersonActivityType() == HomeEventType.EAT)
                ate++;
        }
        if ( ate >= ATE_THRESHOLD )
            return true;
        else
            return false;
    }

    private boolean analyzeWasActiveState(int currIndex)
    {
        int active = 0;
        int i = indexForPeriodToAnalyze(currIndex);

        for (; i < currIndex ; i++) { // go over all events till the current index
            SimEvent rawEvent = rawEvents.get(i);
            if ( rawEvent.getPersonActivityType() == HomeEventType.PHYSICAL || rawEvent.getPersonActivityType() == HomeEventType.NOTINHOME
                    || rawEvent.getPersonActivityType() == HomeEventType.PLAY )
                active++;
            if (rawEvent.getkYouActivityType() == kYouActivities.SUGGESTWALK) // if walk was suggested, let's see if not in home. if so we can assume was active
            {
                // two step away shall be enough
                for (int j=i; (j < currIndex) || (j < i+2 ) ; j++)
                {
                    rawEvent = rawEvents.get(j);
                    if ( rawEvent.getPersonActivityType() == HomeEventType.NOTINHOME )
                        active++;
                }
            }
        }

        if ( active >= ACTIVE_THRESHOLD )
            return true;
        else
            return false;
    }

    private Moods analyzeMoodState(int currIndex) {
        int i = indexForPeriodToAnalyze(currIndex);// maybe for mood we need longer periods... TODO:
        int[] moodsArray = new int[Moods.values().length];
        for (int j=0; j<moodsArray.length; j++)
            moodsArray[j] = 0;

        for (; i < currIndex ; i++) { // go over all events till the current index
            SimEvent rawEvent = rawEvents.get(i);
            if (rawEvent.getkYouActivityType() == kYouActivities.CHECKMOOD) {
                int metadata = rawEvent.getMetaData();
                moodsArray[metadata]++; // updates per mood (HAPPY, SAD,etc')
            }
        }
        //find the highest one now
        int max = 0;
        int mood = -1;
        for ( i = 0 ; i < 3 ; i++)
        {
            if ( max < moodsArray[i]) {
                max = moodsArray[i]; // remember the highest one + value
                mood = i;
            }
        }

        switch (mood)
        {
            case MOOD_HAPPY:
                return Moods.HAPPY;
            case MOOD_SAD:
                return Moods.SAD;
            case MOOD_ANGRY:
                return Moods.ANGRY;
            default:
                return Moods.UNKNOWN;
        }
    }
}
