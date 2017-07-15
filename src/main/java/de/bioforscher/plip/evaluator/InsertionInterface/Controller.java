package de.bioforscher.plip.evaluator.InsertionInterface;

import de.bioforscher.plip.evaluator.HibernateHandler;
import de.bioforscher.plip.evaluator.Interaction;
import de.bioforscher.plip.evaluator.Protein;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;



public class Controller {

    @FXML TextField doi, PDBid, chain;

    @FXML TextField residueNumber, aceptor, donor, type;

    @FXML TableView<Interaction> tableView;
    @FXML TableColumn<Interaction, Integer> resNumberCol, aceptorCol, donorCol;
    @FXML TableColumn<Interaction, String> interTypeCol;


    public void initialize(){
        resNumberCol.setCellValueFactory(new PropertyValueFactory<>("ResidueNumber"));
        aceptorCol.setCellValueFactory(new PropertyValueFactory<>("Aceptor"));
        donorCol.setCellValueFactory(new PropertyValueFactory<>("Donor"));
        interTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
    }


    //TODO specify interaction types

/*    @FXML
    protected void addInteractionToTable() {
//        if (type.getText() == "H-Bond"){
//            System.err.println("Type must be H-Bond, ::::::::::::::::::::::::::::::::::::");
//        }
        if (StringUtils.isNumeric(residueNumber.getText()) == false || StringUtils.isNumeric(donor.getText()) == false || StringUtils.isNumeric(aceptor.getText()) == false){
            System.err.println("Residue Number, Donor and Aceptor must be numeric!");
        }
        else {
            tableView.getItems().add(new Interaction(Integer.valueOf(residueNumber.getText()) , Integer.valueOf(donor.getText()), Integer.valueOf(aceptor.getText()), type.getText()));
        }

    }*/


    @FXML
    protected void SubmitButtonAction(){
        String doiText = doi.getText();
        String PDBidText = PDBid.getText();
        String chainText = chain.getText();


        //Interaction[] interactions = new Interaction[tableView.getItems().size()];
        //interactions = tableView.getItems().toArray();



        //System.out.println(interactions[0].getResidueNumber());

        Protein protein = new Protein();

        /*HibernateHandler handler = new HibernateHandler();
        handler.openSession();
        handler.storeProtein(protein);
        handler.closeSession();*/
    }




}
