package de.bioforscher.plip.evaluator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * protein object representation
 */

@Entity
public class Protein implements Serializable{

   @Id
    private String PDBid;
    private String chain;
    private byte[] interactionContainerByte;
    private String doi;

    @Transient
    private InteractionContainer interactionContainer;



    public Protein(String doi, String PDBid, String chain, InteractionContainer interactionContainer) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.interactionContainer = interactionContainer;
    }

    public Protein(String doi, String PDBid, String chain, byte[] interactionContainerByte) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.interactionContainerByte = interactionContainerByte;
    }

    public String getPDBid() {
        return PDBid;
    }

    public void setPDBid(String PDBid) {
        this.PDBid = PDBid;
    }

    public String getChain() {
        return chain;
    }

    public void setChain(String chain) {
        this.chain = chain;
    }

    public byte[] getInteractionContainerByte() {
        return interactionContainerByte;
    }

    public void setInteractionContainerByte(byte[] interactionContainerByte) {
        this.interactionContainerByte = interactionContainerByte;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public InteractionContainer getInteractionContainer() {
        return interactionContainer;
    }

    public void setInteractionContainer(InteractionContainer interactionContainer) {
        this.interactionContainer = interactionContainer;
    }
}

