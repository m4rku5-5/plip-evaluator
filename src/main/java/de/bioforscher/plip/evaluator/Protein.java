package de.bioforscher.plip.evaluator;

public class Protein {
    private String doi;
    private String PDBid;
    private String chain;
    private Interaction[] interactions;

    public Protein(String doi, String PDBid, String chain, Interaction[] interactions) {
        this.doi = doi;
        this.PDBid = PDBid;
        this.chain = chain;
        this.interactions = interactions;
    }
}

