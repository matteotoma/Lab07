package it.polito.tdp.poweroutages;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Blackout;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	private List<Nerc> list = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Nerc> boxNerc;

    @FXML
    private TextField txtYears;

    @FXML
    private TextField txtHours;

    @FXML
    private Button btnCase;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalysis(ActionEvent event) {
    	txtResult.clear();
    	Nerc scelto = boxNerc.getValue();
    	if(scelto == null){
    		txtResult.appendText("Devi scegliere un Nerc!");
    		return;
    	}
    	String annoScelto = txtYears.getText();
    	String oreScelte = txtHours.getText();
    	int anno;
    	int ore;
    	try {
    		anno = Integer.parseInt(annoScelto);
    		ore = Integer.parseInt(oreScelte);
    	}
    	catch(Exception e){
    		txtResult.appendText("Devi inserire un numero nel campo Years e nel campo Hours!");
    		return;
    	}
    	List<Blackout> soluzione = model.trovaEventi(scelto, anno, ore);
    	if(soluzione == null) {
    		txtResult.appendText("Non c'è nessun evento legato a questo Nerc!");
    		return;
    	}
    	Collections.sort(soluzione);
    	txtResult.appendText("Tot people affected: "+model.sommaClienti(soluzione)+"\n");
    	txtResult.appendText("Tot hours of outage: "+model.calcolaOre(soluzione)+"\n");
    	StringBuilder sb = new StringBuilder();
    	for(Blackout b: soluzione) {
    		long oreDisservizio = (Duration.between(b.getDataBegin(), b.getDataEnd())).getSeconds() / 3600;
    		sb.append(String.format("%-5s ", b.getDataBegin().getYear()));
    		sb.append(String.format("%-18s ", b.getDataBegin()));
    		sb.append(String.format("%-18s ", b.getDataEnd()));
    		sb.append(String.format("%-5s ", oreDisservizio));
    		sb.append(String.format("%-10s ", b.getCustomersAffected()));
    		sb.append("\n");
    	}
    	txtResult.appendText(sb.toString());
    }

    @FXML
    void initialize() {
        assert boxNerc != null : "fx:id=\"boxNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCase != null : "fx:id=\"btnCase\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.setList();
	}
	
	public void setList() {
		list.addAll(model.getNercList());
		boxNerc.getItems().addAll(list);
	}
	
}
