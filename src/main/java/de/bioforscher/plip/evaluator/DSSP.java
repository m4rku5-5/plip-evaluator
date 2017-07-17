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

class DSSP implements EvaluatorModule {

    public Protein processPDBidDSSP(String PDBid){
        Structure protein = StructureParser.source(PDBid).parse();
        new DictionaryOfProteinSecondaryStructure().process(protein);

        List<Interaction> interactionsList = new ArrayList<Interaction>();

        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if(!(group instanceof AminoAcid)) {
                    continue;
                }

                DSSPSecondaryStructure secondaryStructureDSSP = group.getFeatureContainer().getFeature(DSSPSecondaryStructure.class);

                Interaction interaction = new Interaction(group.getResidueIdentifier().getResidueNumber(), 0, 0, 0, 0, "H-Bond");

                if (secondaryStructureDSSP.getAccept1().getPartner() != null){
                    interaction.setAccept1(secondaryStructureDSSP.getAccept1().getPartner().getResidueIdentifier().getResidueNumber());
                }
                if (secondaryStructureDSSP.getAccept2().getPartner() != null){
                    interaction.setAccept2(secondaryStructureDSSP.getAccept2().getPartner().getResidueIdentifier().getResidueNumber());
                }
                if (secondaryStructureDSSP.getDonor1().getPartner() != null){
                    interaction.setDonor1(secondaryStructureDSSP.getDonor1().getPartner().getResidueIdentifier().getResidueNumber());
                }
                if (secondaryStructureDSSP.getDonor2().getPartner() != null){
                    interaction.setDonor2(secondaryStructureDSSP.getDonor2().getPartner().getResidueIdentifier().getResidueNumber());
                }
                
                interactionsList.add(interaction);

            }
        }

        Interaction[] interactions = new Interaction[interactionsList.size()];
        interactions = interactionsList.toArray(interactions);

        Protein returnProtein = new Protein("", protein.getProteinIdentifier().getPdbId(), "all", interactions);


        return returnProtein;


    }

}
