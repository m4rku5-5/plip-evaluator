package de.bioforscher.plip.evaluator;

import com.sun.javafx.application.LauncherImpl;
import de.bioforscher.plip.evaluator.InsertionInterface.InsertionInterface;
import org.apache.commons.cli.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
                                "                                                                 \n \t hbm - find exact HBond matches between PLIP and DSSP" +
                                "                                                                 \n \t plot - plots the interactions and writes it to a file")
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
        EvaluatorModule moduleHBPLUS = new HBPLUS();

        PredictedContainer predictedContainer = new PredictedContainer();

        predictedContainer.setInteractionContainersDSSP(moduleDSSP.processPDBid(pdbid));
        predictedContainer.setInteractionContainersPLIP(modulePLIP.processPDBid(pdbid));
        predictedContainer.setInteractionContainersHBPLUS(moduleHBPLUS.processPDBid(pdbid));

        Protein protein = new Protein(null, pdbid, "all", predictedContainer);

        return protein;
    }

    //analyze Protein with given parameter --> print Adjacency List; find exact matches for HBonds; print all Interactions
    private static void analyze(Protein protein, String type){
        System.out.println("Analyzing protein....");
        Analyzer analyzer = new Analyzer();

        System.out.println("Protein ID: " + protein.getPDBid());
        if (protein.getDoi() != null){
            System.out.println("DOI: " + protein.getDoi());
        } else {
            System.out.println("DOI: none");
        }

        switch (type){
            case "": break;

            case "adj": System.out.println("\nAdjacency List for PLIP: ");
                analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersPLIP());
                System.out.println("\nAdjacency List for DSSP: ");
                analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersDSSP());
                System.out.println("\nAdjacency List for HBPLUS: ");
                analyzer.makeAdjacencyList(protein.getPredictedContainer().getInteractionContainersHBPLUS());
                break;

            case "hbm": System.out.println("\nExact HBond matches between PLIP and DSSP: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < analyzer.findExactHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).gethBondInteractions().size(); i++) {
                    System.out.println(analyzer.findExactHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).gethBondInteractions().get(i));
                }

                System.out.println("\nExact HBond matches between PLIP and HBPLUS: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < analyzer.findExactHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersHBPLUS().gethBondInteractions()).gethBondInteractions().size(); i++) {
                    System.out.println(analyzer.findExactHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersHBPLUS().gethBondInteractions()).gethBondInteractions().get(i));
                }
                break;

            case "rhbm": System.out.println("\nHBond matches in range of 2 AA between PLIP and DSSP: ");
                System.out.println("Res Acc Don Distance");
                for (int i = 0; i < analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).getKey().gethBondInteractions().size(); i++) {
                    System.out.println(analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).getKey().gethBondInteractions().get(i) + " " +
                                       analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).getValue().get(i));
                }

                System.out.println("\nHBond matches in range of 2 AA between PLIP and HBPLUS: ");
                System.out.println("Res Acc Don Distance");
                for (int i = 0; i < analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersHBPLUS().gethBondInteractions()).getKey().gethBondInteractions().size(); i++) {
                    System.out.println(analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersHBPLUS().gethBondInteractions()).getKey().gethBondInteractions().get(i) + " " +
                            analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersHBPLUS().gethBondInteractions()).getValue().get(i));
                }
                break;

            case "allint":  System.out.println("\nPrinting all interactions:");
                System.out.println("DSSP: ");
                System.out.println("Res Acc Don Feat");
                for (int i = 0; i < protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().size(); i++) {
                    System.out.println(protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().get(i));
                }
                System.out.println("\nPLIP: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().size(); i++) {
                    System.out.println(protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().get(i));
                }
                System.out.println("\nHBPLUS: ");
                System.out.println("Res Acc Don");
                for (int i = 0; i < protein.predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions().size(); i++) {
                    System.out.println(protein.predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions().get(i));
                }
                System.out.println("\nLiterature: ");
                System.out.println("Res Acc Don");
                if (protein.predictedContainer.getInteractionContainersLiterature() != null){
                    for (int i = 0; i < protein.predictedContainer.getInteractionContainersLiterature().gethBondInteractions().size(); i++) {
                        System.out.println(protein.predictedContainer.getInteractionContainersLiterature().gethBondInteractions().get(i));
                    }
                } else {
                    System.out.println("No interactions present.");
                }

                break;

            case "plot.all":

                XYSeriesCollection xyData = new XYSeriesCollection();
                XYSeries DSSPSeries = new XYSeries("DSSP");
                XYSeries PLIPSeries = new XYSeries("PLIP");
                XYSeries RHBMSeries = new XYSeries("RHBM");
                XYSeries HBMSeries = new XYSeries("HBM");


                for (int i = 0; i < protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().size(); i++) {
                    if (protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().get(i).getAccept() != 0 &&  protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().get(i).getDonor() != 0)
                    DSSPSeries.add(protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().get(i).getAccept(),
                                     protein.predictedContainer.getInteractionContainersDSSP().gethBondInteractions().get(i).getDonor());
                }

                for (int i = 0; i < protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().size(); i++) {
                    PLIPSeries.add(protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().get(i).getAccept(),
                            protein.predictedContainer.getInteractionContainersPLIP().gethBondInteractions().get(i).getDonor());
                }

                System.out.println("Finding exact HBond matches.....");
                List<HBondInteraction> EHBM = analyzer.findExactHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).gethBondInteractions();
                for (int i = 0; i < EHBM.size(); i++) {
                    HBMSeries.add(EHBM.get(i).getAccept(),
                            EHBM.get(i).getDonor());
                }

                System.out.println("Finding range HBond matches.....");
                List<HBondInteraction> RHBM = analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).getKey().gethBondInteractions();
                for (int i = 0; i < RHBM.size(); i++) {
                    RHBMSeries.add(RHBM.get(i).getAccept(),
                            RHBM.get(i).getDonor());
                }

                System.out.println("Plotting.....");
                xyData.addSeries(HBMSeries);
                xyData.addSeries(RHBMSeries);
                xyData.addSeries(DSSPSeries);
                xyData.addSeries(PLIPSeries);


                JFreeChart chart = ChartFactory.createScatterPlot(
                        "Interactions", // chart title
                        "Acceptor", // x axis label
                        "Donor", // y axis label
                        xyData, // data
                        PlotOrientation.VERTICAL,
                        true, // include legend
                        true, // tooltips
                        false // urls
                );

                Shape shape  = new Ellipse2D.Double(0,0,5,5);
                chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(0,0,0));
                chart.getXYPlot().getRenderer().setSeriesShape(0, shape);
                chart.getXYPlot().getRenderer().setSeriesPaint(1, new Color(0,255,0));
                chart.getXYPlot().getRenderer().setSeriesShape(1, shape);
                chart.getXYPlot().getRenderer().setSeriesPaint(2, new Color(255,0,0));
                chart.getXYPlot().getRenderer().setSeriesShape(2, shape);
                chart.getXYPlot().getRenderer().setSeriesPaint(3, new Color(0,0,255));
                chart.getXYPlot().getRenderer().setSeriesShape(3, shape);

                //chart.getXYPlot().getRangeAxis().setAutoRange(true);
                //chart.getXYPlot().getDomainAxis().setAutoRange(true);

                //  create and display a frame...
                //ChartFrame frame = new ChartFrame("Interactions", chart);
                //frame.pack();
                //frame.setVisible(true);

                OutputStream out = null;
                try {
                    out = new FileOutputStream("interactions_" + protein.getPDBid() + ".png");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    ChartUtilities.writeChartAsPNG(out,
                            chart,
                            1000,
                            1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case "plot.annotated":

                EHBM = analyzer.findExactHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).gethBondInteractions();
                RHBM = analyzer.findRangeHBondMatches(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions()).getKey().gethBondInteractions();

                Map<Integer, String> dsspFeatures = new DSSP().getDSSPFeatureList(protein.getPDBid());

                plotAnnotated(analyzer.annotateDSSPFeatures(protein.getPredictedContainer().getInteractionContainersDSSP().gethBondInteractions(), dsspFeatures), protein.getPDBid(), "DSSP");
                plotAnnotated(analyzer.annotateDSSPFeatures(protein.getPredictedContainer().getInteractionContainersPLIP().gethBondInteractions(), dsspFeatures), protein.getPDBid(), "PLIP");
                plotAnnotated(analyzer.annotateDSSPFeatures(EHBM, dsspFeatures), protein.getPDBid(), "EHBM");
                plotAnnotated(analyzer.annotateDSSPFeatures(RHBM, dsspFeatures), protein.getPDBid(), "RHBM");

                break;

            case "stats":

                System.out.println(analyzer.makeStatistics(protein.getPredictedContainer()));
                JsonExporter jsonExporter = new JsonExporter();
                jsonExporter.exportStatsAsJson(analyzer.makeStatistics(protein.getPredictedContainer()), protein.getPDBid());

                break;
            default:
                System.err.println("Wrong analyzing parameter!");
                break;
        }
    }

    private static void plotAnnotated(List<HBondInteractionDSSPAnnotated> hBondInteractions, String pdbId, String type) {

        XYSeriesCollection xyData = new XYSeriesCollection();
        XYSeries helix = new XYSeries("Helix");
        XYSeries strand = new XYSeries("Strand");
        XYSeries coil = new XYSeries("Coil");

        for (HBondInteractionDSSPAnnotated hbond : hBondInteractions) {
            if (hbond.getFeature() == "E"){
                strand.add(hbond.getAccept(), hbond.getDonor());
            } else if (hbond.getFeature() == "H"){
                helix.add(hbond.getAccept(), hbond.getDonor());
            } else {
                coil.add(hbond.getAccept(), hbond.getDonor());
            }
        }

        xyData.addSeries(helix);
        xyData.addSeries(strand);
        xyData.addSeries(coil);

        //xyData.addSeries(HBMSeries);
        JFreeChart chart = ChartFactory.createScatterPlot(
                 type + "Interactions DSSP annotated", // chart title
                "Acceptor", // x axis label
                "Donor", // y axis label
                xyData, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips
                false // urls
        );

        Shape shape  = new Ellipse2D.Double(0,0,5,5);
        chart.getXYPlot().getRenderer().setSeriesPaint(0, new Color(255,0,0));
        chart.getXYPlot().getRenderer().setSeriesShape(0, shape);
        chart.getXYPlot().getRenderer().setSeriesPaint(1, new Color(0,255,0));
        chart.getXYPlot().getRenderer().setSeriesShape(1, shape);
        chart.getXYPlot().getRenderer().setSeriesPaint(2, new Color(0,0,255));
        chart.getXYPlot().getRenderer().setSeriesShape(2, shape);

        OutputStream out = null;
        try {
            out = new FileOutputStream("interactions_" + pdbId + "_" + type +".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            ChartUtilities.writeChartAsPNG(out,
                    chart,
                    1000,
                    1000);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
