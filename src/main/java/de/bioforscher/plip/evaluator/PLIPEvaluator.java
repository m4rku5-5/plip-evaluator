package de.bioforscher.plip.evaluator;

import de.bioforscher.jstructure.feature.interactions.HydrogenBond;
import de.bioforscher.jstructure.feature.interactions.PLIPAnnotator;
import de.bioforscher.jstructure.feature.interactions.PLIPInteractionContainer;
import de.bioforscher.jstructure.feature.sse.GenericSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.dssp.DSSPSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.dssp.DictionaryOfProteinSecondaryStructure;
import de.bioforscher.jstructure.model.structure.Chain;
import de.bioforscher.jstructure.model.structure.Group;
import de.bioforscher.jstructure.model.structure.Protein;
import de.bioforscher.jstructure.model.structure.aminoacid.AminoAcid;
import de.bioforscher.jstructure.parser.ProteinParser;

import java.util.TimeZone;


public class PLIPEvaluator {
    public static void main(String[] args) {
        //process("1aki");
        test();



    }

    private static void process(String pdbID) {
        Protein protein = ProteinParser.source(pdbID).parse();

        // annotate the features
        new DictionaryOfProteinSecondaryStructure().process(protein);
        //new PLIPAnnotator().process(protein);

        // traverse all amino acids of the protein
        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if(!(group instanceof AminoAcid)) {
                    continue;
                }

                GenericSecondaryStructure secondaryStructure = group.getFeatureContainer().getFeature(GenericSecondaryStructure.class);
               // PLIPInteractionContainer plipInteractionContainer = group.getFeatureContainer().getFeature(PLIPInteractionContainer.class);

                System.out.println(group);
                System.out.println(secondaryStructure.getSecondaryStructure());
                //System.out.println(plipInteractionContainer.getHydrogenBonds());
                System.out.println();

                //for (HydrogenBond hydrogenBond : plipInteractionContainer.getHydrogenBonds()) {

               //}
            }
        }
    }

    public static void test(){

        Interaction[] interactions = {new Interaction(1,3,5,3,"H-Bond"), new Interaction(3,3,7,5,"H-Bond")};
        de.bioforscher.plip.evaluator.Protein protein = new de.bioforscher.plip.evaluator.Protein("xyzz:34","5blb","E",interactions);

        HibernateHandler handler = new HibernateHandler();

        handler.openSession();
        handler.storeProtein(protein);

        de.bioforscher.plip.evaluator.Protein loaded = handler.fetchProtein("4blu");
        System.out.println(loaded.getDoi());

        handler.closeSession();

    }
}
