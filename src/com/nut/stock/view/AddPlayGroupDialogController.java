package com.nut.stock.view;

import java.sql.SQLException;

import com.nut.stock.model.PlayGroup;
import com.nut.stock.model.PlayTimeDAO;
import com.nut.stock.util.StockUtil;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPlayGroupDialogController extends Controller {



	@FXML
	private ChoiceBox startHourChoiceBox;
	@FXML
	private ChoiceBox startMinuteChoiceBox;

	@FXML
	private TextField totalPlayerTextField;

	@FXML
	private Button okButton;

	private Stage dialogStage;
	private PlayGroupViewController playGroupViewController;

	public String year;
	public String month;
	public String day;
	public int groupNum;

	@FXML
	private void initialize(){
		for(int i = 0; i < 24; i++){
			startHourChoiceBox.getItems().add(i);
		}
		for(int i = 0; i < 60; i++){
			startMinuteChoiceBox.getItems().add(i);
		}

		//
		BooleanBinding booleanBind = startHourChoiceBox.valueProperty().isNull()
				.or(startMinuteChoiceBox.valueProperty().isNull())
                .or(totalPlayerTextField.textProperty().isEmpty());
		okButton.disableProperty().bind(booleanBind);
	}

	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}

	public void setPlayGroupViewController(PlayGroupViewController playGroupViewController){
		this.playGroupViewController = playGroupViewController;
	}



	@FXML
	private void handleAddButton() throws ClassNotFoundException, SQLException{

		if(!isInputValid()){
			return;
		}

		//
		int totalPlayer = Integer.parseInt(totalPlayerTextField.getText());

		//start time
		int startHour = (int) startHourChoiceBox.getValue();
		int startMinute = (int) startMinuteChoiceBox.getValue();
		String startTime = StockUtil.getTimeString(startHour, startMinute);

		//end time
		int endHour = startHour + 1;
		if(endHour > 23){
			endHour = 0;
		}

		int endMinute = startMinute;

		String endTime = StockUtil.getTimeString(endHour, endMinute);;

		//add players
		for(int i = 0; i < totalPlayer; i++){
			String date = year + "-" + month + "-" + day;
			int playerNum = i+1;

			PlayTimeDAO.addPlayer(date, startTime, endTime, groupNum, playerNum);
		}



		//
		PlayGroup playGroup = new PlayGroup();
		playGroup.setGroupNum(groupNum);
		playGroup.setStartTime(startTime);
		playGroup.setEndTime(endTime);
		playGroup.setTotalPlayer(totalPlayer);

		playGroupViewController.playGroupTable.getItems().add(playGroup); //must fix

		//playSuitViewController.loadData(year, month, day);

		//
		dialogStage.close();

	}

	private boolean isInputValid(){
		String errorMessage = "";

		try{
			Integer.parseInt(totalPlayerTextField.getText());
		}catch(NumberFormatException e){
			errorMessage += "ต้องใส่จำนวนผู้เล่นเป็นตัวเลขเท่านั้น!\n";
		}

		/*if(Integer.parseInt(totalPlayerTextField.getText()) <= 0){
			errorMessage += "ต้องใส่จำนวนผู้เล่นมากกว่า 0!\n";
		}*/

		if(errorMessage.length() == 0){
			System.out.println("true");
			return true;
		}else{
			System.out.println("false");
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("กรอกผิด");
			alert.setHeaderText("กุณากรอกใหม่");
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
	}

	public void loadData(String year, String month, String day, int groupNum){
		this.year = year;
		this.month = month;
		this.day = day;
		this.groupNum = groupNum;
	}

}
