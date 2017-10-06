package de.bioforscher.plip.evaluator;


import de.bioforscher.jstructure.feature.interactions.PLIPAnnotator;
import de.bioforscher.jstructure.feature.interactions.PLIPInteractionContainer;
import de.bioforscher.jstructure.model.structure.Chain;
import de.bioforscher.jstructure.model.structure.Group;
import de.bioforscher.jstructure.model.structure.Structure;
import de.bioforscher.jstructure.model.structure.StructureParser;
import de.bioforscher.jstructure.model.structure.aminoacid.AminoAcid;

import java.util.ArrayList;
import java.util.List;

/**
 * PLIP module
 */

class PLIP implements EvaluatorModule {

    public InteractionContainer processPDBid(String PDBid){

        //make new structure and process with DSSP
        Structure protein = StructureParser.source(PDBid).parse();
        new PLIPAnnotator().process(protein);

        List<HBondInteraction> hBondInteractions = new ArrayList<HBondInteraction>();

        //traverse over all amino acids
        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if (!(group instanceof AminoAcid)) {
                    continue;
                }

                PLIPInteractionContainer plipInteractionContainer = group.getFeatureContainer().getFeature(PLIPInteractionContainer.class);

                if(!plipInteractionContainer.getHydrogenBonds().isEmpty()){
                    for (int i = 0; i < plipInteractionContainer.getHydrogenBonds().size(); i++) {
                        hBondInteractions.add(new HBondInteraction(group.getResidueIdentifier().getResidueNumber(),plipInteractionContainer.getHydrogenBonds().get(i).getDonor().getParentGroup().getResidueIdentifier().getResidueNumber(),plipInteractionContainer.getHydrogenBonds().get(i).getAcceptor().getParentGroup().getResidueIdentifier().getResidueNumber()));
                    }
                }
            }
        }

        // add interactions to container
        InteractionContainer interactionContainer = new InteractionContainer(hBondInteractions);
        interactionContainer.removeDuplicateHBonds();

        return interactionContainer;
    }
}
