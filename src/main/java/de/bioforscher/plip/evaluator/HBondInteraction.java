package de.bioforscher.plip.evaluator;


import java.io.Serializable;

/**
 * basic HBond interaction
 */

//TODO maybe some other parameters like energy or angle

public class HBondInteraction implements Serializable{

    private int residueNumber;
    private int donor;
    private int accept;


    public HBondInteraction(int residueNumber, int donor, int accept) {
        this.residueNumber = residueNumber;
        this.donor = donor;
        this.accept = accept;
    }

    public HBondInteraction() {
    }

    public int getResidueNumber() {
        return residueNumber;
    }

    public void setResidueNumber(int residueNumber) {
        this.residueNumber = residueNumber;
    }

    public int getDonor() {
        return donor;
    }

    public void setDonor(int donor) {
        this.donor = donor;
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    @Override
    public boolean equals(Object obj) {
        HBondInteraction hBondInteraction = (HBondInteraction) obj;
        if (this.getResidueNumber() == hBondInteraction.getResidueNumber() && this.getAccept() == hBondInteraction.getAccept() && this.getDonor() == hBondInteraction.getDonor()){return true;}
        return false;
    }
}
