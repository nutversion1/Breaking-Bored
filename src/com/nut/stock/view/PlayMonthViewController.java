package com.nut.stock.view;

import java.io.IOException;
import java.sql.SQLException;

import com.nut.stock.MainApp;
import com.nut.stock.model.PlayDay;
import com.nut.stock.model.PlayMonth;
import com.nut.stock.model.PlayTimeDAO;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class PlayMonthViewController extends Controller{

	@FXML
	private TableView<PlayMonth> playMonthTable;

	@FXML
	private TableColumn<PlayMonth, String> monthColumn;
	@FXML
	private TableColumn<PlayMonth, Integer> costColumn;
	@FXML
	private TableColumn<PlayDay, Integer> totalPlayerColumn;
	@FXML
	private TableColumn<PlayDay, Float> totalHourColumn;
	@FXML
	private TableColumn<PlayDay, Boolean> paidColumn;

	@FXML
	private Button viewButton;

	@FXML
	private Label titleLabel;

	public String year;

	@FXML
	private void initialize(){
		//
		monthColumn.setCellValueFactory(new PropertyValueFactory<PlayMonth, String>("month"));
		costColumn.setCellValueFactory(new PropertyValueFactory<PlayMonth, Integer>("cost"));
		totalPlayerColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Integer>("totalPlayer"));
		totalHourColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Float>("totalHour"));
		paidColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Boolean>("paid"));

		//
		viewButton.disableProperty().bind(Bindings.isEmpty(playMonthTable.getSelectionModel().getSelectedItems()));

		//
		for(int i = 1; i <= 12; i++){
			String month = "";
			if(i < 10){
				month = "0"+i;
			}else{
				month = ""+i;
			}

			PlayMonth playMonth = new PlayMonth(month, 0, 0, 0, false);
			playMonthTable.getItems().add(playMonth);
		}

		//
		playMonthTable.setRowFactory(row -> new TableRow<PlayMonth>() {
		    @Override
		    public void updateItem(PlayMonth item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.getTotalPlayer() == 0) {
		            setStyle("-fx-background-color: gray;");
		        } else {
		            setStyle("-fx-background-color: lightgreen;");




		        }
		    }
		});


	}

	public void loadData(String year){
		this.year = year;

		try {
			PlayTimeDAO.loadPlayMonths(year, playMonthTable.getItems());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//playMonthTable.getSelectionModel().selectLast();

		//
		titleLabel.setText("Ле " + year);
	}

	@FXML
	private void handleViewButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayDayView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayDayViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData(year, playMonthTable.getSelectionModel().getSelectedItem().getMonth());

		mainApp.setOperationPane(pane);
	}

	@FXML
	private void handleBackButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayYearView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayYearViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData();

		mainApp.setOperationPane(pane);

		//System.out.println("back");
	}




}
