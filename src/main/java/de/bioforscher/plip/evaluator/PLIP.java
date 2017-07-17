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

class PLIP implements EvaluatorModule {

    public Protein processPDBidPLIP(String PDBid){
        Structure protein = StructureParser.source(PDBid).parse();
        new PLIPAnnotator().process(protein);

        List<Interaction> interactionsList = new ArrayList<Interaction>();

        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if(!(group instanceof AminoAcid)) {
                    continue;
                }

                PLIPInteractionContainer secondaryStructurePLIP = group.getFeatureContainer().getFeature(PLIPInteractionContainer.class);

                Interaction interaction = new Interaction(group.getResidueIdentifier().getResidueNumber(), 0, 0, 0, 0, "H-Bond");


                //interactionsList.add(interaction);

            }
        }

        Interaction[] interactions = new Interaction[interactionsList.size()];
        interactions = interactionsList.toArray(interactions);

        Protein returnProtein = new Protein("", protein.getProteinIdentifier().getPdbId(), "all", interactions);


        return returnProtein;


    }


}
