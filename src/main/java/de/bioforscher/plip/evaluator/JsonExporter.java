package de.bioforscher.plip.evaluator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class JsonExporter {

    public void exportDBAsJson(){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        HibernateHandler handler = new HibernateHandler();

        handler.openSession();

        List<Protein> proteinList =  handler.fetchAllProteins();

        handler.closeSession();

        String str = gson.toJson(proteinList);
        //System.out.println(str);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("DBexport.json"));
            writer.write(str);
            writer.close();
            System.out.println("DB successfully written.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
