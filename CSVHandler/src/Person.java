import definitions.*;

import java.lang.reflect.AccessibleObject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Person {

    private int id;

    private int age;
    private Gender gender;
    private PersonaProfile persona;

    /**
     * For each potential kYou suggested activity the attitude of the persona for each activity is defined
     * Scale from 0-10
     * 0 - like it at all. will never do it.
     * 10 - like it a lot. will always do it.
     *
     * Potential events include: DOSKYPE, DOMUSIC, DOTV, DOBOOK, DOPHYSICAL, DOSOCIAL
     */

    private double attToSkype = 0.5;
    private double attToMusic = 0.5;
    private double attToTV = 0.5;
    private double attToBook = 0.5;
    private double attToPhysical = 0.5;
    private double attToSocial = 0.5;

    // interval between events generation for this person
    int minutesInterval;


    /* when is the person most active and energized */
    private BioClockType bioType;


    /**
     * Represent how close (probability) the person is to the persona associated with him.
     * 0 - not at all
     * 1 - compeltely
     *
     * This is used to generate the specific behaviour of the person. it is guided by the community overall variance
    * */
    private double varianceFromTemplate;

    /* simulated events of that person */
    private List<SimEvent> simEvents;

    /* person weekday daily profile */
    private List<SimpleDailyEvent> weekDayTemplateEvents;

    /* person weekend daily profile */
    private List<SimpleDailyEvent> weekEndDayTemplateEvents;

    /* person assigned kYou Profile */
    private kYouProfile mykYou;


    public Person() {
        simEvents = new ArrayList<SimEvent>();
        mykYou = new kYouProfile();
    }


    /**
     * specific people attributes are calculated based on the person persona attributes + a variance based on the
     * overall community behavioral varinace.
     *
     * For example:
     * Persona social-att is 0.5
     * Population behavioral variance is 0.5, i.e. 50%
     *
     * The person social attribute will be a random number in the range of 0.5-50%, 0.5+50%
     */


    public void generateAttributes(double communityVariance) {

        // set skype attribute
        double attribute = persona.getAttToSkype();
        attToSkype = generateFactoredAttribute(attribute,communityVariance);
        //System.out.println("Skype attribute: " + attribute + " , variance: " + communityVariance + " , factored attirbute " + attToSkype);

        // set music attribute
        attribute = persona.getAttToMusic();
        attToMusic = generateFactoredAttribute(attribute,communityVariance);
        //System.out.println("Music attribute: " + attribute + " , variance: " + communityVariance + " , factored attirbute " + attToMusic);

        // set TV attribute
        attribute = persona.getAttToTV();
        attToTV = generateFactoredAttribute(attribute,communityVariance);
        //System.out.println("TV attribute: " + attribute + " , variance: " + communityVariance + " , factored attirbute " + attToTV);

        // set book attribute
        attribute = persona.getAttToBook();
        attToBook = generateFactoredAttribute(attribute,communityVariance);
        //System.out.println("Book attribute: " + attribute + " , variance: " + communityVariance + " , factored attirbute " + attToBook);

        // set physical attribute
        attribute = persona.getAttToPhysical();
        attToPhysical = generateFactoredAttribute(attribute,communityVariance);
        //System.out.println("Physical attribute: " + attribute + " , variance: " + communityVariance + " , factored attirbute " + attToPhysical);

        // set social attribute
        attribute = persona.getAttToSocial();
        attToSocial = generateFactoredAttribute(attribute,communityVariance);
        //System.out.println("Social attribute: " + attribute + " , variance: " + communityVariance + " , factored attirbute " + attToSocial);

    }

    /**
     * Generate the  simulated events based on the person persona, daily template, personal attributes and simulation configuration
     * Assumes that the simulation starts on the first day of the week. Day 6 and 7 of the week are weekend days
     * @param numberOfDays
     */
    public void generateSimEvents(int numberOfDays){

        boolean weekEndFlag;



        for(int day = 1 ; day < numberOfDays+1 ; day++) {
            // reset kYou log
            mykYou.setLastSuggestedActivityTimeStamp(0);

            weekEndFlag = false;

            if (((day % 6) == 0) || ((day % 7) == 0))
                weekEndFlag = true;

            generateDayEvents(day, weekEndFlag);
        }
    }

    /**
     * Generate a single day simulated events
     *
     * /*

     Day template
     {"type": "TOILET",  "start" : "00:00", "end" :"02:10", "meta-data": "none", "mandatory" : "NO"},
     {"type": "SLEEP",   "start" : "02:10", "end" :"06:30", "meta-data": "none", "mandatory" : "YES"},
     {"type": "EAT",   "start" : "09:00", "end" :"09:40", "meta-data": "none", "mandatory" : "YES"},
     {"type": "TV",    "start" : "10:00", "end" :"14:10", "meta-data": "none", "mandatory" : "NO"}
     {"type": "MOOD",    "start" : "15:00", "end" :"15:20", "meta-data": "NEUTRAL", "mandatory" : "YES"},
     {"type": "EAT",   "start" : "18:20", "end" :"19:00", "meta-data": "none", "mandatory" : "YES"},
     {"type": "TOILET",  "start" : "21:30", "end" :"21:40", "meta-data": "none", "mandatory" : "NO"},
     {"type": "SLEEP",   "start" : "22:10", "end" :"00:00", "meta-data": "none", "mandatory" : "YES"}

     ASSUMES ALL EVENTS ARE TIME ORDERED




        go on every X min interval and decide which event to generate (


        iterate on each event e from template {
            check if event happens based on probability and variance (0 means not happened, 1 means happened regardless of variance
            generate variance starting time / end-time
            for the duration of the event in given intervals
                if e happened
                    generate original event with original type
                  else
                    generate event with unknown type

            decide if kYou should intervine
                if yes
                    generate intervension type
                    generate kYou initiated event
                    decide if it was sucsuful or not
                    if success
                        generate intervension casue events for X duration

            }


     * @param dayID
     */

    private void generateDayEvents(int dayID, boolean weekEnd) {

        // in minutes
        int currenTime = 0;

        List<SimpleDailyEvent> dayTemplate;

        if (weekEnd)
            dayTemplate = weekDayTemplateEvents;
        else
            dayTemplate = weekDayTemplateEvents;


        double eventProbability;
        double factoredProbability;
        Random rand = new Random();

        boolean eventIsOn = false;

        int endEventTime = 0;
        SimpleDailyEvent templateEvent;
        SimpleDailyEvent eventInProgress = null;



        for (int timeSlot = 0; timeSlot < (24 * 60) / minutesInterval; timeSlot++) {
            SimEvent event = new SimEvent();
            boolean kEventAddedFlag = false;
            event.setPersonID(this.id);
            event.setDayNum(dayID);
            event.setHour(currenTime / 60);
            event.setMinute(currenTime % 60);
            event.setInitiaor(ActivityInitiator.PERSON);
            event.setPersonActivityType(HomeEventType.UNKNOWN);
            event.setAcceptedEvent(false);

            // check if this is a starting point of a new event
            templateEvent = persona.getWeekdayRoutine().isEventStartTime(dayTemplate,currenTime);

            // check if it is time to start a new event
            if (templateEvent == null) {
                // check if an existing event is in progress
                if ((eventIsOn) && (eventInProgress.getEndMinuteOfDay() != currenTime)) {
                    // event in progress didnt end yet.
                    event.setInitiaor(ActivityInitiator.PERSON);
                    event.setPersonActivityType(eventInProgress.getType());

                    if (eventInProgress.getType() == HomeEventType.MOOD) {
                        // "dress up" a mood event to be kYou initiated
                        event.setMetaData(eventInProgress.getMetaData());
                        event.setInitiaor(ActivityInitiator.KYOU);
                        event.setkYouActivityType(kYouActivities.CHECKMOOD);
                        event.setPersonActivityType(HomeEventType.UNKNOWN);

                    }
                } else
                // no event in progress and no new event
                {
                    eventIsOn = false;
                    eventInProgress = null;

                    /*
                        logic for evaluating and generating a kYou driven event.
                        If kYou suggestion is accepted, a new event


                     */
                    System.out.println("DATA GEN: kYou deciding if to generate an intervention");

                    if( mykYou.wantToSuggestAnActivity(currenTime)) {
                        System.out.println("DATA GEN: potential kYou event generated ");

                        kYouActivities kActivity = mykYou.generateActivitySuggestion();
                        System.out.println("DATA GEN: potential kYou event (" + kActivity + ") is evaluated ");

                        //generate cause event
                        //if accepted, then generate affect
                        SimEvent kYouEvent = new SimEvent(this.id, dayID, event.getHour(), event.getMinute(),
                                ActivityInitiator.KYOU, HomeEventType.UNKNOWN, kActivity, 0, false);

                        simEvents.add(kYouEvent);

                        mykYou.setLastSuggestedActivity(kActivity);
                        mykYou.setLastSuggestedActivityTimeStamp(currenTime);

                        System.out.println("DATA GEN: Adding a new kYou event");
                        System.out.println(kYouEvent);

                        if(acceptKYouActivity(kActivity)){

                            // generate affect event (for now a single instance, 1 minute later after kYou suggestion

                            // indicate that the event was accepted
                            kYouEvent.setAcceptedEvent(true);
                            System.out.println("DATA GEN: kYou event (" + kActivity + ") was accepted ");
                            
                            // convert kYou activity to effect event on person (e.g. suggestmusic--> music

                            SimEvent kYouDrivenEvent = new SimEvent();
                            kYouDrivenEvent.setPersonID(this.id);
                            kYouDrivenEvent.setDayNum(dayID);
                            kYouDrivenEvent.setHour(event.getHour());
                            kYouDrivenEvent.setMinute(event.getMinute() + 1);
                            kYouDrivenEvent.setInitiaor(ActivityInitiator.PERSON);
                            kYouDrivenEvent.setPersonActivityType(mykYou.convertCasueToAffect(kActivity));
                            kYouDrivenEvent.setMetaData(0);

                            simEvents.add(kYouDrivenEvent);

                            System.out.println("DATA GEN: Adding a new kYou event");
                            System.out.println(kYouDrivenEvent);
                            kEventAddedFlag = true;

                        }
                        // decide which event to try (random)
                        // check if event was accepeted by person (based on persona and person specific attributes)
                        // if event was accepted, generate related event actions and update flags/time counters

                    } else {
                        System.out.println("DATA GEN: kYou event rejected due to kYou ops time or last interval (" + currenTime + ")");
                    }
                }

            } else
            // found an event in template
            {
                // decide if this event is going to happen
                eventProbability = templateEvent.getProbability();
                double random = rand.nextDouble();

                // if probability is 0 so the event will never happen
                // generate event only if probability
                if (eventProbability > 0) {
                    if ((eventProbability == 1) || (eventProbability > random)) {

                        // set event attirbutes
                        event.setInitiaor(ActivityInitiator.PERSON);
                        event.setPersonActivityType(templateEvent.getType());

                        // check if a Mood event. if yes, generate it as a kYou evnet.
                        if (templateEvent.getType() == HomeEventType.MOOD) {

                            // make it look like it is a kYou moodcheck event
                            event.setMetaData(templateEvent.getMetaData());
                            event.setInitiaor(ActivityInitiator.KYOU);
                            event.setkYouActivityType(kYouActivities.CHECKMOOD);
                            event.setPersonActivityType(HomeEventType.UNKNOWN);

                        }

                        eventInProgress = templateEvent;
                        eventIsOn = true;

                    } else {
                        System.out.println("Event didnt pass probability. EventProbability:" + eventProbability + ", random:" + random);
                        System.out.println(templateEvent);
                    }

                }
            }

            if(kEventAddedFlag == false) {
                System.out.println("DATA GEN: Adding event to person " + this.id);
                System.out.println(event);
                this.simEvents.add(event);
            }

            currenTime += minutesInterval;
        }
            // update flags and state, endTime
    }



    /* calcualate factored attirbutes based on variance and persona */

    private double generateFactoredAttribute(double attribute, double variance){

        // set book attribute
        double factoredAttribute;
        double factor;
        boolean negative;
        Random rand = new Random();


        /* attToBook attribute */
        negative = rand.nextBoolean();
        factor = rand.nextDouble() * variance;

        if (negative == true)
            factor = -factor;

        factoredAttribute = attribute + (attribute * factor);

        if(factoredAttribute < 0) factoredAttribute = 0;
        if(factoredAttribute > 1) factoredAttribute = 1;

        DecimalFormat df = new DecimalFormat("#.##");
        factoredAttribute = Double.valueOf(df.format(factoredAttribute));

        return factoredAttribute;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public PersonaProfile getPersona() {
        return persona;
    }

    public void setPersona(PersonaProfile persona) {
        this.persona = persona;
        weekDayTemplateEvents = persona.getWeekendRoutine().duplicateEvents();
        weekEndDayTemplateEvents = persona.getWeekendRoutine().duplicateEvents();
    }

    public double getVarianceFromTemplate() {
        return varianceFromTemplate;
    }

    public void setVarianceFromTemplate(double varianceFromTemplate, int intervals) {
        this.varianceFromTemplate = varianceFromTemplate;

        updateTemplateEventTime((ArrayList)weekDayTemplateEvents,varianceFromTemplate, intervals);
        updateTemplateEventTime((ArrayList)weekEndDayTemplateEvents,varianceFromTemplate, intervals);

    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public BioClockType getBioType() {
        return bioType;
    }

    public void setBioType(BioClockType bioType) {
        this.bioType = bioType;
    }

    public double getAttToSkype() {
        return attToSkype;
    }

    public void setAttToSkype(double attToSkype) {
        this.attToSkype = attToSkype;
    }

    public double getAttToMusic() {
        return attToMusic;
    }

    public void setAttToMusic(double attToMusic) {
        this.attToMusic = attToMusic;
    }

    public double getAttToTV() {
        return attToTV;
    }

    public void setAttToTV(double attToTV) {
        this.attToTV = attToTV;
    }

    public double getAttToBook() {
        return attToBook;
    }

    public void setAttToBook(double attToBook) {
        this.attToBook = attToBook;
    }

    public double getAttToPhysical() {
        return attToPhysical;
    }

    public void setAttToPhysical(double attToPhysical) {
        this.attToPhysical = attToPhysical;
    }

    public double getAttToSocial() {
        return attToSocial;
    }

    public void setAttToSocial(double attToSocial) {
        this.attToSocial = attToSocial;
    }

    public List<SimEvent> getSimEvents() {
        return simEvents;
    }

    public int getMinutesInterval() {
        return minutesInterval;
    }

    public void setMinutesInterval(int minutesInterval) {
        this.minutesInterval = minutesInterval;
    }

    public kYouProfile getMykYYouProfile() {
        return mykYou;
    }

    public void setMykYYouProfile(kYouProfile kProfile) {
        this.mykYou = kProfile;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", age=" + age +
                ", gender=" + gender +
                ", persona=" + persona +
                ", attToSkype=" + attToSkype +
                ", attToMusic=" + attToMusic +
                ", attToTV=" + attToTV +
                ", attToBook=" + attToBook +
                ", attToPhysical=" + attToPhysical +
                ", attToSocial=" + attToSocial +
                ", minutesInterval=" + minutesInterval +
                ", bioType=" + bioType +
                ", varianceFromTemplate=" + varianceFromTemplate +
                ", simEvents=" + simEvents +
                //", weekDayTemplateEvents=" + weekDayTemplateEvents +
                //", weekEndDayTemplateEvents=" + weekEndDayTemplateEvents +
                ", mykYou=" + mykYou +
                '}';
    }


    /**
     * Decides if the person accepts the activity suggested by kYou.
     * The decision is based on the person attitude to different activites, and his variance (to have some random effect)
     *
     * FOR NOW ALWAYS ACCEPTS REGARDLESS OF PROFILE
     * @param suggestedActivity
     * @return
     */
    private boolean acceptKYouActivity(kYouActivities suggestedActivity){

        return true;
    }
  /*
    * pass over person day event templates and update the start/end time based on variance input.
    * The variance is within a 1 hour window. 20% means originial time +60*20% , -60*20% (rounded to 5 min jumps)
    *
    * */

    private void updateTemplateEventTime(ArrayList<SimpleDailyEvent> inputTemplate, double varianceFromTemplate, int eventsTimeInterval) {

        for(int i = 0; i < inputTemplate.size() ; i++){
            SimpleDailyEvent event = inputTemplate.get(i);
            int startMin = event.getStartMinuteOfDay();
            int endMin = event.getEndMinuteOfDay();
            int newMin = 0;

            //System.out.println("Event before time variance (" + varianceFromTemplate + ") factor: " + event);

            /*
             update start time
             update only events not starting at midnight (which will be sleep)
            */


            if((startMin != 0)){
                Random rand = new Random();
                double variance = (rand.nextDouble() * 60) * varianceFromTemplate;
                int factor = (int) variance;


                // need to round to interval steps
                factor = (factor / eventsTimeInterval) * eventsTimeInterval;
                boolean negative = rand.nextBoolean();

                if(negative){
                    newMin = startMin - factor;

                    if((newMin > 0) && (newMin < endMin)){
                        event.setStartMinInTheDay(newMin);
                    }

                } else {
                    newMin = startMin + factor;

                    if (newMin < 60 * 24) {
                        event.setStartMinInTheDay(newMin);
                    }
                }
            }

            /*
             update end time
             update only events not ending at midnight (which will be sleep)
            */

            newMin = 0;

            if((endMin != 0)){
                Random rand = new Random();
                double variance = (rand.nextDouble() * 60) * varianceFromTemplate;
                int factor = (int) variance;

                // need to round to interval steps
                factor = (factor / eventsTimeInterval) * eventsTimeInterval;
                boolean negative = rand.nextBoolean();

                if(negative){
                    newMin = endMin - factor;

                    if((newMin > 0) && (newMin > startMin)){
                        event.setEndMindInTheDay(newMin);
                    }

                } else {
                    newMin = endMin + factor;

                    if (newMin < 60 * 24) {
                        event.setEndMindInTheDay(newMin);
                    }
                }
            }

            //System.out.println("Event after time variance (" + varianceFromTemplate + ")factor: " + event);
        }
    }


}
