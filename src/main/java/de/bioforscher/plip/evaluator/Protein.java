package de.bioforscher.plip.evaluator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * protein object representation
 */

@Entity
public class Protein implements Serializable{

    @Id
     String PDBid;
     String chain;
     String doi;

     @Transient
     PredictedContainer predictedContainer;

    public Protein(){}

    public Protein(String doi, String PDBid, String chain, PredictedContainer predictedContainer) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.predictedContainer = predictedContainer;
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

