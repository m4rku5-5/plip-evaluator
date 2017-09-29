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
    private byte[] predictedContainerByte;
    private String doi;

    @Transient
    private PredictedContainer predictedContainer;


    public Protein(String doi, String PDBid, String chain, PredictedContainer predictedContainer) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.predictedContainer = predictedContainer;
    }

    public Protein(String doi, String PDBid, String chain, byte[] predictedContainerByte) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.predictedContainerByte = predictedContainerByte;
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

    public byte[] getPredictedContainerByte() {
        return predictedContainerByte;
    }

    public void setPredictedContainerByte(byte[] predictedContainerByte) {
        this.predictedContainerByte = predictedContainerByte;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public PredictedContainer getPredictedContainer() {
        return predictedContainer;
    }

    public void setPredictedContainer(PredictedContainer predictedContainer) {
        this.predictedContainer = predictedContainer;
    }
}

