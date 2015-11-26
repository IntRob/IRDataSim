import definitions.AgeDistribution;
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

        // for now using the default hard coded kYou profile
        kProfile = new kYouProfile();

    }




    static public void readDailyTemplatesJson(String fileName) {



        /**
         *
         {
         "daily-templates" : [
         {
         "template-id" : "1",
         "template-name" : "normal",
         "events": [
         {"type": "TOILET",  "start" : "02:00", "end" :"02:10", "meta-data": "none", "mandatory" : "NO"},
         {"type": "SLEEP",   "start" : "02:10", "end" :"06:30", "meta-data": "none", "mandatory" : "YES"},
         {"type": "DOPHONE", "start" : "06:40", "end" :"08:10", "meta-data": "none", "mandatory" : "NO"},
         {"type": "DDEAT",   "start" : "09:00", "end" :"09:40", "meta-data": "none", "mandatory" : "YES"},
         {"type": "DOTV",    "start" : "10:00", "end" :"22:10", "meta-data": "none", "mandatory" : "NO"}
         ]
         },

         {
         "template-id" : "2",
         "template-name" : "normal",
         "events": [
         {"type": "TOILET",  "start" : "02:00", "end" :"02:10", "meta-data": "none", "mandatory" : "NO"},
         {"type": "SLEEP",   "start" : "02:10", "end" :"06:30", "meta-data": "none", "mandatory" : "YES"},
         {"type": "DOPHONE", "start" : "06:40", "end" :"08:10", "meta-data": "none", "mandatory" : "NO"},
         {"type": "DDEAT",   "start" : "09:00", "end" :"09:40", "meta-data": "none", "mandatory" : "YES"},
         {"type": "DOTV",    "start" : "10:00", "end" :"22:10", "meta-data": "none", "mandatory" : "NO"}
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
                    String metaDataStr = (String) eventsObj.get("meta-data");
                    String mandatoryStr = (String) eventsObj.get("mandatory");

                    event.setType(eventTypeStr);
                    event.setStartDate(startTimeStr);
                    event.setEndDate(endTimeStr);
                    event.setMetaData(metaDataStr);
                    event.setMandatoryEvent(mandatoryStr);
                    template.addEvent(event);
                    //System.out.println("event loaded");
                }

                dailyTemplates.add(template);
                System.out.println("TEMPLATE LOADED");
                System.out.println(template);
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

            int numOfTemplates = (int) Integer.parseInt((String) json.get("simulation-numOfDailyTemplates"));
            int numOfDays = (int) Integer.parseInt((String) json.get("simulation-numOfDays"));

            SimConfig simConfig = new SimConfig(numOfTemplates, numOfDays);

            // load community params


            /**
             *
             "community-size": "20",
             "community-minAge": "70",
             "community-maxAge": "85",
             "community-ageDirstibution": "Random",
             "community-percentageMale": "50",
             "community-variance" : "0.1",
             "communipersonaSplit": [
             {
             "0": "5",
             "1": "5",
             "2": "10"
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
            AgeDistribution ageDist = AgeDistribution.NORMAL;

            if (ageDistribution.contentEquals(AgeDistribution.RANDOM.toString()))
                ageDist = AgeDistribution.RANDOM;

            simulatedCommunity.setAgeDistribution(ageDist);
            simulatedCommunity.setMalePercentile(Integer.parseInt((String) json.get("community-percentageMale")));
            simulatedCommunity.setVariance(Float.parseFloat((String) json.get("community-variance")));


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
             * load persona details
             * "personas": [
             {
             "id": "Persona1",
             "description": "Germen",
             "weekdaytemplateID": "1",
             "weekendtemplateID": "1",
             "attToMusic" : "10",
             "attToaBook" : "5",
             "attToPhysical" : "7",
             "attToSocial" : "10",
             "attToRoutine" : "10",
             "attToSmallTalk" : "1"

             },



             */

            JSONArray personaDetails = (JSONArray) json.get("personas");
            i = personaDetails.listIterator();


            System.out.println("number of persona types: " + personaDetails.size());

            for (int index = 0; index < personaDetails.size(); index++) {

                obj = (JSONObject) i.next();

                profile = simulatedCommunity.getPersonaProfile(index);

                System.out.println("loading persona " + profile.getName());
                System.out.println("Persona details: " + obj);

                profile.setTypeDescriptor((String) obj.get("description"));

                String weekdayRoutineID = ((String) obj.get("weekdaytemplateID"));
                profile.setWeekdayRoutine(getDailyTemplateByID((int) Integer.parseInt(weekdayRoutineID)));

                String weekendRoutineID = ((String) obj.get("weekendtemplateID"));
                profile.setWeekendRoutine(getDailyTemplateByID((int) Integer.parseInt(weekendRoutineID)));

                // load attitude for each activity
                profile.setAttToMusic(Integer.parseInt((String) obj.get("attToMusic")));
                profile.setAttToPhone(Integer.parseInt((String) obj.get("attToPhone")));
                profile.setAttToTV(Integer.parseInt((String) obj.get("attToTV")));
                profile.setAttToPhysical(Integer.parseInt((String) obj.get("attToPhysical")));
                profile.setAttToSocial(Integer.parseInt((String) obj.get("attToSocial")));
                profile.setAttToBook(Integer.parseInt((String) obj.get("attToBook")));
                profile.setAttToRoutine(Integer.parseInt((String) obj.get("attToRoutine")));

                System.out.println("Persona profile loaded");
                System.out.println(profile);

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

    public static SimulatedCommunity getSimulatedCommunity() {
        return simulatedCommunity;
    }
}
