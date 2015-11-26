/**
 * Created by admin on 11/12/15.
 */



import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.io.FileReader;
import java.util.ListIterator;

import definitions.AgeDistribution;
import definitions.HomeEventType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;


public class DataGen {

    // input files directory
    static private String inputDirectory;

    // output files directory
    static private String outputDirectory;

    // each simulation generates new files names by using the timestamp as a prefix
    static private String outputFilesPrefix;


    // Sim input file name
    static private String simConfigFileName = "simconfig.json";
    static private String kYouConfigFileName = "kYoutypes.json";
    static private String simDailyTemplatesFileName = "simDailyTemplates.json";

    // Simulation input loader
    static private SimLoader simLoader = new SimLoader();


    public static void main(String [ ] args)
    {


        String simInputFileName;

        boolean validParams = setParams(args);
        if(validParams == false)
            return;

        simLoader = new SimLoader();

        simInputFileName = new String(inputDirectory + "/" + simDailyTemplatesFileName);
        System.out.println("loading daily templates input file: " + simConfigFileName);
        simLoader.readDailyTemplatesJson(simInputFileName);


        simInputFileName = new String(inputDirectory + "/" + simConfigFileName);
        System.out.println("loading simulation input file: " + simInputFileName);
        simLoader.readSimInputsJson(simInputFileName);


        // generate synthetic people daily activities
        //SimulatedCommunity community = simLoader.getCommunity();
        //community.generatePopulation(simLoader);


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

        outputFilesPrefix = date.toString();
        System.out.println("DataGen: output file prefix is " + outputFilesPrefix);

        return true;


    }




}
