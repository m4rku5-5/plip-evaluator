package de.bioforscher.plip.evaluator;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Interaction implements Serializable{

    private int id;
    private String type;
    private int residueNumber;
    private int donor;
    private int aceptor;




    public Interaction(int id, int residueNumber, int donor, int aceptor, String type){
        this.residueNumber = residueNumber;
        this.donor = donor;
        this.aceptor = aceptor;
        this.type = type;
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getDonor() {
        return donor;
    }

    public void setDonor(int donor) {
        this.donor = donor;
    }

    public int getAceptor() {
        return aceptor;
    }

    public void setAceptor(int aceptor) {
        this.aceptor = aceptor;
    }


    // JavaFX Stuff <------------------------------------------------------------------------------


        /*    private final SimpleStringProperty residueNumberFX = new SimpleStringProperty("");
    private final SimpleStringProperty donorFX = new SimpleStringProperty("");
    private final SimpleStringProperty aceptorFX = new SimpleStringProperty("");
    private final SimpleStringProperty typeFX = new SimpleStringProperty("");*/

    /*public Interaction(String residueNumber, String donor, String aceptor, String type){
        setResidueNumberFX(residueNumber);
        setDonorFX(donor);
        setAceptorFX(aceptor);
        setTypeFX(type);
    }

    public Interaction(){
        this("","","","");
    }

    public void setResidueNumberFX(String residueNumberFX) {
        this.residueNumberFX.set(residueNumberFX);
    }

    public void setDonorFX(String donorFX) {
        this.donorFX.set(donorFX);
    }

    public void setAceptorFX(String aceptorFX) {
        this.aceptorFX.set(aceptorFX);
    }

    public void setTypeFX(String typeFX) {
        this.typeFX.set(typeFX);
    }*/


}
