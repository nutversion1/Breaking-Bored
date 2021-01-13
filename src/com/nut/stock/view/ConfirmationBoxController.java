package com.nut.stock.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmationBoxController extends Controller{

	@FXML
	private Label messageLabel;

	@FXML
	private Button okButton;
	@FXML
	private Button cancelButton;

	private Stage dialogStage;
	private boolean okClicked;

	@FXML
	private void initialize(){
		okClicked = false;
	}

	@FXML
	private void handleOkButton(){
		dialogStage.close();

		okClicked = true;
	}

	@FXML
	private void handleCancelButton(){
		dialogStage.close();

		okClicked = false;
	}

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	public boolean isOkClicked(){
		return okClicked;
	}

	public void setMessage(String message){
		messageLabel.setText(message);
	}

}
