package de.bioforscher.plip.evaluator;

import java.io.Serializable;

/**
 * 'container' holding all interactionContainers for different algorithms
 */
public class PredictedContainer implements Serializable {
    private InteractionContainer interactionContainersDSSP;
    private InteractionContainer interactionContainersPLIP;
    private InteractionContainer interactionContainersLiterature;

    public PredictedContainer(InteractionContainer interactionContainersDSSP, InteractionContainer interactionContainersPLIP, InteractionContainer interactionContainersLiterature) {
        this.interactionContainersDSSP = interactionContainersDSSP;
        this.interactionContainersPLIP = interactionContainersPLIP;
        this.interactionContainersLiterature = interactionContainersLiterature;
    }

    public PredictedContainer() {}

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
}
