package de.bioforscher.plip.evaluator;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by Markus on 05.10.2017.
 */

@Entity
public class ProteinByte extends Protein implements Serializable{

    @Column(columnDefinition = "MEDIUMBLOB")
    byte[] predictedContainerByte;

    public ProteinByte(){}

    public ProteinByte(String doi, String PDBid, String chain, byte[] predictedContainerByte) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.predictedContainerByte = predictedContainerByte;
    }

    public byte[] getPredictedContainerByte() {
        return predictedContainerByte;
    }

    public void setPredictedContainerByte(byte[] predictedContainerByte) {
        this.predictedContainerByte = predictedContainerByte;
    }
}
