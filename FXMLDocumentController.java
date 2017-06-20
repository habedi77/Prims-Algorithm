/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dMathProject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

/**
 *
 * @author Habedi
 */
public class FXMLDocumentController implements Initializable
{

	@FXML
	private Button generateButton;
	@FXML
	private Slider noOfNodesSlider;
	@FXML
	private ToggleGroup toggleGroupGraph;
	@FXML
	private ToggleGroup toggleGroupEdge;
	@FXML
	private RadioButton graphRadio1;
	@FXML
	private RadioButton graphRadio2;
	@FXML
	private RadioButton edgeRadio1;
	@FXML
	private RadioButton edgeRadio2;

	private EdgesMode eMode;
	private GraphMode gMode;

	@FXML
	public void startGeneration(ActionEvent ev)
	{
		if (toggleGroupGraph.getSelectedToggle().getUserData().equals("FULL"))
			gMode = GraphMode.COMPLETE;
		else
			gMode = GraphMode.CONNECTED;

		if (toggleGroupEdge.getSelectedToggle().getUserData().equals("SIMPLE"))
			eMode = EdgesMode.SIMPLE_DISTANCE;
		else
			eMode = EdgesMode.DISTANCE_W_MODIFIER;
		
		
		Main.generate(gMode, eMode, (int) noOfNodesSlider.getValue() );

	}

	@Override
	public void initialize(URL url, ResourceBundle rb)
	{
		System.out.println("initializing FXML");

		graphRadio1.setUserData("FULL");
		graphRadio2.setUserData("CONNECTED");

		edgeRadio1.setUserData("SIMPLE");
		edgeRadio2.setUserData("ADV");
	}

}




