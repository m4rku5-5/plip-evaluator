package de.bioforscher.plip.evaluator.InsertionInterface;

import de.bioforscher.plip.evaluator.*;
//import de.bioforscher.plip.evaluator.Interaction;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;

import java.util.List;
//import org.apache.commons.lang3.StringUtils;



public class Controller {

    @FXML TextField doi, PDBid, chain;

    @FXML TextField residueNumber, acceptor, donor, type;

    @FXML TableView<HBondInteraction> tableView;
    @FXML TableColumn<HBondInteraction, Integer> resNumberCol, acceptorCol, donorCol;
    //@FXML TableColumn<HBondInteraction, String> interTypeCol;


    public void initialize(){

        resNumberCol.setCellValueFactory(new PropertyValueFactory<>("ResidueNumber"));
        acceptorCol.setCellValueFactory(new PropertyValueFactory<>("Accept"));
        donorCol.setCellValueFactory(new PropertyValueFactory<>("Donor"));
        //interTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }


    //TODO specify interaction types

    @FXML
    protected void addInteractionToTable() {
//        if (type.getText() == "H-Bond"){
//            System.err.println("Type must be H-Bond, ::::::::::::::::::::::::::::::::::::");
//        }
        if (StringUtils.isNumeric(residueNumber.getText()) == false || StringUtils.isNumeric(donor.getText()) == false || StringUtils.isNumeric(acceptor.getText()) == false){
            System.err.println("Residue Number, Donor and Acceptor must be numeric!");
        }
        else {
            tableView.getItems().add(new HBondInteraction(Integer.valueOf(residueNumber.getText()) , Integer.valueOf(donor.getText()), Integer.valueOf(acceptor.getText())));

            residueNumber.setText("");
            donor.setText("");
            acceptor.setText("");
            //type.setText("");
        }

    }

    @FXML
    protected void removeInteractionFromTable(){
        tableView.getItems().remove(tableView.getSelectionModel().getSelectedIndex());
    }


    @FXML
    protected void SubmitButtonAction(){

        List<HBondInteraction> hBondInteractions = null;
        for (int i = 0; i < tableView.getItems().size(); i++) {
            hBondInteractions.add(tableView.getItems().get(i));
        }

        InteractionContainer interactionContainer = new InteractionContainer(hBondInteractions);
        PredictedContainer predictedContainer = new PredictedContainer();
        predictedContainer.setInteractionContainersLiterature(interactionContainer);


        HibernateHandler handler = new HibernateHandler();
        handler.openSession();

        if(handler.containsProtein(PDBid.getText())){
            Protein fetchedProtein = handler.fetchProtein(PDBid.getText());
            fetchedProtein.getPredictedContainer().setInteractionContainersLiterature(interactionContainer);
            handler.storeProtein(fetchedProtein);
        } else {
            Protein protein = new Protein(doi.getText(), PDBid.getText(), "all", predictedContainer);
            handler.storeProtein(protein);
        }


    }




}
