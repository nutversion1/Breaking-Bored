package com.nut.stock.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;

import com.nut.stock.MainApp;
import com.nut.stock.model.PlayDay;
import com.nut.stock.model.PlayMonth;
import com.nut.stock.model.PlayGroup;
import com.nut.stock.model.PlayTimeDAO;
import com.nut.stock.util.StockUtil;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlayGroupViewController extends Controller{

	@FXML
	public TableView<PlayGroup> playGroupTable;

	@FXML
	private TableColumn<PlayGroup, String> timeColumn;
	@FXML
	private TableColumn<PlayGroup, Integer> groupColumn;
	@FXML
	private TableColumn<PlayGroup, Integer> costColumn;
	@FXML
	private TableColumn<PlayGroup, Integer> totalPlayerColumn;
	@FXML
	private TableColumn<PlayGroup, Float> totalHourColumn;
	@FXML
	private TableColumn<PlayGroup, Boolean> paidColumn;

	@FXML
	private Button viewButton;
	@FXML
	private Button deleteButton;

	@FXML
	private Label titleLabel;

	public String year;
	public String month;
	public String day;

	@FXML
	private void initialize(){
		//
		timeColumn.setCellValueFactory(new PropertyValueFactory<PlayGroup, String>("startTime"));
		groupColumn.setCellValueFactory(new PropertyValueFactory<PlayGroup, Integer>("groupNum"));
		costColumn.setCellValueFactory(new PropertyValueFactory<PlayGroup, Integer>("cost"));
		totalPlayerColumn.setCellValueFactory(new PropertyValueFactory<PlayGroup, Integer>("totalPlayer"));
		totalHourColumn.setCellValueFactory(new PropertyValueFactory<PlayGroup, Float>("totalHour"));
		paidColumn.setCellValueFactory(new PropertyValueFactory<PlayGroup, Boolean>("paid"));

		//System.out.println(playGroupTable);

		//
		viewButton.disableProperty().bind(Bindings.isEmpty(playGroupTable.getSelectionModel().getSelectedItems()));
		deleteButton.disableProperty().bind(Bindings.isEmpty(playGroupTable.getSelectionModel().getSelectedItems()));

		//
		playGroupTable.setRowFactory(row -> new TableRow<PlayGroup>() {
		    @Override
		    public void updateItem(PlayGroup item, boolean empty) {
		        super.updateItem(item, empty) ;
		        if (item == null) {
		            setStyle("");
		        } else if (item.paid().get()) {
		            setStyle("-fx-background-color: lightblue;");
		        } else {
		        	setStyle("-fx-background-color: lightgray;");
		        }
		    }
		});

	}

	public void loadData(String year, String month, String day){
		this.year = year;
		this.month = month;
		this.day = day;

		//
		try {
			PlayTimeDAO.loadPlayGroups(year, month, day, playGroupTable.getItems());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//playDayTable.getSelectionModel().selectLast();

		//
		titleLabel.setText("วันที่ "+ day + " เดือน " + month + " ปี " + year);
	}

	@FXML
	private void handleViewButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayerView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayerViewController controller  = loader.getController();
		System.out.println(controller);
		controller.setMainApp(mainApp);
		controller.loadData(year, month, day,
				playGroupTable.getSelectionModel().getSelectedItem().getGroupNum(),
				playGroupTable.getSelectionModel().getSelectedItem().getStartTime(), playGroupTable.getSelectionModel().getSelectedItem().getEndTime());


		mainApp.setOperationPane(pane);
	}

	@FXML
	private void handleBackButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayDayView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayDayViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData(year, month);


		mainApp.setOperationPane(pane);


	}

	@FXML
	private void handleAddButton() throws IOException{

		//show "add play group" dialog
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/AddPlayGroupDialog.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);

		AddPlayGroupDialogController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.setDialogStage(dialogStage);
		controller.setPlayGroupViewController(this);

		int lastGroupNum = 0;
		if(playGroupTable.getItems().size() != 0){
			lastGroupNum = playGroupTable.getItems().get(playGroupTable.getItems().size()-1).getGroupNum();
		}
		controller.loadData(year, month, day, lastGroupNum + 1);

		Scene scene = new Scene(pane);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();
	}

	@FXML
	private void handleDeleteButton() throws ClassNotFoundException, SQLException, IOException {
		//
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/ConfirmationBox.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.APPLICATION_MODAL);

		ConfirmationBoxController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.setDialogStage(dialogStage);
		controller.setMessage("ลบ ?");

		Scene scene = new Scene(pane);
		dialogStage.setScene(scene);
		dialogStage.showAndWait();

		//
		if(controller.isOkClicked()){
			String date = year + "-" + month + "-" + day;

			PlayTimeDAO.deletePlayGroup(date,
					playGroupTable.getSelectionModel().getSelectedItem().getGroupNum());

			playGroupTable.getItems().remove(playGroupTable.getSelectionModel().getSelectedItem());
		}

	}

}
