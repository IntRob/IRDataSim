import definitions.ProbabilityDistribution;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * This object loads and hold all the simulation input data
 *
 * Created by admin on 11/24/15.
 */
public class SimLoader {


    // holds kYou profile
    static private kYouProfile kProfile;

    // holds simulation params
    static private SimConfig simConfig;

    // holds the community profile
    static private SimulatedCommunity simulatedCommunity;

    // hold all persona's
    static private List<DailyTemplate> dailyTemplates;


    public SimLoader() {
        System.out.println("Loading simulation data");

        simulatedCommunity = new SimulatedCommunity();
        dailyTemplates = new ArrayList<DailyTemplate>();
        simConfig = new SimConfig();

        // for now using the default hardcoded kYou profile
        kProfile = new kYouProfile();

        // turn kYou on
        kProfile.setOnButton(true);
        kProfile.setStartMinOfDayOperation(6 * 60); // starts operating from 06:00
        kProfile.setEndMinOfDayOperation(23 * 60);  // stop at 23:00

        // FOR NOW THIS PARAMETER IS IGNORED
        kProfile.setMaxNumberOfDailyInterventions(50);

    }




    static public void readDailyTemplatesJson(String fileName) {



        /**
         *
         {
         "daily-templates" : [
         {
         "template-id" : "1",
         "template-desc" : "normal",
         "events": [
         {"type": "TOILET",  "start" : "00:00", "end" :"02:10", "meta-data": "none", "probability" : "0.5"},
         {"type": "SLEEP",   "start" : "02:10", "end" :"06:30", "meta-data": "none", "probability" : "1"},
         {"type": "EAT",   "start" : "09:00", "end" :"09:40", "meta-data": "none", "probability" : "1"},
         {"type": "TV",    "start" : "10:00", "end" :"14:10", "meta-data": "none", "probability" : "1"}
         {"type": "MOOD",    "start" : "15:00", "end" :"15:20", "meta-data": "NEUTRAL", "probability" : "0"},
         {"type": "EAT",   "start" : "18:20", "end" :"19:00", "meta-data": "none", "probability" : "1"},
         {"type": "TOILET",  "start" : "21:30", "end" :"21:40", "meta-data": "none", "probability" : "0.8"},
         {"type": "SLEEP",   "start" : "22:10", "end" :"00:00", "meta-data": "none", "probability" : "1"},
         ]
         },

         {
         "template-id" : "2",
         "template-desc" : "normal",
         "events": [
         {"type": "SLEEP",   "start" : "02:10", "end" :"06:30", "meta-data": "none", "probability" : "1"},
         {"type": "PHONE", "start" : "06:40", "end" :"08:10", "meta-data": "none", "probability" : "0.3"},
         {"type": "EAT",   "start" : "09:00", "end" :"09:40", "meta-data": "none", "probability" : "0.9"},
         ]
         }
         ]
         }
         * @param fileName
         */


        JSONParser parser = new JSONParser();

        try {
            System.out.println("Reading " + fileName);
            FileReader fileReader = new FileReader(fileName);
            JSONObject json = (JSONObject) parser.parse(fileReader);

            // loading daily templates
            System.out.println("Reading daily templates ");

            JSONArray templates = (JSONArray) json.get("daily-templates");
            ListIterator i = templates.listIterator();



            System.out.println("Number of daily templates : " + templates.size());


            /* read each template */
            for(int templateIndex = 0; templateIndex < templates.size() ; templateIndex++) {

                JSONObject obj = (JSONObject) i.next();
                //System.out.println("Obj: " + obj);

                DailyTemplate template = new DailyTemplate();
                int templateID = (int) Integer.parseInt((String) obj.get("template-id"));
                String templateDesc = (String) obj.get("template-desc");
                template.setId(templateID);
                template.setDescription(templateDesc);

                JSONArray events = (JSONArray) obj.get("events");
                ListIterator eventsi = events.listIterator();

                System.out.println("Number of events in template : " + events.size());
                System.out.println("Loading template events");

                for(int eventIndex = 0; eventIndex < events.size() ; eventIndex++){
                    JSONObject eventsObj = (JSONObject) eventsi.next();
                    //System.out.println("EventObj: " + eventsObj);

                    SimpleDailyEvent event = new SimpleDailyEvent();

                    String eventTypeStr = (String)eventsObj.get("type");
                    String startTimeStr = (String) eventsObj.get("start");
                    String endTimeStr = (String) eventsObj.get("end");
                    int metaData = Integer.parseInt((String)eventsObj.get("meta-data"));
                    double probability =  Double.parseDouble((String) eventsObj.get("probability"));

                    event.setType(eventTypeStr);
                    event.setStartDate(startTimeStr);
                    event.setEndDate(endTimeStr);
                    event.setMetaData(metaData);
                    event.setProbability(probability);
                    template.addEvent(event);
                    //System.out.println("event loaded");
                }

                dailyTemplates.add(template);
                System.out.println("TEMPLATE LOADED");
                //System.out.println(template);
            }

        } catch (Exception e) {
            System.out.println(e);
        }


    }



    /*
     * read JSON siminputs file
     */
    static public void readSimInputsJson(String fileName) {
        JSONParser parser = new JSONParser();
        PersonaProfile profile;

        try {
            System.out.println("Reading " + fileName);
            FileReader fileReader = new FileReader(fileName);
            JSONObject json = (JSONObject) parser.parse(fileReader);


            // load sim config params
            System.out.println("Reading sim params ");
            System.out.println("Reading simulation configuration profile ");

            int numOfDays = (int) Integer.parseInt((String) json.get("simulation-numOfDays"));

            simConfig = new SimConfig(numOfDays);

            // load community params


            /**
             *
             "simulation-numOfDailyTemplates": "5",
             "simulation-numOfDays": "1000",
             "community-size": "30",
             "community-minAge": "70",
             "community-maxAge": "85",
             "community-ageDirstibution": "Random",
             "community-bioClockDistribution": "Random",
             "community-percentageMale": "50",
             "community-variance" : "0.5"
             "community-persona-split": [
             {
             "Number-of-persona-types": "3",
             "Persona0": "5",
             "Persona1": "10",
             "Persona2": "15"
             }
             ],
             */
            // load community profile

            simulatedCommunity = new SimulatedCommunity();


            System.out.println("Reading community profile ");

            simulatedCommunity.setSize(Integer.parseInt((String) json.get("community-size")));
            simulatedCommunity.setMinAge(Integer.parseInt((String) json.get("community-minAge")));
            simulatedCommunity.setMaxAge(Integer.parseInt((String) json.get("community-maxAge")));

            String ageDistribution = (String) json.get("community-ageDirstibution");
            ProbabilityDistribution distribution = ProbabilityDistribution.NORMAL;

            if (ageDistribution.contentEquals(ProbabilityDistribution.RANDOM.toString()))
                distribution = ProbabilityDistribution.RANDOM;

            simulatedCommunity.setAgeDistribution(distribution);
            simulatedCommunity.setMalePercentile(Integer.parseInt((String) json.get("community-percentageMale")));
            simulatedCommunity.setVariance(Double.parseDouble((String) json.get("community-variance")));


            String BioClockDistribution = (String) json.get("community-bioClockDistribution");
            distribution = ProbabilityDistribution.NORMAL;

            if (BioClockDistribution.contentEquals(ProbabilityDistribution.RANDOM.toString()))
                distribution = ProbabilityDistribution.RANDOM;

            simulatedCommunity.setBioClockDistribution(distribution);


            // loading persona split config
            JSONArray personaSplit = (JSONArray) json.get("community-persona-split");
            ListIterator i = personaSplit.listIterator();



            System.out.println("Persona types split array size: " + personaSplit.size());

            /**
             * loading persona types split
             * {
             "Number-of-persona-types": "3",
             "Persona0": "5",
             "Persona1": "5",
             "Persona2": "10"
             }
             */


            JSONObject obj = (JSONObject) i.next();
            int numOfPersonas = (int) Integer.parseInt((String) obj.get("Number-of-persona-types"));
            simulatedCommunity.setPersonaTypeNum(numOfPersonas);
            System.out.println("number of persona types: " + numOfPersonas);

            int personaNum;
            simulatedCommunity.getPersonaSplit();

            for (int index = 0; index < numOfPersonas; index++) {
                //System.out.println("Obj: " + obj);

                String label = "Persona" + Integer.toString(index);
                profile = new PersonaProfile();
                profile.setID(index);
                profile.setName(label);

                simulatedCommunity.getPersonaProfiles().add(profile);
                personaNum = Integer.parseInt((String) obj.get(label));
                simulatedCommunity.setPersonaTypeSize(index, personaNum);

                System.out.println("number of people with " + label +  " type is: " + personaNum);
            }

            /**
             * load persona's profiles

             "name" : "Persona2",
             "description": "Happy",
             "weekdaytemplateID": "2",
             "weekendtemplateID": "2",
             "attVariance" : "0",
             "attToMusic" : "0.4",
             "attToBook" : "0.5",
             "attToPhysical" : "0.6",
             "attToTV" : "0.7",
             "attToSocial" : "0.8",
             "attToSkype" : "0.9",
             "attToRoutine" : "1"


             */

            JSONArray personaDetails = (JSONArray) json.get("personas");
            i = personaDetails.listIterator();


            System.out.println("number of persona types: " + personaDetails.size());

            for (int index = 0; index < personaDetails.size(); index++) {

                obj = (JSONObject) i.next();

                profile = simulatedCommunity.getPersonaProfile(index);

                System.out.println("loading persona " + profile.getName());
                //System.out.println("Persona details: " + obj);

                profile.setTypeDescriptor((String) obj.get("description"));

                String weekdayRoutineID = ((String) obj.get("weekdaytemplateID"));
                profile.setWeekdayRoutine(getDailyTemplateByID(Integer.parseInt(weekdayRoutineID)));

                String weekendRoutineID = ((String) obj.get("weekendtemplateID"));
                profile.setWeekendRoutine(getDailyTemplateByID(Integer.parseInt(weekendRoutineID)));


                // load attitude for each activity
                profile.setAttToMusic(Double.parseDouble((String) obj.get("attToMusic")));
                profile.setAttToPhone(Double.parseDouble((String) obj.get("attToSkype")));
                profile.setAttToTV(Double.parseDouble((String) obj.get("attToTV")));
                profile.setAttToPhysical(Double.parseDouble((String) obj.get("attToPhysical")));
                profile.setAttToSocial(Double.parseDouble((String) obj.get("attToSocial")));
                profile.setAttToBook(Double.parseDouble((String) obj.get("attToBook")));
                profile.setAttToRoutine(Double.parseDouble((String) obj.get("attToRoutine")));

                System.out.println("Persona profile loaded");
                //System.out.println(profile);

            }


        } catch (Exception e) {
            System.out.println("Exception while loading JSON input");
            System.out.println(e);

        }

    }


    static private DailyTemplate getDailyTemplateByID(int id){

        ListIterator i = dailyTemplates.listIterator();


        while (i.hasNext()) {
            DailyTemplate template = (DailyTemplate) i.next();
            if (template.getId() == id)
                return template;
        }

        System.out.println("ERROR: Template ID " + id + " not found");
        System.exit(1);

        return null;
    }


    public static kYouProfile getkProfile() {
        return kProfile;
    }

    public static void setkProfile(kYouProfile kProfile) {
        SimLoader.kProfile = kProfile;
    }

    public static SimulatedCommunity getSimulatedCommunity() {
        return simulatedCommunity;
    }

    public static SimConfig getSimConfig() {
        return simConfig;
    }
}
