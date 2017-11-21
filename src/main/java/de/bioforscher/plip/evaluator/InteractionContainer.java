package de.bioforscher.plip.evaluator;

import java.io.Serializable;
import java.util.List;

/**
 * some 'container' to hold the predicted interactions
 * -- is extendable for other interaction types
 */
public class InteractionContainer implements Serializable{

    private static final long serialVersionUID = 8995916421498263859L;

    private List<HBondInteraction> hBondInteractions;
    // more to come

    public InteractionContainer(){}

    public InteractionContainer(List<HBondInteraction> hBondInteractions) {
        this.hBondInteractions = hBondInteractions;
    }

    public void sethBondInteractions(List<HBondInteraction> hBondInteractions) {
        this.hBondInteractions = hBondInteractions;
    }

    public List<HBondInteraction> gethBondInteractions() {
        return hBondInteractions;
    }

    public void removeDuplicateHBonds(){

        List<HBondInteraction> hBondInteractions = this.gethBondInteractions();

        for (int i = 0; i < hBondInteractions.size(); i++) {
            for (int j = i+1; j < hBondInteractions.size(); j++) {
                if(hBondInteractions.get(i).equals(hBondInteractions.get(j))){hBondInteractions.remove(j);}
            }
        }

        for (int i = 0; i < hBondInteractions.size(); i++) {
            for (int j = i+1; j < hBondInteractions.size(); j++) {
                if(hBondInteractions.get(i).equals(hBondInteractions.get(j))){hBondInteractions.remove(j);}
            }
        }

        this.sethBondInteractions(hBondInteractions);
    }
}
