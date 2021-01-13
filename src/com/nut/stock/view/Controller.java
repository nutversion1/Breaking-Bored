package com.nut.stock.view;

import com.nut.stock.MainApp;

import javafx.scene.layout.Pane;

public abstract class Controller {
	protected MainApp mainApp;
	private Pane previousOperationPane;

	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}

	public void setPreviousOperationPane(Pane previousOperationPane){
		this.previousOperationPane = previousOperationPane;
	}

	public Pane getPreviousOperationPane(){
		return previousOperationPane;
	}
}
