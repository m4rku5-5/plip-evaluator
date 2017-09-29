package de.bioforscher.plip.evaluator;


import de.bioforscher.jstructure.feature.sse.dssp.DSSPSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.dssp.DictionaryOfProteinSecondaryStructure;
import de.bioforscher.jstructure.model.structure.Chain;
import de.bioforscher.jstructure.model.structure.Group;
import de.bioforscher.jstructure.model.structure.Structure;
import de.bioforscher.jstructure.model.structure.StructureParser;
import de.bioforscher.jstructure.model.structure.aminoacid.AminoAcid;

import java.util.ArrayList;
import java.util.List;

/**
 * DSSP module under use of jstructure
 */

class DSSP implements EvaluatorModule {

    // central function
    public InteractionContainer processPDBid(String PDBid){
        //make new structure and process with DSSP
        Structure protein = StructureParser.source(PDBid).parse();
        new DictionaryOfProteinSecondaryStructure().process(protein);

        List<HBondInteraction> hBondInteractions = new ArrayList<HBondInteraction>();

        //traverse over all amino acids
        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if(!(group instanceof AminoAcid)) {
                    continue;
                }

                //get features
                DSSPSecondaryStructure secondaryStructureDSSP = group.getFeatureContainer().getFeature(DSSPSecondaryStructure.class);

                //only HBond interactions
                HBondInteraction interaction1 = new HBondInteraction();


                //TODO --> is acceptor donor assignment right??
                if (secondaryStructureDSSP.getAccept1().getPartner() != null){
                    interaction1.setAccept(secondaryStructureDSSP.getAccept1().getPartner().getResidueIdentifier().getResidueNumber());
                }

                if (secondaryStructureDSSP.getDonor1().getPartner() != null) {
                    interaction1.setDonor(secondaryStructureDSSP.getDonor1().getPartner().getResidueIdentifier().getResidueNumber());
                }


                HBondInteraction interaction2 = new HBondInteraction();

                if (secondaryStructureDSSP.getAccept2().getPartner() != null){
                    interaction2.setAccept(secondaryStructureDSSP.getAccept2().getPartner().getResidueIdentifier().getResidueNumber());
                }

                if (secondaryStructureDSSP.getDonor2().getPartner() != null){
                    interaction2.setDonor(secondaryStructureDSSP.getDonor2().getPartner().getResidueIdentifier().getResidueNumber());
                }

                //add interactions to list
                hBondInteractions.add(interaction1);
                hBondInteractions.add(interaction2);

            }
        }

        // add interactions to container
        InteractionContainer interactionContainer = new InteractionContainer(hBondInteractions);

        //make a protein to return
        //Protein returnProtein = new Protein("", protein.getProteinIdentifier().getPdbId(), "all", interactionContainer);


        return interactionContainer;


    }
}
