package de.bioforscher.plip.evaluator.InsertionInterface;

import de.bioforscher.plip.evaluator.EvaluatorModule;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    TextField doi;

    @FXML
    TextField PDBid;

    @FXML
    TextField chain;

    @FXML
    protected void SubmitButtonAction(){
        String doiText = doi.getText();
        String PDBidText = PDBid.getText();
        String chainText = chain.getText();

        //new de.bioforscher.plip.evaluator.JsonHandler().makeJson(doiText,PDBidText,chainText,interactions);
    }



}
