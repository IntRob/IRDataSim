
import java.io.*;

/**
 * Handle simulation output into files
 * Created by admin on 11/26/15.
 */
public class SimOutputer {

    private String outFileName;
    private String outputDir;

    private Writer writer;

    public SimOutputer(String outFileName, String outputDir) {
        this.outFileName = outFileName;
        this.outputDir = outputDir;
    }

    public SimOutputer() {
    }

    public void prepFile(){

        String fullFilleName = outputDir + "/" + outFileName;

        try {
            File statText = new File(fullFilleName);
            FileOutputStream is = new FileOutputStream(statText);
            OutputStreamWriter osw = new OutputStreamWriter(is);
            writer = new BufferedWriter(osw);

        } catch (IOException e) {
            System.out.println("Problem openning output the file " + fullFilleName);
            System.out.println("Check that directory exists");

        }
    }

    public void closeOutputer(){

        try {
            writer.close();
            System.out.println("File closed");
        } catch (IOException e) {
            System.out.println("Problem closing the file");
        }
    }

    public void writeToFile(String str) {

        try {
            writer.write(str);
            //System.out.println("Writing to file" + str);

        } catch (IOException e) {
            System.out.println("Problem writing to the file ");
        }
    }

    public String getOutFileName() {
        return outFileName;
    }

    public void setOutFileName(String outFileName) {
        this.outFileName = outFileName;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }


}
