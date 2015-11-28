/**
 * Created by admin on 11/16/15.
 *
 * A community represents the total number of people in the simulation and the population characteristics
 */

import definitions.BioClockType;
import definitions.ProbabilityDistribution;
import definitions.Gender;

import java.util.*;

public class SimulatedCommunity {

    // number of people in the simulated community
    private int size;

    // elderly min age
    private int minAge;

    // elderly max age
    private int maxAge;

    // age distribution type
    private ProbabilityDistribution ageDistribution;

    // bio-clock type distribution
    private ProbabilityDistribution bioClockDistribution;

   // percentile of males in community
    private int malePercentile;

    // split of persona types across population (how many from type 0/1/2...
    private int [] personaSplit;

    // number of persona types in community
    private int personaTypeNum;

    // vairance of behaviour of community population. How much they stick to their dailyroutine
    private double variance;

    // hold all persona's
    private List<PersonaProfile> personaProfiles;

    // hold all people in the community
    private List<Person> peopleRecords;


    public SimulatedCommunity(int size, int minAge, int maxAge, ProbabilityDistribution ageDistribution, ProbabilityDistribution bioClockDistribution, int malePercentile, int[] personaSplit, int personaTypeNum, double variance, List<PersonaProfile> personaProfiles) {
        this.size = size;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.ageDistribution = ageDistribution;
        this.bioClockDistribution = bioClockDistribution;
        this.malePercentile = malePercentile;
        this.personaSplit = personaSplit;
        this.personaTypeNum = personaTypeNum;
        this.variance = variance;
        this.personaProfiles = personaProfiles;
        this.peopleRecords = new ArrayList<Person>();

    }

    // default construbtor
    public SimulatedCommunity() {
        this.size = 100;
        this.minAge = 70;
        this.maxAge = 85;
        this.ageDistribution = ProbabilityDistribution.RANDOM;
        this.bioClockDistribution = ProbabilityDistribution.RANDOM;
        this.malePercentile = 50;
        this.personaProfiles = new ArrayList<PersonaProfile>();
        this.peopleRecords = new ArrayList<Person>();

    }

    /**
     * Generates the people that are part of the community
     */

    public void generatePopulation (int timeInterval) {

        int id = 0;
        Random rand = new Random();


        System.out.println("Generating population (size " + size + ")");

        if(sumPersonaSplit() != this.size){
            System.out.println("ERORR: population size and persona split numbers dont match in simulation config file");
            System.exit(1);
        }

        // main people generation loop. for each persona type generate its people
        for(int i = 0; i < personaSplit.length ; i++) {
            PersonaProfile persona = this.personaProfiles.get(i);

            for (int j = 0; j < personaSplit[i]; j++) {
                Person person = new Person();

                // set Persona
                person.setPersona(persona);

                // set personal time interval for event generation
                person.setMinutesInterval(timeInterval);

                // all people have the same variance from template
                person.setVarianceFromTemplate(variance,timeInterval);

                person.setId(id);
                System.out.println("Generating person " + person.getId());
                // age distribution is random between min and max. Ignoring probability distribution type for now
                person.setAge(rand.nextInt((maxAge - minAge) + 1) + minAge);



                // set gender based on input gender distribution (male)
                Gender gender = Gender.MALE;
                int probability = rand.nextInt(100 + 1);
                if(probability > malePercentile){
                    gender = Gender.FEMALE;
                }

                person.setGender(gender);

                // set bioClock type
                BioClockType clockType = BioClockType.MORNINGTYPE;
                if (rand.nextInt(2) == 1)
                    clockType = BioClockType.EVENINGTYPE;

                person.setBioType(clockType);
                person.generateAttributes(variance);


                // add person to the population
                peopleRecords.add(person);
                System.out.println("DATAGEN : New Person added ");
                //System.out.println(person);
                id++;

            }

        }

    }

    /**
     * Generate the day events for all people in the population for the whole duration of the simulation (as in sim config)
     */

    public void generateEvents(int numOfDays){

        for(int i = 0; i < peopleRecords.size(); i++){
            Person person = peopleRecords.get(i);

            person.generateSimEvents(numOfDays);
            //System.out.println("Person " + person.getId() + "events: ");
            //System.out.println(person.getSimEvents());
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public ProbabilityDistribution getAgeDistribution() {
        return ageDistribution;
    }

    public void setAgeDistribution(ProbabilityDistribution ageDistribution) {
        this.ageDistribution = ageDistribution;
    }

    public int getMalePercentile() {
        return malePercentile;
    }

    public void setMalePercentile(int malePercentile) {
        this.malePercentile = malePercentile;
    }

    public int[] getPersonaSplit() {
        return personaSplit;
    }

    public void setPersonaSplit(int[] personaSplit) {
        this.personaSplit = personaSplit;
    }

    public int getPersonaTypeNum() {
        return personaTypeNum;
    }

    public void setPersonaTypeNum(int personaTypeNum) {
        this.personaTypeNum = personaTypeNum;
        this.personaSplit = new int[personaTypeNum];
    }

    public List<PersonaProfile> getPersonaProfiles() {
        return personaProfiles;
    }


    public void setPersonaTypeSize(int index, int size){
        this.personaSplit[index] = size;
    }

    public int getPersonaTypeSize(int index){
        return this.personaSplit[index];
    }

    public PersonaProfile getPersonaProfile(int index){
        return personaProfiles.get(index);
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public void setPersonaProfiles(List<PersonaProfile> personaProfiles) {
        this.personaProfiles = personaProfiles;
    }

    public ProbabilityDistribution getBioClockDistribution() {
        return bioClockDistribution;
    }

    public void setBioClockDistribution(ProbabilityDistribution bioClockDistribution) {
        this.bioClockDistribution = bioClockDistribution;
    }

    public List<Person> getPeopleRecords() {
        return peopleRecords;
    }

    @Override
    public String toString() {
        return "SimulatedCommunity{" +
                "size=" + size +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", ageDistribution=" + ageDistribution +
                ", bioClockDistribution=" + bioClockDistribution +
                ", malePercentile=" + malePercentile +
                ", personaSplit=" + Arrays.toString(personaSplit) +
                ", personaTypeNum=" + personaTypeNum +
                ", variance=" + variance +
                ", personaProfiles=" + personaProfiles +
                '}';
    }


    /* dump all people simulation events into a csv file */
    public void createSimEventsCSV(SimOutputManager outputer) {

        for (int i = 0; i < size; i++) {
            Person person = peopleRecords.get(i);
            for (int j = 0; j < person.getSimEvents().size(); j++) {
                SimEvent event = person.getSimEvents().get(j);

                outputer.writeToFile(event.toCSVLine());
            }
        }
    }

    private int sumPersonaSplit(){
        int sum = 0;

        for(int i = 0; i < this.personaSplit.length ; i++){
            sum += this.personaSplit[i];
        }

        return sum;
    }


}