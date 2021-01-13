package com.nut.stock.view;

import java.io.IOException;
import java.sql.SQLException;

import com.nut.stock.MainApp;
import com.nut.stock.util.DBUtil;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController extends Controller {
	@FXML
	private ListView pageList;

	@FXML
	public AnchorPane operationPane;

	@FXML
	private void initialize(){
		System.out.println("initialize");

		Platform.runLater(() -> {
			System.out.println("run later");

			//pageList.getItems().add("ขายสินค้า");
			//pageList.getItems().add("คลังสินค้า");
			//pageList.getItems().add("ค่าเล่น");
			pageList.getItems().add("เวลาเล่น");

			pageList.getSelectionModel().select(0);
			showOperationView((String)pageList.getSelectionModel().selectedItemProperty().getValue());

			pageList.getSelectionModel().selectedItemProperty().addListener(
					(observable, oldValue, newValue) -> showOperationView((String)pageList.getSelectionModel().selectedItemProperty().getValue()));
		});


	}

	public void showOperationView(String value){

		if(operationPane.getChildren().size() > 0){
			operationPane.getChildren().clear();
		}

		String viewName = "";
		if(value == "ขายสินค้า"){
			viewName = "view/Sell.fxml";
		}else if(value == "คลังสินค้า"){
			viewName = "view/Stock.fxml";
		}else if(value == "ค่าเล่น"){
			viewName = "view/PlayCost.fxml";
		}else if(value == "เวลาเล่น"){
			//viewName = "view/PlayTime.fxml";
			viewName = "view/PlayYearView.fxml";

			//
			try{
				FXMLLoader loader = new  FXMLLoader();
				loader.setLocation(MainApp.class.getResource(viewName));
				AnchorPane view = (AnchorPane)loader.load();

				operationPane.getChildren().add(view);

				PlayYearViewController controller = loader.getController();
				controller.setMainApp(mainApp);
				controller.loadData();
			}catch(IOException e){
				e.printStackTrace();
			}
		}




	}


}
