package de.bioforscher.plip.evaluator;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
public class Protein implements Serializable{

    @Id
    private String PDBid;
    private String chain;
    private byte[] interactionsByte;

    @Transient
    private Interaction[] interactions;
    private String doi;


    public Protein(String doi, String PDBid, String chain, Interaction[] interactions) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.interactions = interactions;
    }

    public Protein(String doi, String PDBid, String chain, byte[] interactions) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.interactionsByte = interactions;
    }

    public Protein(){}

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
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

    public Interaction[] getInteractions() {
        return interactions;
    }

    public void setInteractions(Interaction[] interactions) {
        this.interactions = interactions;
    }

    public byte[] getInteractionsByte() {
        return interactionsByte;
    }

    public void setInteractionsByte(byte[] interactionsByte) {
        this.interactionsByte = interactionsByte;
    }
}

