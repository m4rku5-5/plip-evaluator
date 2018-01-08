package de.bioforscher.plip.evaluator;

import javafx.util.Pair;
import org.apache.commons.math3.util.Precision;

import java.util.*;



public class Analyzer {


    /**
     * Code taken and modified from http://www.sanfoundry.com/java-program-implement-adjacency-list/
     *
     * for better handling as nested class
     * */
    class AdjacencyList {

        private Map<Integer, LinkedHashSet<Integer>> Adjacency_List;

        //standard constructor
        public AdjacencyList(int number_of_vertices){

            Adjacency_List = new HashMap<>();

            for (int i = 1; i <= number_of_vertices; i++){

                Adjacency_List.put(i, new LinkedHashSet<Integer>());

            }
        }

        //set connection
        public void setEdge(int source, int destination) {

            if (source > Adjacency_List.size() || destination > Adjacency_List.size() || destination == 0 || source == 0) {

                //System.out.println("set the vertex entered in not present ");

                return;
            }

            LinkedHashSet<Integer> slist = Adjacency_List.get(source);

            slist.add(destination);

            LinkedHashSet<Integer> dlist = Adjacency_List.get(destination);

            dlist.add(source);

        }

        //get connection
        public LinkedHashSet<Integer> getEdge(int source) {

            if (source > Adjacency_List.size()) {

                //System.out.println("the vertex entered is not present");

                return null;
            }

            return Adjacency_List.get(source);
        }

    }

    class Statistics {
        int numberOfPLIPInt;
        int numberOfDSSPInt;
        int numberOfLitInt;
        int numberOfHBM;
        float percentOfHBM;
        int numberOfRHBM;
        float percentOfRHBM;
        int totalScore;
        float normTotalScore;

        public Statistics(int numberOfPLIPInt, int numberOfDSSPInt, int numberOfLitInt, int numberOfHBM, float percentOfHBM, int numberOfRHBM, float percentOfRHBM, int totalScore, float normTotalScore) {
            this.numberOfPLIPInt = numberOfPLIPInt;
            this.numberOfDSSPInt = numberOfDSSPInt;
            this.numberOfLitInt = numberOfLitInt;
            this.numberOfHBM = numberOfHBM;
            this.percentOfHBM = percentOfHBM;
            this.numberOfRHBM = numberOfRHBM;
            this.percentOfRHBM = percentOfRHBM;
            this.totalScore = totalScore;
            this.normTotalScore = normTotalScore;
        }

        public Statistics() {
        }

        public int getNumberOfPLIPInt() {
            return numberOfPLIPInt;
        }

        public void setNumberOfPLIPInt(int numberOfPLIPInt) {
            this.numberOfPLIPInt = numberOfPLIPInt;
        }

        public int getNumberOfDSSPInt() {
            return numberOfDSSPInt;
        }

        public void setNumberOfDSSPInt(int numberOfDSSPInt) {
            this.numberOfDSSPInt = numberOfDSSPInt;
        }

        public int getNumberOfLitInt() {
            return numberOfLitInt;
        }

        public void setNumberOfLitInt(int numberOfLitInt) {
            this.numberOfLitInt = numberOfLitInt;
        }

        public int getNumberOfHBM() {
            return numberOfHBM;
        }

        public void setNumberOfHBM(int numberOfHBM) {
            this.numberOfHBM = numberOfHBM;
        }

        public float getPercentOfHBM() {
            return percentOfHBM;
        }

        public void setPercentOfHBM(int percentOfHBM) {
            this.percentOfHBM = percentOfHBM;
        }

        public int getNumberOfRHBM() {
            return numberOfRHBM;
        }

        public void setNumberOfRHBM(int numberOfRHBM) {
            this.numberOfRHBM = numberOfRHBM;
        }

        public float getPercentOfRHBM() {
            return percentOfRHBM;
        }

        public void setPercentOfRHBM(int percentOfRHBM) {
            this.percentOfRHBM = percentOfRHBM;
        }

        public int getTotalScore() {
            return totalScore;
        }

        public void setTotalScore(int totalScore) {
            this.totalScore = totalScore;
        }

        public float getNormTotalScore() {
            return normTotalScore;
        }

        public void setNormTotalScore(float normTotalScore) {
            this.normTotalScore = normTotalScore;
        }

        @Override
        public String toString() {

            String out = "Number of PLIP interactions: " + this.numberOfPLIPInt +
                         "\nNumber of DSSP interactions: " + this.numberOfDSSPInt +
                         "\nNumber of Literature interactions: " + this.numberOfLitInt +
                         "\nNumber of exact HBond matches: " + this.numberOfHBM +
                         "\nPercentage of exact HBond matches: " + this.percentOfHBM +
                         "\nNumber of range HBond matches: " + this.numberOfRHBM +
                         "\nPercentage of range HBond matches: " + this.percentOfRHBM +
                         "\nTotal Score: " + this.totalScore +
                         "\nNormalized Total Score: " + this.normTotalScore;
            return out;
        }
    }


    //make the List and print it
    public AdjacencyList makeAdjacencyList(InteractionContainer interactionContainer){
        List<HBondInteraction> hBondInteractions = interactionContainer.gethBondInteractions();

        List<Integer> tempList = new ArrayList<>();

        for (int i = 0; i < hBondInteractions.size(); i++) {
            tempList.add(hBondInteractions.get(i).getDonor());
            tempList.add(hBondInteractions.get(i).getAccept());
        }

        Collections.sort(tempList);
        int number_of_vertices = tempList.get(tempList.size() - 1);

        AdjacencyList adjacencyList = new AdjacencyList(number_of_vertices);

        for (int i = 1; i < hBondInteractions.size(); i++) {
            adjacencyList.setEdge(hBondInteractions.get(i).getDonor(), hBondInteractions.get(i).getAccept());
        }

        for (int i = 1; i <= number_of_vertices; i++) {
            System.out.print(i + ": ");
            System.out.println(adjacencyList.getEdge(i).toString());
        }

        return adjacencyList;

    }

    //finding exact matches for HBond interactions
    public InteractionContainer findExactHBondMatches(PredictedContainer predictedContainer){
        List<HBondInteraction> matches = new ArrayList<>();


        List<HBondInteraction> hBondInteractionDSSP = predictedContainer.getInteractionContainersDSSP().gethBondInteractions();
        List<HBondInteraction> hBondInteractionPLIP = predictedContainer.getInteractionContainersPLIP().gethBondInteractions();

        for (int i = 0; i < hBondInteractionPLIP.size(); i++) {
            for (int j = 0; j < hBondInteractionDSSP.size(); j++) {
                if (hBondInteractionPLIP.get(i).getAccept() == hBondInteractionDSSP.get(j).getAccept() && hBondInteractionPLIP.get(i).getDonor() == hBondInteractionDSSP.get(j).getDonor()){
                    matches.add(hBondInteractionPLIP.get(i));
                }
            }
        }

        InteractionContainer exactHBondInteractions = new InteractionContainer(matches);
        exactHBondInteractions.removeDuplicateHBonds();
        return exactHBondInteractions;
    }


    public Pair<InteractionContainer, List> findRangeHBondMatches(PredictedContainer predictedContainer){
        List<HBondInteraction> matches = new ArrayList<>();


        List<HBondInteraction> hBondInteractionDSSP = predictedContainer.getInteractionContainersDSSP().gethBondInteractions();
        List<HBondInteraction> hBondInteractionPLIP = predictedContainer.getInteractionContainersPLIP().gethBondInteractions();

        List<Double> distanceList = new ArrayList<>();

        double range = Precision.round(Math.sqrt(8), 2);
        for (int i = 0; i < hBondInteractionPLIP.size(); i++) {
            for (int j = 0; j < hBondInteractionDSSP.size(); j++) {
                double d = 0;
                d = Math.sqrt(Math.pow(hBondInteractionPLIP.get(i).getAccept()-hBondInteractionDSSP.get(j).getAccept(),2)+
                              Math.pow(hBondInteractionPLIP.get(i).getDonor()-hBondInteractionDSSP.get(j).getDonor(),2));
                d = Precision.round(d, 2);
                if (d < range && d != 0){
                    for (int k = 0; k < matches.size(); k++) {
                        if (!hBondInteractionPLIP.get(i).equals(matches.get(k))){
                            if(distanceList.get(k) > d){
                                matches.remove(k);
                                distanceList.remove(k);
                            }
                            matches.add(hBondInteractionPLIP.get(i));
                            distanceList.add(d);
                        }
                    }
                }
            }
        }

        InteractionContainer rangeHBondInteractions = new InteractionContainer(matches);


        Pair<InteractionContainer, List> containerDistancePair = new Pair<>(rangeHBondInteractions, distanceList);
        return containerDistancePair;
    }

    public List<HBondInteractionDSSPAnnotated> annotateDSSPFeatures(List<HBondInteraction> hBondInteractions, Map<Integer, String> featureAnnotations){

        List<HBondInteractionDSSPAnnotated> hBondInteractionDSSPAnnotated = new ArrayList<>();

        for (HBondInteraction hbond : hBondInteractions) {
            hBondInteractionDSSPAnnotated.add(new HBondInteractionDSSPAnnotated(hbond.getResidueNumber(), hbond.getDonor(), hbond.getAccept(), featureAnnotations.get(hbond.getResidueNumber())));
        }

        return hBondInteractionDSSPAnnotated;
    }

    public Statistics makeStatistics(PredictedContainer predictedContainer){

        int numberOfPLIPInt = predictedContainer.getInteractionContainersPLIP().gethBondInteractions().size();
        int numberOfHBM = findExactHBondMatches(predictedContainer).gethBondInteractions().size();
        int numberOfRHBM = findRangeHBondMatches(predictedContainer).getKey().gethBondInteractions().size();

        int numberOfLitInt = 0;
        if (predictedContainer.getInteractionContainersLiterature() != null){
            numberOfLitInt = predictedContainer.getInteractionContainersLiterature().gethBondInteractions().size();
        }

        int totalScore = 0;
        float normTotalScore = 0;



        Statistics statistics = new Statistics(
                numberOfPLIPInt,
                predictedContainer.getInteractionContainersDSSP().gethBondInteractions().size(),
                numberOfLitInt,
                numberOfHBM,
                (numberOfHBM / numberOfPLIPInt) * 100,
                numberOfRHBM,
                (numberOfRHBM / numberOfPLIPInt) * 100,
                totalScore,
                normTotalScore
        );
        return statistics;
    }

}
