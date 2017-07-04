package de.bioforscher.plip.evaluator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class JsonHandler {



    public void makeJson(String doi, String PDBid, String chain, Interaction[] interactions){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Protein protein = new Protein(doi, PDBid, chain, interactions);

        String str = gson.toJson(protein);

    }

    public void makeJson(String PDBid, String chain, Interaction[] interactions){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Protein protein = new Protein(PDBid, chain, interactions);

        String str = gson.toJson(protein);
        System.out.println(str);
    }


}
