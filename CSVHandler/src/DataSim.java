/**
 * Created by admin on 11/12/15.
 */



import java.util.Date;
import java.util.ArrayList;


public class DataSim {

    // input files directory
    static private String inputDirectory;

    // output files directory
    static private String outputDirectory;

    // output file name
    static private String outputFileName;
    // ML output file name
    static private String outputMLFileName;


    // each simulation generates new files names by using the timestamp as a prefix
    static private String outputFilesPrefix;


    // Sim input file name
    static private String simConfigFileName = "simconfig.json";
    static private String kYouConfigFileName = "kYoutypes.json";
    static private String simDailyTemplatesFileName = "simDailyTemplates.json";

    // Simulation input loader
    static private SimLoader simLoader = new SimLoader();

    // Simulation output handler
    static private SimOutputManager simOutputer = new SimOutputManager();

    // MLs holder
    static private MLHolder mlHolder;


    public static void main(String [ ] args)
    {


        String simInputFileName;

        boolean validParams = setParams(args);
        if(validParams == false)
            return;

        simLoader = new SimLoader();

        simInputFileName = new String(inputDirectory + "/" + simDailyTemplatesFileName);
        System.out.println("DATAGEN : loading daily templates input file: " + simConfigFileName);
        simLoader.readDailyTemplatesJson(simInputFileName);


        simInputFileName = new String(inputDirectory + "/" + simConfigFileName);
        System.out.println("DATAGEN : loading simulation input file: " + simInputFileName);
        simLoader.readSimInputsJson(simInputFileName);

        SimulatedCommunity community = SimLoader.getSimulatedCommunity();

        // Generate people in community
        community.generatePopulation(SimLoader.getSimConfig().getTimeIntervalBetweenEvents(), SimLoader.getkProfile());

        // generate all the events of the community
        System.out.println("DATAGEN : Generating events");
        community.generateEvents(SimLoader.getSimConfig().getNumOfSimDays());

        // prepare for output
        simOutputer.setOutFileName(outputFileName);
        simOutputer.setOutputDir(outputDirectory);
        simOutputer.prepFile();

        // dump events to output CSV file
        community.createSimEventsCSV(simOutputer);

        // close output
        simOutputer.closeOutputer();

        // create arraylist from community
        ArrayList <SimEvent> events = new ArrayList<SimEvent>();
        community.createSimEventsArrayList(events); // update the list
        // send community to ML and generate ML per activity
        mlHolder = new MLHolder(events);
        mlHolder.init();

        // prepare for ML output
        simOutputer.setOutFileName(outputMLFileName);
        simOutputer.setOutputDir(outputDirectory);
        simOutputer.prepFile();

        // dump ML events to output CSV file
        mlHolder.dumpToCSV(simOutputer);

        // close output
        simOutputer.closeOutputer();

    }

    /* set directories params */
    static private boolean setParams(String args[]){

        // generate the current time stamp for file prefix
        Date date = new Date();

        if(args.length != 2) {
            System.out.println("Help: param1 = input-dir, param2 = output-dir");
            System.out.println("Help: Input file names: " + simConfigFileName + ", " + kYouConfigFileName);

            return false;
        }

        inputDirectory = args[0];
        outputDirectory = args[1];

        outputFilesPrefix = new String(date.getDay() + "-" + date.getHours() + "-" + date.getMinutes() + "IRSimOut.csv");
        System.out.println("DataSim: output file prefix is " + date.getDay() + date.getHours() + date.getMinutes());

        outputFileName = outputFilesPrefix;

        outputFilesPrefix = new String(date.getDay() + "-" + date.getHours() + "-" + date.getMinutes() + "IRMLSimOut.csv");
        System.out.println("DataMLSim: output file prefix is " + date.getDay() + date.getHours() + date.getMinutes());

        outputMLFileName = outputFilesPrefix;

        System.out.println("DataMLSim: output file name is " + outputMLFileName);

        return true;


    }




}
