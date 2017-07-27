package de.bioforscher.plip.evaluator;

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

        options.addOption("expDB", false,"export the processed proteins to an database; included in 'literature'");

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
            //TODO binding to literature mode
        }

        int withExport = 0;
        if (cmd.hasOption("expDB")){
            withExport = 1;
        }

        if (cmd.hasOption("pdbid")){
            String pdbid = cmd.getOptionValue("pdbid");
            process(pdbid, withExport);
        }


        //test();



    }



    private static void process(String pdbid, int withExport){
        //TODO
    }



    public static void test(){

        EvaluatorModule module = new DSSP();
        module.processPDBid("1aki");
    }
}
