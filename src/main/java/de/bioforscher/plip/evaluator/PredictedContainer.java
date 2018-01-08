package de.bioforscher.plip.evaluator;

import java.io.Serializable;

/**
 * 'container' holding all interactionContainers for different algorithms
 */
public class PredictedContainer implements Serializable {
    private InteractionContainer interactionContainersDSSP;
    private InteractionContainer interactionContainersPLIP;
    private InteractionContainer interactionContainersLiterature;
    private InteractionContainer interactionContainersHBPLUS;

    public PredictedContainer() {
    }

    public PredictedContainer(InteractionContainer interactionContainersDSSP, InteractionContainer interactionContainersPLIP, InteractionContainer interactionContainersLiterature, InteractionContainer interactionContainersHBPLUS) {
        this.interactionContainersDSSP = interactionContainersDSSP;
        this.interactionContainersPLIP = interactionContainersPLIP;
        this.interactionContainersLiterature = interactionContainersLiterature;
        this.interactionContainersHBPLUS = interactionContainersHBPLUS;
    }

    public InteractionContainer getInteractionContainersDSSP() {
        return interactionContainersDSSP;
    }

    public void setInteractionContainersDSSP(InteractionContainer interactionContainersDSSP) {
        this.interactionContainersDSSP = interactionContainersDSSP;
    }

    public InteractionContainer getInteractionContainersPLIP() {
        return interactionContainersPLIP;
    }

    public void setInteractionContainersPLIP(InteractionContainer interactionContainersPLIP) {
        this.interactionContainersPLIP = interactionContainersPLIP;
    }

    public InteractionContainer getInteractionContainersLiterature() {
        return interactionContainersLiterature;
    }

    public void setInteractionContainersLiterature(InteractionContainer interactionContainersLiterature) {
        this.interactionContainersLiterature = interactionContainersLiterature;
    }

    public InteractionContainer getInteractionContainersHBPLUS() {
        return interactionContainersHBPLUS;
    }

    public void setInteractionContainersHBPLUS(InteractionContainer interactionContainersHBPLUS) {
        this.interactionContainersHBPLUS = interactionContainersHBPLUS;
    }
}

