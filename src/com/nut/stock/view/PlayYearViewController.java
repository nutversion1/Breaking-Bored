package com.nut.stock.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;

import com.nut.stock.MainApp;
import com.nut.stock.model.PlayDay;
import com.nut.stock.model.PlayMonth;
import com.nut.stock.model.PlayTimeDAO;
import com.nut.stock.model.PlayYear;
import com.nut.stock.util.DBUtil;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PlayYearViewController extends Controller {

	@FXML
	private TableView<PlayYear> playYearTable;

	@FXML
	private TableColumn<PlayYear, String> yearColumn;
	@FXML
	private TableColumn<PlayYear, Integer> costColumn;
	@FXML
	private TableColumn<PlayDay, Integer> totalPlayerColumn;
	@FXML
	private TableColumn<PlayDay, Float> totalHourColumn;
	@FXML
	private TableColumn<PlayDay, Boolean> paidColumn;

	@FXML
	private Button viewButton;




	public PlayYearViewController() {
		System.out.println("constructor");
	}


	@FXML
	private void initialize(){
		System.out.println("init");

		//
		yearColumn.setCellValueFactory(new PropertyValueFactory<PlayYear, String>("year"));
		costColumn.setCellValueFactory(new PropertyValueFactory<PlayYear, Integer>("cost"));
		totalPlayerColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Integer>("totalPlayer"));
		totalHourColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Float>("totalHour"));
		paidColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Boolean>("paid"));

		//
		viewButton.disableProperty().bind(Bindings.isEmpty(playYearTable.getSelectionModel().getSelectedItems()));

		//
		for(int i = 2017; i <= 2030; i++){
			String year = Integer.toString(i);

			PlayYear playYear = new PlayYear(year, 0, 0, 0, false);
			playYearTable.getItems().add(playYear);
		}

		//
		playYearTable.setRowFactory(row -> new TableRow<PlayYear>() {
		    @Override
		    public void updateItem(PlayYear item, boolean empty) {
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

	public void loadData(){


		//
		try {
			PlayTimeDAO.loadPlayYears(playYearTable.getItems());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//playMonthTable.getSelectionModel().selectLast();


	}

	@FXML
	private void handleViewButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayMonthView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayMonthViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData(playYearTable.getSelectionModel().getSelectedItem().getYear());

		mainApp.setOperationPane(pane);
	}


}
