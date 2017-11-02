package de.bioforscher.plip.evaluator;

import com.sun.javafx.application.LauncherImpl;
import de.bioforscher.plip.evaluator.InsertionInterface.InsertionInterface;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Date;


/**
 * Evaluator Main Class: Implements CLI and accesses all other functionality
 * PLIP Evaluator by Markus Schwalbe, 2017
 */

public class PLIPEvaluator {
    public static void main(String[] args) throws ParseException {

        //set Options for CLI
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

        options.addOption("json",false,"Exports all Proteins of the database as JSON file");

        //make CLI
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = parser.parse(options, args);

        //get Date vor current version output
        Date d = null;
        try {
            d = new Date(PLIPEvaluator.class.getResource("PLIPEvaluator.class").openConnection().getLastModified());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("PLIP Evaluator from " + d);

        //handle input options
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

        if (cmd.hasOption("json")){
            System.out.println("Exporting Database");
            new JsonExporter().exportDBAsJson();
        }
    }

    //process given pdbid with or without DB
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

    //calculate the SecStuct
    private static Protein calculateProtein(String pdbid){
        EvaluatorModule moduleDSSP = new DSSP();
        EvaluatorModule modulePLIP = new PLIP();

        PredictedContainer predictedContainer = new PredictedContainer();

        predictedContainer.setInteractionContainersDSSP(moduleDSSP.processPDBid(pdbid));
        predictedContainer.setInteractionContainersPLIP(modulePLIP.processPDBid(pdbid));

        Protein protein = new Protein(null, pdbid, "all", predictedContainer);

        return protein;
    }

    //analyze Protein with given parameter --> print Adjacency List; find exact matches for HBonds; print all Interactions
    private static void analyze(Protein protein, String type){
        System.out.println("Analyzing protein....");
        Analyzer analyzer = new Analyzer();

        System.out.println("Protein Id: " + protein.getPDBid());
        if (protein.getDoi() != null){
            System.out.println("DOI: " + protein.getDoi());
        } else {
            System.out.println("DOI: none");
        }

        switch (type){
            case "adj": System.out.println("\n Adjacency List for PLIP: ");
                analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersPLIP());
                System.out.println("\n Adjacency List for DSSP: ");
                analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersDSSP());
                break;

            case "hbm": System.out.println("\n Exact HBond matches between PLIP and DSSP: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < analyzer.findExactHBondMatches(protein.getPredictedContainer()).size(); i++) {
                    System.out.println(analyzer.findExactHBondMatches(protein.getPredictedContainer()).get(i));
                }
                break;

            case "allint":  System.out.println("\n Printing all interactions:");
                System.out.println("DSSP: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().size(); i++) {
                    System.out.println(protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().get(i));
                }
                System.out.println("\nPLIP: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().size(); i++) {
                    System.out.println(protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().get(i));
                }
                System.out.println("\nLiterature: ");
                System.out.println("Res Acc Don");
                if (protein.predictedContainer.getInteractionContainersLiterature().gethBondInteractions() != null){
                    for (int i = 0; i < protein.predictedContainer.getInteractionContainersLiterature().gethBondInteractions().size(); i++) {
                        System.out.println(protein.predictedContainer.getInteractionContainersLiterature().gethBondInteractions().get(i));
                    }
                } else {
                    System.out.println("No interactions present.");
                }

                break;

            default:
                System.err.println("Wrong analyzing parameter!");
                break;
        }
    }

}
