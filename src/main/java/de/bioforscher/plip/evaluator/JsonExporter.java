package de.bioforscher.plip.evaluator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


/**
 * module to export the Database as a json for exchange
 */

public class JsonExporter {

    public void exportDBAsJson(){
        //used google gson as json interface
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //get all proteins
        HibernateHandler handler = new HibernateHandler();

        handler.openSession();

        List<Protein> proteinList =  handler.fetchAllProteins();

        handler.closeSession();

        String str = gson.toJson(proteinList);
        //System.out.println(str);

        //write json to file
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
