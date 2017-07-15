package de.bioforscher.plip.evaluator;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Interaction implements Serializable{

    private String type;
    private int residueNumber;
    private int donor1;
    private int donor2;
    private int accept1;
    private int accept2;


    //TODO specify and check interaction types


    public Interaction(int residueNumber, int accept1, int accept2, int donor1, int donor2, String type){
        this.residueNumber = residueNumber;
        this.donor1 = donor1;
        this.donor2 = donor2;
        this.accept1 = accept1;
        this.accept2 = accept2;
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getResidueNumber() {
        return residueNumber;
    }

    public void setResidueNumber(int residueNumber) {
        this.residueNumber = residueNumber;
    }

    public int getDonor1() {
        return donor1;
    }

    public void setDonor1(int donor1) {
        this.donor1 = donor1;
    }

    public int getDonor2() {
        return donor2;
    }

    public void setDonor2(int donor2) {
        this.donor2 = donor2;
    }

    public int getAccept1() {
        return accept1;
    }

    public void setAccept1(int accept1) {
        this.accept1 = accept1;
    }

    public int getAccept2() {
        return accept2;
    }

    public void setAccept2(int accept2) {
        this.accept2 = accept2;
    }


}
