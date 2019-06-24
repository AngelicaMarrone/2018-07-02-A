package it.polito.tdp.extflightdelays;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.extflightdelays.model.Airport;
import it.polito.tdp.extflightdelays.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ExtFlightDelaysController {

	private Model model;
	private Airport scelto;
	
	public void setModel(Model model) {
		this.model = model;
		
	}

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="distanzaMinima"
    private TextField distanzaMinima; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizza"
    private Button btnAnalizza; // Value injected by FXMLLoader

    @FXML // fx:id="cmbBoxAeroportoPartenza"
    private ComboBox<Airport> cmbBoxAeroportoPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnAeroportiConnessi"
    private Button btnAeroportiConnessi; // Value injected by FXMLLoader

    @FXML // fx:id="numeroVoliTxtInput"
    private TextField numeroVoliTxtInput; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaItinerario"
    private Button btnCercaItinerario; // Value injected by FXMLLoader

    @FXML
    void doAnalizzaAeroporti(ActionEvent event) {
    	double media = 0;
    	
    	try {
    		media = Double.parseDouble(distanzaMinima.getText().trim());
    	} catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero reale come distanza media minima!");
    		distanzaMinima.clear();
    		return ;
    	}
    	
    	model.creaGrafo(media);
    	cmbBoxAeroportoPartenza.getItems().clear();
    	cmbBoxAeroportoPartenza.getItems().addAll(model.getAereoporti());
    }

    @FXML
    void doCalcolaAeroportiConnessi(ActionEvent event) {
    	scelto = cmbBoxAeroportoPartenza.getValue();
    	
    	if (scelto == null) {
    		txtResult.setText("Devi selezionare un aereoporto dal menù a tendina!");
    		return ;
    	}
    	
    	for (Airport a: model.calcolaConnessi(scelto))
    		txtResult.appendText(a.getAirportName()+"\n");
    }

    @FXML
    void doCercaItinerario(ActionEvent event) {
    	double disponibili = 0;
    	
    	try {
    		disponibili = Double.parseDouble(numeroVoliTxtInput.getText().trim());
    	} catch(NullPointerException npe) {
    		txtResult.setText("Inserisci un numero reale come numero totale di miglia che si è disponibili a percorrere!");
    		distanzaMinima.clear();
    		return ;
    	}
    	
    	List<Airport> itinerario = model.calcolaItinerario(disponibili, scelto);
    	txtResult.appendText("\n\n\nDistanza Percorsa: "+model.distanzaPercorsa(itinerario)+" miglia\n\n");
    	for (Airport a: itinerario)
    		txtResult.appendText(a.getAirportName()+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert distanzaMinima != null : "fx:id=\"distanzaMinima\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAnalizza != null : "fx:id=\"btnAnalizza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert cmbBoxAeroportoPartenza != null : "fx:id=\"cmbBoxAeroportoPartenza\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnAeroportiConnessi != null : "fx:id=\"btnAeroportiConnessi\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert numeroVoliTxtInput != null : "fx:id=\"numeroVoliTxtInput\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";
        assert btnCercaItinerario != null : "fx:id=\"btnCercaItinerario\" was not injected: check your FXML file 'ExtFlightDelays.fxml'.";

    }
    
}