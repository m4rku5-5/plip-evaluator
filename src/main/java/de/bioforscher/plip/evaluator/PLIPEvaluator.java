package de.bioforscher.plip.evaluator;

import com.sun.javafx.application.LauncherImpl;
import de.bioforscher.plip.evaluator.InsertionInterface.InsertionInterface;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Date;


/**
 * Evaluator Main Class: Implements CLI and accesses all other functionality
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

        options.addOption(
                OptionBuilder.withArgName("type")
                        .hasArg()
                        .withDescription("Analyze Protein with specified type; Valid <types> are: \n \t adj - make adjacency table for Protein interactions from PLIP and DSSP" +
                                "                                                                 \n \t hbm - find exact HBond matches between PLIP and DSSP")
                        .create("analyze"));

        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        Date d = null;
        try {
            d = new Date(PLIPEvaluator.class.getResource("PLIPEvaluator.class").openConnection().getLastModified());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PLIP Evaluator from " + d);


        if(cmd.hasOption("help") && !cmd.hasOption("pdbid")){
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

        Protein protein = new Protein();
        if (cmd.hasOption("pdbid")){
            String pdbid = cmd.getOptionValue("pdbid");
            protein = process(pdbid, withDB);
        } else if (!cmd.hasOption("help")){
            System.err.println("Please give pdbid! See usage!");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("plip-evaluator -pdbid <pdbid> [...]","", options,"");
        }

        if (cmd.hasOption("analyze")){
            String type = cmd.getOptionValue("analyze");
            analyze(protein, type);
        }
    }


    private static Protein process(String pdbid, int withDB){

        Protein protein = new Protein();

        if(withDB == 1){
            HibernateHandler handler = new HibernateHandler();
            handler.openSession();

            boolean containsProtein = handler.containsProtein(pdbid);
            if(containsProtein == true){
                System.out.println("Protein exists -- Fetching Protein");
                protein = handler.fetchProtein(pdbid);
            } else if (containsProtein == false){
                System.out.println("Protein doesn't exist in Database -- SecStruct is being calculated.....");
                protein = calculateProtein(pdbid);
                System.out.println("Storing Protein.");
                handler.storeProtein(protein);
            }

            handler.closeSession();
        } else {
            System.out.println("Ignoring Database -- Calculating SecStruct.....");
            protein = calculateProtein(pdbid);
        }

        return protein;
    }

    private static Protein calculateProtein(String pdbid){
        EvaluatorModule moduleDSSP = new DSSP();
        EvaluatorModule modulePLIP = new PLIP();

        PredictedContainer predictedContainer = new PredictedContainer();

        predictedContainer.setInteractionContainersDSSP(moduleDSSP.processPDBid(pdbid));
        predictedContainer.setInteractionContainersPLIP(modulePLIP.processPDBid(pdbid));

        Protein protein = new Protein(null, pdbid, "all", predictedContainer);

        return protein;
    }

    private static void analyze(Protein protein, String type){
        Analyzer analyzer = new Analyzer();
        if (type == "adj"){
            System.out.println("Adjacency List for PLIP: ");
            analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersPLIP());
            System.out.println("Adjacency List for DSSP: ");
            analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersDSSP());
        } else if (type == "hbm"){
            System.out.println("Exact HBond matches between PLIP and DSSP: ");
            analyzer.findExactHBondMatches(protein.getPredictedContainer());
        }
    }

}
