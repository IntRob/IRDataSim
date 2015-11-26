/**
 * Created by admin on 11/16/15.
 *
 * SimParams holds the basic parameters for the simulation such as how many events to generate, etc.
 *
 */
public class SimConfig {

    // number of daily templates to load
    private int numOfDailyTemplates = 0;

    // number of events to generate
    private int numOfDays = 0;

    public SimConfig() {

    }

    public SimConfig(int numOfDailyTemplates, int numOfDays) {
        this.numOfDailyTemplates = numOfDailyTemplates;
        this.numOfDays = numOfDays;
    }

    public int getNumOfDailyTemplates() {
        return numOfDailyTemplates;
    }

    public void setNumOfDailyTemplates(int numOfDailyTemplates) {
        this.numOfDailyTemplates = numOfDailyTemplates;
    }

    public int getNumOfEvents() {
        return numOfDays;
    }

    public void setNumOfEvents(int numOfEvents) {
        this.numOfDays = numOfEvents;
    }

    @Override
    public String toString() {
        return "SimConfig{" +
                "numOfDailyTemplates=" + numOfDailyTemplates +
                ", numOfDays=" + numOfDays +
                '}';
    }

}
