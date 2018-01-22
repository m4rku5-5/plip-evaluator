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
        int numberOfHBPLUSInt;
        int numberOfLitInt;
        int numberOfHBMPLIPDSSP;
        int numberOfHBMPLIPHBPLUS;
        float percentOfHBMPLIPDSSP;
        float percentOfHBMPLIPHBPLUS;
        int numberOfRHBMPLIPDSSP;
        int numberOfRHBMPLIPHBPLUS;
        float percentOfRHBMPLIPDSSP;
        float percentOfRHBMPLIPHBPLUS;
        int totalScorePLIPDSSP;
        int totalScorePLIPHBPLUS;
        float normTotalScorePLIPDSSP;
        float normTotalScorePLIPHBPLUS;

        public Statistics() {
        }

        public Statistics(int numberOfPLIPInt, int numberOfDSSPInt, int numberOfHBPLUSInt, int numberOfLitInt, int numberOfHBMPLIPDSSP, int numberOfHBMPLIPHBPLUS, float percentOfHBMPLIPDSSP, float percentOfHBMPLIPHBPLUS, int numberOfRHBMPLIPDSSP, int numberOfRHBMPLIPHBPLUS, float percentOfRHBMPLIPDSSP, float percentOfRHBMPLIPHBPLUS, int totalScorePLIPDSSP, int totalScorePLIPHBPLUS, float normTotalScorePLIPDSSP, float normTotalScorePLIPHBPLUS) {
            this.numberOfPLIPInt = numberOfPLIPInt;
            this.numberOfDSSPInt = numberOfDSSPInt;
            this.numberOfHBPLUSInt = numberOfHBPLUSInt;
            this.numberOfLitInt = numberOfLitInt;
            this.numberOfHBMPLIPDSSP = numberOfHBMPLIPDSSP;
            this.numberOfHBMPLIPHBPLUS = numberOfHBMPLIPHBPLUS;
            this.percentOfHBMPLIPDSSP = percentOfHBMPLIPDSSP;
            this.percentOfHBMPLIPHBPLUS = percentOfHBMPLIPHBPLUS;
            this.numberOfRHBMPLIPDSSP = numberOfRHBMPLIPDSSP;
            this.numberOfRHBMPLIPHBPLUS = numberOfRHBMPLIPHBPLUS;
            this.percentOfRHBMPLIPDSSP = percentOfRHBMPLIPDSSP;
            this.percentOfRHBMPLIPHBPLUS = percentOfRHBMPLIPHBPLUS;
            this.totalScorePLIPDSSP = totalScorePLIPDSSP;
            this.totalScorePLIPHBPLUS = totalScorePLIPHBPLUS;
            this.normTotalScorePLIPDSSP = normTotalScorePLIPDSSP;
            this.normTotalScorePLIPHBPLUS = normTotalScorePLIPHBPLUS;
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

        public int getNumberOfHBPLUSInt() {
            return numberOfHBPLUSInt;
        }

        public void setNumberOfHBPLUSInt(int numberOfHBPLUSInt) {
            this.numberOfHBPLUSInt = numberOfHBPLUSInt;
        }

        public int getNumberOfLitInt() {
            return numberOfLitInt;
        }

        public void setNumberOfLitInt(int numberOfLitInt) {
            this.numberOfLitInt = numberOfLitInt;
        }

        public int getNumberOfHBMPLIPDSSP() {
            return numberOfHBMPLIPDSSP;
        }

        public void setNumberOfHBMPLIPDSSP(int numberOfHBMPLIPDSSP) {
            this.numberOfHBMPLIPDSSP = numberOfHBMPLIPDSSP;
        }

        public int getNumberOfHBMPLIPHBPLUS() {
            return numberOfHBMPLIPHBPLUS;
        }

        public void setNumberOfHBMPLIPHBPLUS(int numberOfHBMPLIPHBPLUS) {
            this.numberOfHBMPLIPHBPLUS = numberOfHBMPLIPHBPLUS;
        }

        public float getPercentOfHBMPLIPDSSP() {
            return percentOfHBMPLIPDSSP;
        }

        public void setPercentOfHBMPLIPDSSP(float percentOfHBMPLIPDSSP) {
            this.percentOfHBMPLIPDSSP = percentOfHBMPLIPDSSP;
        }

        public float getPercentOfHBMPLIPHBPLUS() {
            return percentOfHBMPLIPHBPLUS;
        }

        public void setPercentOfHBMPLIPHBPLUS(float percentOfHBMPLIPHBPLUS) {
            this.percentOfHBMPLIPHBPLUS = percentOfHBMPLIPHBPLUS;
        }

        public int getNumberOfRHBMPLIPDSSP() {
            return numberOfRHBMPLIPDSSP;
        }

        public void setNumberOfRHBMPLIPDSSP(int numberOfRHBMPLIPDSSP) {
            this.numberOfRHBMPLIPDSSP = numberOfRHBMPLIPDSSP;
        }

        public int getNumberOfRHBMPLIPHBPLUS() {
            return numberOfRHBMPLIPHBPLUS;
        }

        public void setNumberOfRHBMPLIPHBPLUS(int numberOfRHBMPLIPHBPLUS) {
            this.numberOfRHBMPLIPHBPLUS = numberOfRHBMPLIPHBPLUS;
        }

        public float getPercentOfRHBMPLIPDSSP() {
            return percentOfRHBMPLIPDSSP;
        }

        public void setPercentOfRHBMPLIPDSSP(float percentOfRHBMPLIPDSSP) {
            this.percentOfRHBMPLIPDSSP = percentOfRHBMPLIPDSSP;
        }

        public float getPercentOfRHBMPLIPHBPLUS() {
            return percentOfRHBMPLIPHBPLUS;
        }

        public void setPercentOfRHBMPLIPHBPLUS(float percentOfRHBMPLIPHBPLUS) {
            this.percentOfRHBMPLIPHBPLUS = percentOfRHBMPLIPHBPLUS;
        }

        public int getTotalScorePLIPDSSP() {
            return totalScorePLIPDSSP;
        }

        public void setTotalScorePLIPDSSP(int totalScorePLIPDSSP) {
            this.totalScorePLIPDSSP = totalScorePLIPDSSP;
        }

        public int getTotalScorePLIPHBPLUS() {
            return totalScorePLIPHBPLUS;
        }

        public void setTotalScorePLIPHBPLUS(int totalScorePLIPHBPLUS) {
            this.totalScorePLIPHBPLUS = totalScorePLIPHBPLUS;
        }

        public float getNormTotalScorePLIPDSSP() {
            return normTotalScorePLIPDSSP;
        }

        public void setNormTotalScorePLIPDSSP(float normTotalScorePLIPDSSP) {
            this.normTotalScorePLIPDSSP = normTotalScorePLIPDSSP;
        }

        public float getNormTotalScorePLIPHBPLUS() {
            return normTotalScorePLIPHBPLUS;
        }

        public void setNormTotalScorePLIPHBPLUS(float normTotalScorePLIPHBPLUS) {
            this.normTotalScorePLIPHBPLUS = normTotalScorePLIPHBPLUS;
        }

        @Override
        public String toString() {

            String out = "\nNumber of PLIP interactions: " + this.numberOfPLIPInt +
                         "\nNumber of DSSP interactions: " + this.numberOfDSSPInt +
                         "\nNumber of HBPLUS interactions: " + this.numberOfHBPLUSInt +
                         "\nNumber of Literature interactions: " + this.numberOfLitInt +
                         "\n\nNumber of exact HBond matches (PLIP-DSSP): " + this.numberOfHBMPLIPDSSP +
                         "\nPercentage of exact HBond matches (PLIP-DSSP): " + this.percentOfHBMPLIPDSSP + "%" +
                         "\nNumber of exact HBond matches (PLIP-HBPLUS): " + this.numberOfHBMPLIPHBPLUS +
                         "\nPercentage of exact HBond matches (PLIP-HBPLUS): " + this.percentOfHBMPLIPHBPLUS + "%" +
                         "\n\nNumber of range HBond matches (PLIP-DSSP): " + this.numberOfRHBMPLIPDSSP +
                         "\nPercentage of range HBond matches (PLIP-DSSP): " + this.percentOfRHBMPLIPDSSP + "%" +
                         "\nNumber of range HBond matches (PLIP-HBPLUS): " + this.numberOfRHBMPLIPHBPLUS +
                         "\nPercentage of range HBond matches (PLIP-HBPLUS): " + this.percentOfRHBMPLIPHBPLUS + "%" +
                         "\n\nTotal Score (PLIP-DSSP): " + this.totalScorePLIPDSSP +
                         "\nTotal Score (PLIP-HBPLUS): " + this.totalScorePLIPHBPLUS +
                         "\nNormalized Total Score (PLIP-DSSP): " + this.normTotalScorePLIPDSSP +
                         "\nNormalized Total Score (PLIP-HBPLUS): " + this.normTotalScorePLIPHBPLUS;
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
    public InteractionContainer findExactHBondMatches(List<HBondInteraction> hBondInteractionPLIP, List<HBondInteraction> hBondInteraction2){
        List<HBondInteraction> matches = new ArrayList<>();


        for (int i = 0; i < hBondInteractionPLIP.size(); i++) {
            for (int j = 0; j < hBondInteraction2.size(); j++) {
                if (hBondInteractionPLIP.get(i).getAccept() == hBondInteraction2.get(j).getAccept() && hBondInteractionPLIP.get(i).getDonor() == hBondInteraction2.get(j).getDonor()){
                    matches.add(hBondInteractionPLIP.get(i));
                }
            }
        }

        InteractionContainer exactHBondInteractions = new InteractionContainer(matches);
        exactHBondInteractions.removeDuplicateHBonds();
        return exactHBondInteractions;
    }


    public Pair<InteractionContainer, List> findRangeHBondMatches(List<HBondInteraction> hBondInteractionPLIP, List<HBondInteraction> hBondInteraction2){
        List<HBondInteraction> matches = new ArrayList<>();


        List<Double> distanceList = new ArrayList<>();

        double range = Precision.round(Math.sqrt(8), 2);
        for (int i = 0; i < hBondInteractionPLIP.size(); i++) {
            for (int j = 0; j < hBondInteraction2.size(); j++) {
                double d = 0;
                d = Math.sqrt(Math.pow(hBondInteractionPLIP.get(i).getAccept()-hBondInteraction2.get(j).getAccept(),2)+
                              Math.pow(hBondInteractionPLIP.get(i).getDonor()-hBondInteraction2.get(j).getDonor(),2));
                d = Precision.round(d, 2);

                if (d < range){
                   matches.add(hBondInteractionPLIP.get(i));
                   distanceList.add(d);

                }
            }
        }

        //List cleanup
        List<HBondInteraction> uniquematches = new ArrayList<>(matches);
        InteractionContainer tempContainer = new InteractionContainer(uniquematches);
        tempContainer.removeDuplicateHBonds();
        tempContainer.removeDuplicateHBonds();
        TreeMap<Double, HBondInteraction> tempmap = new TreeMap<>();
        List<Double> distanceListCleaned = new ArrayList<>();
        List<HBondInteraction> matchesCleaned = new ArrayList<>();


        for (int i = 0; i < uniquematches.size(); i++) {
            for (int j = 0; j < matches.size(); j++) {
                if (uniquematches.get(i).equals(matches.get(j))){
                    tempmap.put(distanceList.get(j), matches.get(j));
                }
            }
            matchesCleaned.add(tempmap.firstEntry().getValue());
            distanceListCleaned.add(tempmap.firstEntry().getKey());
            tempmap.clear();
        }

        InteractionContainer rangeHBondInteractions = new InteractionContainer(matchesCleaned);


        Pair<InteractionContainer, List> containerDistancePair = new Pair<>(rangeHBondInteractions, distanceListCleaned);
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
        int numberOfHBMPLIPDSSP = findExactHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersDSSP().gethBondInteractions()).gethBondInteractions().size();
        int numberOfRHBMPLIPDSSP = findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersDSSP().gethBondInteractions()).getKey().gethBondInteractions().size();
        int numberOfHBMPLIPHBPLUS = findExactHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions()).gethBondInteractions().size();
        int numberOfRHBMPLIPHBPLUS = findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions()).getKey().gethBondInteractions().size();



        int numberOfLitInt = 0;
        if (predictedContainer.getInteractionContainersLiterature() != null){
            numberOfLitInt = predictedContainer.getInteractionContainersLiterature().gethBondInteractions().size();
        }

        float percentOfHBMPLIPDSSP = Precision.round( ((float) numberOfHBMPLIPDSSP / numberOfPLIPInt) * 100, 2);
        float percentOfRHBMPLIPDSSP = Precision.round ( ((float) numberOfRHBMPLIPDSSP / numberOfPLIPInt) * 100, 2);
        float percentOfHBMPLIPHBPLUS = Precision.round( ((float) numberOfHBMPLIPHBPLUS / numberOfPLIPInt) * 100, 2);
        float percentOfRHBMPLIPHBPLUS = Precision.round ( ((float) numberOfRHBMPLIPHBPLUS / numberOfPLIPInt) * 100, 2);


        //Scoring
        int totalScorePLIPDSSP = 0;
        int totalScorePLIPHBPLUS = 0;

        //wins and costs
        int costHBM = 5;
        int costRHBM1And14 = 2;
        int costRHBM22 = 1;
        int costMM = -1;


        totalScorePLIPDSSP = totalScorePLIPDSSP + (numberOfHBMPLIPDSSP * costHBM);
        totalScorePLIPDSSP = totalScorePLIPDSSP + (Collections.frequency(findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersDSSP().gethBondInteractions()).getValue(), 1.0) * costRHBM1And14) +
                                  (Collections.frequency(findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersDSSP().gethBondInteractions()).getValue(), 1.41) * costRHBM1And14);
        totalScorePLIPDSSP = totalScorePLIPDSSP + (Collections.frequency(findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersDSSP().gethBondInteractions()).getValue(), 2.24) * costRHBM22);
        totalScorePLIPDSSP = totalScorePLIPDSSP + ((numberOfPLIPInt - numberOfHBMPLIPDSSP - numberOfRHBMPLIPDSSP) * costMM);

        totalScorePLIPHBPLUS = totalScorePLIPHBPLUS + (numberOfHBMPLIPHBPLUS * costHBM);
        totalScorePLIPHBPLUS = totalScorePLIPHBPLUS + (Collections.frequency(findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions()).getValue(), 1.0) * costRHBM1And14) +
                (Collections.frequency(findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions()).getValue(), 1.41) * costRHBM1And14);
        totalScorePLIPHBPLUS = totalScorePLIPHBPLUS + (Collections.frequency(findRangeHBondMatches(predictedContainer.getInteractionContainersPLIP().gethBondInteractions(), predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions()).getValue(), 2.24) * costRHBM22);
        totalScorePLIPHBPLUS = totalScorePLIPHBPLUS + ((numberOfPLIPInt - numberOfHBMPLIPHBPLUS - numberOfRHBMPLIPHBPLUS) * costMM);



        float normTotalScorePLIPDSSP = Precision.round((float) totalScorePLIPDSSP / numberOfPLIPInt, 4);
        float normTotalScorePLIPHBPLUS = Precision.round((float) totalScorePLIPHBPLUS / numberOfPLIPInt, 4);


        Statistics statistics = new Statistics(
                numberOfPLIPInt,
                predictedContainer.getInteractionContainersDSSP().gethBondInteractions().size(),
                predictedContainer.getInteractionContainersHBPLUS().gethBondInteractions().size(),
                numberOfLitInt,
                numberOfHBMPLIPDSSP,
                numberOfHBMPLIPHBPLUS,
                percentOfHBMPLIPDSSP,
                percentOfHBMPLIPHBPLUS,
                numberOfRHBMPLIPDSSP,
                numberOfRHBMPLIPHBPLUS,
                percentOfRHBMPLIPDSSP,
                percentOfRHBMPLIPHBPLUS,
                totalScorePLIPDSSP,
                totalScorePLIPHBPLUS,
                normTotalScorePLIPDSSP,
                normTotalScorePLIPHBPLUS
        );
        return statistics;
    }

}
