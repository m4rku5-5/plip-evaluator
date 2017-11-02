package de.bioforscher.plip.evaluator.InsertionInterface;

import de.bioforscher.plip.evaluator.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

//import de.bioforscher.plip.evaluator.Interaction;
//import org.apache.commons.lang3.StringUtils;



public class Controller {

    //define the variables
    @FXML TextField doi, PDBid, chain;

    @FXML TextField residueNumber, acceptor, donor, type;

    @FXML TableView<HBondInteraction> tableView;
    @FXML TableColumn<HBondInteraction, Integer> resNumberCol, acceptorCol, donorCol;
    //@FXML TableColumn<HBondInteraction, String> interTypeCol;

    //set the mapping for the TableView
    public void initialize(){

        resNumberCol.setCellValueFactory(new PropertyValueFactory<>("ResidueNumber"));
        acceptorCol.setCellValueFactory(new PropertyValueFactory<>("Accept"));
        donorCol.setCellValueFactory(new PropertyValueFactory<>("Donor"));
        //interTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }


    //adding the interactions to the TableView
    @FXML
    protected void addInteractionToTable() {

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

    //getting all interactions from the View and storing them in the Database/ merging with existing protein; resetting the interface
    @FXML
    protected void SubmitButtonAction(){

        List<HBondInteraction> hBondInteractions = new ArrayList<HBondInteraction>();
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
            if(fetchedProtein.getDoi() == null){
                fetchedProtein.setDoi(doi.getText());
            }
            System.out.println("Storing protein..");
            handler.mergeProtein(fetchedProtein);

            PDBid.setText("");
            doi.setText("");
            tableView.getItems().clear();

        } else {
            Protein protein = new Protein(doi.getText(), PDBid.getText(), "all", predictedContainer);
            System.out.println("Storing protein..");
            handler.storeProtein(protein);

            PDBid.setText("");
            doi.setText("");
            tableView.getItems().clear();
        }


    }




}
