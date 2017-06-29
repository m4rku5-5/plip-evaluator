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

/**
 * Created by markus on 29.06.17.
 */
public class Main {
    public static void main(String[] args) {
        process("1aki");
    }

    private static void process(String pdbID) {
        Protein protein = ProteinParser.source(pdbID).parse();

        // annotate the features
        new DictionaryOfProteinSecondaryStructure().process(protein);
        new PLIPAnnotator().process(protein);

        // traverse all amino acids of the protein
        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if(!(group instanceof AminoAcid)) {
                    continue;
                }

                GenericSecondaryStructure secondaryStructure = group.getFeatureContainer().getFeature(GenericSecondaryStructure.class);
                PLIPInteractionContainer plipInteractionContainer = group.getFeatureContainer().getFeature(PLIPInteractionContainer.class);

                System.out.println(group);
                System.out.println(secondaryStructure.getSecondaryStructure());
                System.out.println(plipInteractionContainer.getHydrogenBonds());

                for (HydrogenBond hydrogenBond : plipInteractionContainer.getHydrogenBonds()) {
                    
                }
            }
        }
    }
}
