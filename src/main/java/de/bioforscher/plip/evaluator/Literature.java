package de.bioforscher.plip.evaluator;

import com.google.gson.*;


class Literature implements EvaluatorModule{



    public void makeJson(){



        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Interaction ALA_GLU = new Interaction(1,2,15,"H-Bond");
        Interaction VAL_GLU = new Interaction(1,4,16,"H-Bond");
        Interaction[] interactions = {ALA_GLU, VAL_GLU};
        Protein protein = new Protein("bla123", "2bla", "A", interactions);

        String str = gson.toJson(protein);
        System.out.println(str);
    }



}
