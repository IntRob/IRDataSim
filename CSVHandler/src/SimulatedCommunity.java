/**
 * Created by admin on 11/16/15.
 *
 * A community represents the total number of people in the simulation and the population characteristics
 */

import definitions.AgeDistribution;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class SimulatedCommunity {

    // number of people in the simulated community
    private int size;

    // elderly min age
    private int minAge;

    // elderly max age
    private int maxAge;

    // age distribution type
    private AgeDistribution ageDistribution;

   // percentile of males in community
    private int malePercentile;

    // split of persona types across population (how many from type 0/1/2...
    private int [] personaSplit;

    // number of persona types in community
    private int personaTypeNum;

    // hold all persona's
    private List<PersonaProfile> personaProfiles;


    public SimulatedCommunity(int size, int minAge, int maxAge, AgeDistribution ageDistribution, int malePercentile, int[] personaSplit) {
        this.size = size;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.ageDistribution = ageDistribution;
        this.malePercentile = malePercentile;
        this.personaSplit = personaSplit;
        this.personaProfiles = new ArrayList<PersonaProfile>();
    }

    // default construbtor
    public SimulatedCommunity() {
        this.size = 100;
        this.minAge = 70;
        this.maxAge = 85;
        this.ageDistribution = AgeDistribution.RANDOM;
        this.malePercentile = 50;
        this.personaProfiles = new ArrayList<PersonaProfile>();
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

    public AgeDistribution getAgeDistribution() {
        return ageDistribution;
    }

    public void setAgeDistribution(AgeDistribution ageDistribution) {
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

    @Override
    public String toString() {
        return "SimulatedCommunity{" +
                "size=" + size +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", ageDistribution=" + ageDistribution +
                ", malePercentile=" + malePercentile +
                ", personaSplit=" + Arrays.toString(personaSplit) +
                '}';
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



}