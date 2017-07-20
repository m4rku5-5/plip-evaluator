package de.bioforscher.plip.evaluator;

import de.bioforscher.jstructure.feature.interactions.PLIPAnnotator;
import de.bioforscher.jstructure.feature.interactions.PLIPInteractionContainer;
import de.bioforscher.jstructure.feature.sse.GenericSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.assp.ASSPSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.dssp.DSSPSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.dssp.DictionaryOfProteinSecondaryStructure;
import de.bioforscher.jstructure.model.structure.Chain;
import de.bioforscher.jstructure.model.structure.Group;
import de.bioforscher.jstructure.model.structure.Structure;
import de.bioforscher.jstructure.model.structure.StructureParser;
import de.bioforscher.jstructure.model.structure.aminoacid.AminoAcid;


/**
 * central evaluator
 * --> currently only test functions
 */

public class PLIPEvaluator {
    public static void main(String[] args) {
        //process("1aki");
        test();



    }

    private static void process(String pdbID) {
        Structure protein = StructureParser.source(pdbID).parse();

        // annotate the features
        new DictionaryOfProteinSecondaryStructure().process(protein);
        new PLIPAnnotator().process(protein);

        // traverse all amino acids of the protein
        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if(!(group instanceof AminoAcid)) {
                    continue;
                }

                DSSPSecondaryStructure secondaryStructure = group.getFeatureContainer().getFeature(DSSPSecondaryStructure.class);
                PLIPInteractionContainer plipInteractionContainer = group.getFeatureContainer().getFeature(PLIPInteractionContainer.class);

                System.out.println(group);
                System.out.println(plipInteractionContainer.getHydrogenBonds());
                if(!plipInteractionContainer.getHydrogenBonds().isEmpty()){
                System.out.print(plipInteractionContainer.getHydrogenBonds().get(0).getAcceptor().getParentGroup().getResidueIdentifier().getResidueNumber() + " ");
                System.out.println(plipInteractionContainer.getHydrogenBonds().get(0).getDonor().getParentGroup().getResidueIdentifier().getResidueNumber());}
                System.out.println();

                //for (HydrogenBond hydrogenBond : plipInteractionContainer.getHydrogenBonds()) {

               //}
            }
        }
    }

    public static void test(){




    }
}
