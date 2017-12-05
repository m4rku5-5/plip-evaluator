package de.bioforscher.plip.evaluator;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.*;


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

        double range = Math.sqrt(8);
        for (int i = 0; i < hBondInteractionPLIP.size(); i++) {
            for (int j = 0; j < hBondInteractionDSSP.size(); j++) {
                double d = 0;
                d = Math.sqrt(Math.pow(hBondInteractionPLIP.get(i).getAccept()-hBondInteractionDSSP.get(j).getAccept(),2)+
                              Math.pow(hBondInteractionPLIP.get(i).getDonor()-hBondInteractionDSSP.get(j).getDonor(),2));
                if (d < range){
                    matches.add(hBondInteractionPLIP.get(i));
                    distanceList.add(d);
                }
            }
        }

        InteractionContainer rangeHBondInteractions = new InteractionContainer(matches);
        rangeHBondInteractions.removeDuplicateHBonds();


        Pair<InteractionContainer, List> containerDistancePair = new Pair<>(rangeHBondInteractions, distanceList);
        return containerDistancePair;
    }

    /*public PredictedContainer annotateDSSPFeatures(PredictedContainer predictedContainer){

        List<HBondInteraction> hBondInteractionDSSP = predictedContainer.getInteractionContainersDSSP().gethBondInteractions();
        List<HBondInteraction> hBondInteractionPLIP = predictedContainer.getInteractionContainersPLIP().gethBondInteractions();




        return ;
    }*/
}
