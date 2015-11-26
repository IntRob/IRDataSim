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
    private int numOfEvents = 0;

    public SimConfig() {

    }

    public SimConfig(int numOfDailyTemplates, int numOfEvents) {
        this.numOfDailyTemplates = numOfDailyTemplates;
        this.numOfEvents = numOfEvents;
    }

    public int getNumOfDailyTemplates() {
        return numOfDailyTemplates;
    }

    public void setNumOfDailyTemplates(int numOfDailyTemplates) {
        this.numOfDailyTemplates = numOfDailyTemplates;
    }

    public int getNumOfEvents() {
        return numOfEvents;
    }

    public void setNumOfEvents(int numOfEvents) {
        this.numOfEvents = numOfEvents;
    }

    @Override
    public String toString() {
        return "SimConfig{" +
                "numOfDailyTemplates=" + numOfDailyTemplates +
                ", numOfEvents=" + numOfEvents +
                '}';
    }

}
