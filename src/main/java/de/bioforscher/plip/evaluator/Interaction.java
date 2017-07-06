package de.bioforscher.plip.evaluator;

import javafx.beans.property.SimpleStringProperty;

public class Interaction {
    private String type;
    private int residueNumber;
    private int donor;
    private int aceptor;


    private final SimpleStringProperty residueNumberFX = new SimpleStringProperty("");
    private final SimpleStringProperty donorFX = new SimpleStringProperty("");
    private final SimpleStringProperty aceptorFX = new SimpleStringProperty("");
    private final SimpleStringProperty typeFX = new SimpleStringProperty("");



    public Interaction(int residueNumber, int donor, int aceptor, String type){
        this.residueNumber = residueNumber;
        this.donor = donor;
        this.aceptor = aceptor;
        this.type = type;
    }

    public Interaction(String residueNumber, String donor, String aceptor, String type){
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
    }
}
