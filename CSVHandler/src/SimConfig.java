/**
 * Created by admin on 11/16/15.
 *
 * SimParams holds the basic parameters for the simulation such as how many events to generate, etc.
 *
 */
public class SimConfig {

    // number of simulation days to generate
    private int numOfSimDays = 0;

    // hardcoded for now
    private int timeIntervalBetweenEvents = 5;

    public SimConfig() {

    }

    public SimConfig(int numOfDays) {
        this.numOfSimDays = numOfDays;
    }

    public int getNumOfSimDays() {
        return numOfSimDays;
    }

    public void setNumOfSimDays(int numOfSimDays) {
        this.numOfSimDays = numOfSimDays;
    }

    public int getTimeIntervalBetweenEvents() {
        return timeIntervalBetweenEvents;
    }

    @Override
    public String toString() {
        return "SimConfig{" +
                ", numOfDays=" + numOfSimDays +
                '}';
    }

}
