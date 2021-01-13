package com.nut.stock.view;

import java.io.IOException;
import java.sql.SQLException;
import java.time.YearMonth;

import com.nut.stock.MainApp;
import com.nut.stock.model.PlayDay;
import com.nut.stock.model.PlayMonth;
import com.nut.stock.model.PlayTimeDAO;
import com.nut.stock.util.StockUtil;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class PlayDayViewController extends Controller {

	@FXML
	private TableView<PlayDay> playDayTable;

	@FXML
	private TableColumn<PlayDay, String> dayColumn;
	@FXML
	private TableColumn<PlayDay, String> dayOfWeekColumn;
	@FXML
	private TableColumn<PlayDay, Integer> costColumn;
	@FXML
	private TableColumn<PlayDay, Integer> totalGroupColumn;
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
	public String month;

	@FXML
	private void initialize(){
		//
		dayColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, String>("day"));
		dayOfWeekColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, String>("dayOfWeek"));
		costColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Integer>("cost"));
		totalGroupColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Integer>("totalGroup"));
		totalPlayerColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Integer>("totalPlayer"));
		totalHourColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Float>("totalHour"));
		paidColumn.setCellValueFactory(new PropertyValueFactory<PlayDay, Boolean>("paid"));

		//
		viewButton.disableProperty().bind(Bindings.isEmpty(playDayTable.getSelectionModel().getSelectedItems()));

		//
		playDayTable.setRowFactory(row -> new TableRow<PlayDay>() {
		    @Override
		    public void updateItem(PlayDay item, boolean empty) {
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

	public void loadData(String year, String month){
		this.year = year;
		this.month = month;

		//
		int yearInt = Integer.parseInt(year);
		int monthInt = StockUtil.getMonthInt(month);

		YearMonth yearMonthObject = YearMonth.of(yearInt, monthInt);
		int daysInMonth = yearMonthObject.lengthOfMonth();

		for(int i = 1; i <= daysInMonth; i++){
			String day = "";
			if(i < 10){
				day = "0"+i;
			}else{
				day = ""+i;
			}

			PlayDay playDay = new PlayDay(day, "", 0, 0, 0, 0, false);
			playDayTable.getItems().add(playDay);

			//
			String date = year+"-"+month+"-"+playDay.getDay();
			playDay.setDayOfWeek(StockUtil.getDayOfWeek(date));
		}

		//
		try {
			PlayTimeDAO.loadPlayDays(year, month, playDayTable.getItems());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//
		titleLabel.setText("เดือน " + month + " ปี " + year);


	}

	@FXML
	private void handleViewButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayGroupView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayGroupViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData(year, month, playDayTable.getSelectionModel().getSelectedItem().getDay());

		mainApp.setOperationPane(pane);
	}

	@FXML
	private void handleBackButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayMonthView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayMonthViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData(year);

		mainApp.setOperationPane(pane);

		//System.out.println("back");
	}

}
