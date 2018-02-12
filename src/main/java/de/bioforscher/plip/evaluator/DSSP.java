package de.bioforscher.plip.evaluator;


import de.bioforscher.jstructure.feature.sse.dssp.DSSPSecondaryStructure;
import de.bioforscher.jstructure.feature.sse.dssp.DictionaryOfProteinSecondaryStructure;
import de.bioforscher.jstructure.model.structure.Chain;
import de.bioforscher.jstructure.model.structure.Group;
import de.bioforscher.jstructure.model.structure.Structure;
import de.bioforscher.jstructure.model.structure.StructureParser;
import de.bioforscher.jstructure.model.structure.aminoacid.AminoAcid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                DSSPSecondaryStructure secondaryStructureDSSP = group.getFeature(DSSPSecondaryStructure.class);

                int residueNumber = group.getResidueIdentifier().getResidueNumber();

                //System.out.println(secondaryStructureDSSP);


                //TODO offset?????
                if (secondaryStructureDSSP.getAccept1().getPartner() != null){
                    HBondInteraction interaction1 = new HBondInteraction(residueNumber, secondaryStructureDSSP.getAccept1().getPartner().getResidueIdentifier().getResidueNumber()+1, residueNumber-1);
                    hBondInteractions.add(interaction1);
                }

                if (secondaryStructureDSSP.getAccept2().getPartner() != null){
                    HBondInteraction interaction2 = new HBondInteraction(residueNumber, secondaryStructureDSSP.getAccept2().getPartner().getResidueIdentifier().getResidueNumber()+1, residueNumber-1);
                    hBondInteractions.add(interaction2);
                }

                if (secondaryStructureDSSP.getDonor1().getPartner() != null){
                    HBondInteraction interaction3 = new HBondInteraction(residueNumber, residueNumber+1, secondaryStructureDSSP.getDonor1().getPartner().getResidueIdentifier().getResidueNumber()-1);
                    hBondInteractions.add(interaction3);
                }

                if (secondaryStructureDSSP.getDonor2().getPartner() != null){
                    HBondInteraction interaction4 = new HBondInteraction(residueNumber, residueNumber+1, secondaryStructureDSSP.getDonor2().getPartner().getResidueIdentifier().getResidueNumber()-1);
                    hBondInteractions.add(interaction4);
                }

            }
        }

        // add interactions to container
        InteractionContainer interactionContainer = new InteractionContainer(hBondInteractions);
        interactionContainer.removeDuplicateHBonds();

        return interactionContainer;
    }


    public Map<Integer, String> getDSSPFeatureList(String PDBid){

        Structure protein = StructureParser.source(PDBid).parse();
        new DictionaryOfProteinSecondaryStructure().process(protein);

        Map<Integer, String> featureMap = new HashMap<>();

        //traverse over all amino acids
        for(Chain chain : protein.getChains()) {
            for (Group group : chain.getGroups()) {
                if (!(group instanceof AminoAcid)) {
                    continue;
                }

                //get features
                DSSPSecondaryStructure secondaryStructureDSSP = group.getFeature(DSSPSecondaryStructure.class);

                int residueNumber = group.getResidueIdentifier().getResidueNumber();
                String featureAnnotation = secondaryStructureDSSP.getSecondaryStructure().getReducedRepresentation();

                featureMap.put(residueNumber, featureAnnotation);
            }
        }

        return featureMap;
    }



}
