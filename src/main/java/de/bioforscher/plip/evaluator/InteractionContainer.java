package de.bioforscher.plip.evaluator;

import java.io.Serializable;
import java.util.List;

/**
 * some 'container' to hold the predicted interactions
 * --> is extendable for other interaction types
 */
public class InteractionContainer implements Serializable{

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
}
