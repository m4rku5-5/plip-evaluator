package de.bioforscher.plip.evaluator;

public class Interaction {
    private String type;
    private int residueNumber;
    private int donor;
    private int aceptor;


    public Interaction(int residueNumber, int donor, int aceptor, String type){
        this.residueNumber = residueNumber;
        this.donor = donor;
        this.aceptor = aceptor;
        this.type = type;
    }
}
