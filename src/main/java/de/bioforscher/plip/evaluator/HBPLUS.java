package de.bioforscher.plip.evaluator;

import de.bioforscher.jstructure.model.structure.Structure;
import de.bioforscher.jstructure.model.structure.StructureParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HBPLUS implements EvaluatorModule{

    @Override
    public InteractionContainer processPDBid(String PDBid) {

        Structure structure = StructureParser.source(PDBid).parse();
        List<HBondInteraction> hBondInteractions = new ArrayList<HBondInteraction>();

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(PDBid + "_cleaned.pdb", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        writer.println(structure.getPdbRepresentation().replaceAll("(?m)^HETATM.*", ""));
        writer.close();

        try {
            Process p = Runtime.getRuntime().exec("hbplus.exe " + PDBid + "_cleaned.pdb");
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileReader fr;
        try {
            fr = new FileReader(PDBid + "_cleaned.hb2");
            BufferedReader br = new BufferedReader(fr);

            String line;
            List<String> lineList = new ArrayList<>();
            while( (line = br.readLine()) != null )
            {
                lineList.add(line);
            }

            br.close();

            for (int i = 8; i < lineList.size(); i++) {
                String [] current= lineList.get(i).split("\\s+");

                hBondInteractions.add(new HBondInteraction(0, Integer.valueOf(current[0].substring(1, 5)), Integer.valueOf(current[2].substring(1, 5))));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        InteractionContainer interactionContainer = new InteractionContainer(hBondInteractions);
        interactionContainer.removeDuplicateHBonds();

        return interactionContainer;
    }


}
