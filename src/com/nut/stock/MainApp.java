package com.nut.stock;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.nut.stock.util.DBUtil;
import com.nut.stock.view.Controller;
import com.nut.stock.view.MainController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	public Stage primaryStage;

	private MainController mainController;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		//create main pane
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/Main.fxml"));
		AnchorPane mainPain = (AnchorPane)loader.load();
		MainController controller = loader.getController();
		controller.setMainApp(this);

		mainController = controller;

		//create scene
		Scene mainScene = new Scene(mainPain);

		//set stage
		primaryStage.setScene(mainScene);
		primaryStage.setTitle("BreakingBoredApp");
		primaryStage.show();


		//connect play time database
		try {
			DBUtil.dbConnect();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//setup close
		primaryStage.setOnCloseRequest(e ->{
			e.consume();
			System.out.println("close");

			//disconnect play time database
			try {
				DBUtil.dbDisconnect();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//close program
			primaryStage.close();
		});




	}

	public Controller getMainController(){
		return mainController;
	}

	public void setOperationPane(Pane operationPane){
		if(mainController.operationPane.getChildren().size() > 0){
			mainController.operationPane.getChildren().clear();
		}

		mainController.operationPane.getChildren().add(operationPane);
	}


	public Stage getPrimaryStage(){
		return primaryStage;
	}
}
