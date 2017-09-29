package de.bioforscher.plip.evaluator;

import com.sun.javafx.application.LauncherImpl;
import de.bioforscher.plip.evaluator.InsertionInterface.InsertionInterface;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Date;


/**
 * central evaluator
 * --> currently only test functions
 */

public class PLIPEvaluator {
    public static void main(String[] args) throws ParseException {

        Options options = new Options();

        options.addOption(
                OptionBuilder.withArgName("pdbid")
                        .hasArg()
                        .withDescription("PDBID which should be processed")
                        .create("pdbid"));

        options.addOption(
                OptionBuilder
                .withDescription("display this message and exit")
                .create("help"));

        options.addOption("literature", false,"Literature insertion mode with graphical user interface");

        options.addOption("withDB", false,"export the processed proteins to an database; included in 'literature'");

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        Date d = null;
        try {
            d = new Date(PLIPEvaluator.class.getResource("PLIPEvaluator.class").openConnection().getLastModified());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PLIP Evaluator from " + d);


        if(cmd.hasOption("help")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("plip-evaluator -pdbid <pdbid> [...]","", options,"");
        }

        if (cmd.hasOption("literature")){
            System.out.println("Switching to literature insertion mode");
            LauncherImpl.launchApplication(InsertionInterface.class,args);
            System.exit(0);
        }

        int withDB = 0;
        if (cmd.hasOption("withDB")){
            withDB = 1;
        }

        if (cmd.hasOption("pdbid")){
            String pdbid = cmd.getOptionValue("pdbid");
            process(pdbid, withDB);
        } else {
            System.err.println("Please give pdbid! See usage!");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("plip-evaluator -pdbid <pdbid> [...]","", options,"");
        }


        //test();



    }



    private static void process(String pdbid, int withDB){

        if(withDB == 1){
            HibernateHandler handler = new HibernateHandler();
            handler.openSession();

            boolean containsProtein = handler.containsProtein(pdbid);
            if(containsProtein == true){
                System.out.println("Protein exists -- Fetching Protein");
                Protein protein = handler.fetchProtein(pdbid);
            } else if (containsProtein == false){
                System.out.println("Protein doesn't exist in Database -- SecStruct is being calculated.....");
                Protein protein = calculateProtein(pdbid);
                System.out.println("Storing Protein.");
                handler.storeProtein(protein);
            }

            handler.closeSession();
        } else {
            System.out.println("Ignoring Database -- Calculating SecStruct.....");
            Protein protein = calculateProtein(pdbid);
        }

    }

    public static Protein calculateProtein(String pdbid){
        EvaluatorModule moduleDSSP = new DSSP();
        EvaluatorModule modulePLIP = new PLIP();

        PredictedContainer predictedContainer = new PredictedContainer();

        predictedContainer.setInteractionContainersDSSP(moduleDSSP.processPDBid(pdbid));
        predictedContainer.setInteractionContainersPLIP(modulePLIP.processPDBid(pdbid));

        Protein protein = new Protein(null, pdbid, "all", predictedContainer);

        return protein;
    }

    public static void test(){



    }
}
