package com.nut.stock.view;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nut.stock.MainApp;
import com.nut.stock.model.Player;
import com.nut.stock.model.PlayTimeDAO;
import com.nut.stock.util.StockUtil;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class PlayerViewController extends Controller {

	public static final int COST = 25;
	public static final int PLAY_ALL_DAY_COST = 125;

	public static final int HELP_MINUTE = 5;

	@FXML
	private ChoiceBox<Integer> startHourChoiceBox;
	@FXML
	private ChoiceBox<Integer> startMinuteChoiceBox;
	@FXML
	private ChoiceBox<Integer> endHourChoiceBox;
	@FXML
	private ChoiceBox<Integer> endMinuteChoiceBox;

	@FXML
	private TableView<Player> playerTable;

	@FXML
	private TableColumn<Player, String> nameColumn;
	@FXML
	private TableColumn<Player, Integer> playerNumColumn;
	@FXML
	private TableColumn<Player, Player> playAllDayColumn;
	@FXML
	private TableColumn<Player, Player> earlyQuitColumn;
	@FXML
	private TableColumn<Player, Integer> discountColumn;
	@FXML
	private TableColumn<Player, Integer> costColumn;
	@FXML
	private TableColumn<Player, Float> durationColumn;
	@FXML
	private TableColumn<Player, Float> totalHourColumn;
	@FXML
	private TableColumn<Player, Player> paidColumn;

	@FXML
	private Label totalCostLabel;
	@FXML
	private Label moneyLeftLabel;

	@FXML
	private Button deleteButton;

	@FXML
	private Label titleLabel;

	public String year, month, day;
	public int groupNum;

	public String startTime, endTime;

	@FXML
	private void initialize(){
		//
		for(int i = 0; i < 24; i++){
			startHourChoiceBox.getItems().add(i);
		}
		for(int i = 0; i < 60; i++){
			startMinuteChoiceBox.getItems().add(i);
		}

		for(int i = 0; i < 24; i++){
			endHourChoiceBox.getItems().add(i);
		}
		for(int i = 0; i < 60; i++){
			endMinuteChoiceBox.getItems().add(i);
		}




		//
		playerTable.setEditable(true);

		//
		playerNumColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("playerNum"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		costColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("cost"));
		durationColumn.setCellValueFactory(new PropertyValueFactory<Player, Float>("duration"));
		totalHourColumn.setCellValueFactory(new PropertyValueFactory<Player, Float>("totalHour"));

		//
		discountColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("discount"));
		discountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		discountColumn.setOnEditCommit((CellEditEvent<Player,Integer> event) -> {
            TablePosition<Player,Integer> pos = event.getTablePosition();

            int newValue = event.getNewValue();

            int row = pos.getRow();
            Player player = event.getTableView().getItems().get(row);

            player.setDiscount(newValue);

            calculate();



        });

		//
		playAllDayColumn.setCellValueFactory(cellData -> {
            Player cellValue = cellData.getValue();

            return new SimpleObjectProperty(cellValue);
        });

		playAllDayColumn.setCellFactory(new Callback<TableColumn<Player, Player>, TableCell<Player, Player>>() {

			@Override
			public TableCell<Player, Player> call(TableColumn<Player, Player> param) {
				//System.out.println(param);
				// TODO Auto-generated method stub
				return new PlayAllDayCell();

			}
		});

		//
		earlyQuitColumn.setCellValueFactory(cellData -> {
            Player cellValue = cellData.getValue();

            return new SimpleObjectProperty(cellValue);
        });


		earlyQuitColumn.setCellFactory(new Callback<TableColumn<Player, Player>, TableCell<Player, Player>>() {

			@Override
			public TableCell<Player, Player> call(TableColumn<Player, Player> param) {
				//System.out.println(param);
				// TODO Auto-generated method stub
				return new EarlyQuitCell();

			}
		});



		//
		paidColumn.setCellValueFactory(cellData -> {
            Player cellValue = cellData.getValue();

            return new SimpleObjectProperty(cellValue);
        });

		paidColumn.setCellFactory(new Callback<TableColumn<Player, Player>, TableCell<Player, Player>>() {

			@Override
			public TableCell<Player, Player> call(TableColumn<Player, Player> param) {
				//System.out.println(param);
				// TODO Auto-generated method stub
				return new PaidCell();

			}
		});

		//binding button
	    BooleanBinding booleanBinding1 = Bindings.size(playerTable.getItems()).greaterThan(1).not();
	    BooleanBinding booleanBinding2 = Bindings.isEmpty(playerTable.getSelectionModel().getSelectedItems());
	    deleteButton.disableProperty().bind(Bindings.or(booleanBinding1, booleanBinding2));

		//
		Platform.runLater(()->{
			System.out.println("run later");
			//
			startHourChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				for(Player player : playerTable.getItems()){

					player.setStartTime(StockUtil.getTimeString((int)startHourChoiceBox.getValue(), (int)startMinuteChoiceBox.getValue()));


				}

				calculate();
			});
			startMinuteChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				for(Player player : playerTable.getItems()){

					player.setStartTime(StockUtil.getTimeString((int)startHourChoiceBox.getValue(), (int)startMinuteChoiceBox.getValue()));

				}

				calculate();
			});

			//
			endHourChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				for(Player player : playerTable.getItems()){
					if(!player.getEarlyQuit()){
						player.setEndTime(StockUtil.getTimeString((int)endHourChoiceBox.getValue(), (int)endMinuteChoiceBox.getValue()));
					}

				}

				calculate();
			});
			endMinuteChoiceBox.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
				for(Player player : playerTable.getItems()){
					if(!player.getEarlyQuit()){
						player.setEndTime(StockUtil.getTimeString((int)endHourChoiceBox.getValue(), (int)endMinuteChoiceBox.getValue()));
					}
				}

				calculate();
			});

			//
			calculate();
		});



	}

	public void loadData(String year, String month, String day, int groupNum, String startTime, String endTime){
		this.year = year;
		this.month = month;
		this.day = day;
		this.groupNum = groupNum;

		this.startTime = startTime;
		this.endTime = endTime;

		//
		try {
			PlayTimeDAO.loadPlayers(year, month, day, groupNum, playerTable.getItems());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(endTime);
		//
		startHourChoiceBox.setValue(StockUtil.getHourInt(startTime));
		startMinuteChoiceBox.setValue(StockUtil.getMinuteInt(startTime));
		endHourChoiceBox.setValue(StockUtil.getHourInt(endTime));
		endMinuteChoiceBox.setValue(StockUtil.getMinuteInt(endTime));

		//
		titleLabel.setText("กลุ่มที่ " + groupNum + " วันที่ "+ day + " เดือน " + month + " ปี " + year);

		//
		calculate();
	}

	private void calculate(){
		//System.out.println("calculate");

		//
		int totalCost = 0;
		int moneyLeft = 0;

		for(Player player: playerTable.getItems()){
			player.setDuration(calculateDuration(StockUtil.getHourInt(player.getStartTime()),
					StockUtil.getMinuteInt(player.getStartTime()),
					StockUtil.getHourInt(player.getEndTime()),
					StockUtil.getMinuteInt(player.getEndTime())));

			player.setTotalHour(calculateHour(StockUtil.getHourInt(player.getStartTime()),
					StockUtil.getMinuteInt(player.getStartTime()),
					StockUtil.getHourInt(player.getEndTime()),
					StockUtil.getMinuteInt(player.getEndTime()),
					HELP_MINUTE));

			if(player.getPlayAllDay()){
				player.setCost(PLAY_ALL_DAY_COST);
			}else{
				player.setCost(calculateCost(player.getTotalHour().intValue(), player.getDiscount()));
			}


			totalCost += player.getCost();

			if(!player.getPaid()){
				moneyLeft += player.getCost();
			}
		}


		totalCostLabel.setText(Integer.toString(totalCost));
		moneyLeftLabel.setText(Integer.toString(moneyLeft));

		//
		updateDatabase();

	}

	private float calculateDuration(int startHours, int startMinutes, int endHours, int endMinutes){
		//set time 1
		String time1 = "2018-01-01 " + startHours + ":" + startMinutes;

		//set time 2
		String day = "01";
		if(startHours > endHours){
			day = "02";
		}
		if(startHours == endHours && endMinutes < startMinutes){
			day = "02";
		}
		String time2 = "2018-01-"+day +" " + endHours+ ":" + endMinutes;

		//calculate time difference
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = null;
		try {
			date1 = format.parse(time1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = null;
		try {
			date2 = format.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long difference = date2.getTime() - date1.getTime();
		int totalSecs = (int)(difference/1000);

		//set raw hours & minutes
		int rawHours = totalSecs / 3600;
		int rawMinutes = (totalSecs % 3600) / 60;

		float rawDuration = 0f + rawHours + (rawMinutes / 100f);

		return rawDuration;
	}



	private int calculateHour(int startHours, int startMinutes, int endHours, int endMinutes, int helpMinutes){
		//set time 1
		String time1 = "2018-01-01 " + startHours + ":" + startMinutes;

		//set time 2
		String day = "01";
		if(startHours > endHours){
			day = "02";
		}
		if(startHours == endHours && endMinutes < startMinutes){
			day = "02";
		}
		String time2 = "2018-01-"+day +" " + endHours+ ":" + endMinutes;

		//calculate time difference
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date1 = null;
		try {
			date1 = format.parse(time1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date date2 = null;
		try {
			date2 = format.parse(time2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long difference = date2.getTime() - date1.getTime();
		int totalSecs = (int)(difference/1000);

		//set raw hours & minutes
		int rawHours = totalSecs / 3600;
		int rawMinutes = (totalSecs % 3600) / 60;

		//set phase hours & minutes
		int phaseHours = rawHours;
		int phaseMinutes = rawMinutes;

		if(rawMinutes > 0){
			if(rawHours > 0 && rawMinutes <= 0+helpMinutes){

			}else{
				phaseHours += 1;
			}
		}

		return phaseHours;
	}

	private int calculateCost(int hour, int discount){
		//set cost
		int cost = hour * COST;

		cost -= discount;

		return cost;
	}

	private void updateDatabase(){
		//System.out.println("update database");

		for(Player player : playerTable.getItems()){
			String date = year + "-" + month + "-" + day;

			try {
				PlayTimeDAO.updatePlayer(date, groupNum, player.getPlayerNum(), player.getPlayAllDay(), player.getEarlyQuit(),
						player.getStartTime(), player.getEndTime(), player.getTotalHour(), player.getDiscount(), player.getCost(), player.getPaid());
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void handleBackButton() throws IOException{
		FXMLLoader loader = new  FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PlayGroupView.fxml"));
		AnchorPane pane = (AnchorPane)loader.load();

		PlayGroupViewController controller  = loader.getController();
		controller.setMainApp(mainApp);
		controller.loadData(year, month, day);

		mainApp.setOperationPane(pane);

		System.out.println("back");
	}

	@FXML
	private void handleAddButton() throws ClassNotFoundException, SQLException{
		//
		String date = year + "-" + month + "-" + day;

		int lastPlayerNum = 0;
		if(playerTable.getItems().size() != 0){
			lastPlayerNum = playerTable.getItems().get(playerTable.getItems().size()-1).getPlayerNum();
		}

		//
		PlayTimeDAO.addPlayer(date, startTime, endTime, groupNum, lastPlayerNum + 1);

		Player player = new Player();
		player.setStartTime(startTime);
		player.setEndTime(endTime);
		player.setPlayerNum(lastPlayerNum + 1);
		playerTable.getItems().add(player);

		//
		calculate();
	}

	@FXML
	private void handleDeleteButton() throws ClassNotFoundException, SQLException{
		String date = year + "-" + month + "-" + day;

		PlayTimeDAO.deletePlayer(date, groupNum, playerTable.getSelectionModel().getSelectedItem().getPlayerNum());

		playerTable.getItems().remove(playerTable.getSelectionModel().getSelectedItem());

		//
		calculate();
	}

	class PlayAllDayCell extends TableCell<Player, Player> {
        HBox hbox = new HBox();
        CheckBox checkBox = new CheckBox();
        Player lastItem;

        public PlayAllDayCell() {
            super();

            hbox.getChildren().addAll(checkBox);

            checkBox.setOnAction(e -> {
            	if (e.getSource() instanceof CheckBox) {
		            CheckBox checkBox = (CheckBox) e.getSource();

		            lastItem.setPlayAllDay(checkBox.isSelected());

    				calculate();
		        }
            });
        }


        @Override
        protected void updateItem(Player item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                setGraphic(hbox);
                checkBox.setSelected(lastItem.getPlayAllDay());
            }


        }

    }

	class PaidCell extends TableCell<Player, Player> {
        HBox hbox = new HBox();
        CheckBox checkBox = new CheckBox();
        Player lastItem;

        public PaidCell() {
            super();

            hbox.getChildren().addAll(checkBox);


            checkBox.setOnAction(new EventHandler<ActionEvent>() {
    			@Override
    		    public void handle(ActionEvent event) {
    		        if (event.getSource() instanceof CheckBox) {
    		            CheckBox checkBox = (CheckBox) event.getSource();

    		            System.out.println(lastItem.getPlayerNum() + " : " + event.getSource());

    		            lastItem.setPaid(checkBox.isSelected());
        				calculate();
    		        }
    		    }
            });


        }


        @Override
        protected void updateItem(Player item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                setGraphic(hbox);
                checkBox.setSelected(lastItem.getPaid());
            }


        }

    }



	class EarlyQuitCell extends TableCell<Player, Player> {
        HBox hbox = new HBox();
        CheckBox checkBox = new CheckBox();
        Pane pane = new Pane();
        ChoiceBox<Integer> hourChoiceBox = new ChoiceBox<Integer>();
        ChoiceBox<Integer> minuteChoiceBox = new ChoiceBox<Integer>();
        Player lastItem;

        public EarlyQuitCell() {
            super();

            //System.out.println("constructor");

            hbox.getChildren().addAll(checkBox, hourChoiceBox , minuteChoiceBox);
            HBox.setHgrow(pane, Priority.ALWAYS);

            for(int i = 0; i < 24; i++){
            	hourChoiceBox.getItems().add(i);
    		}
    		for(int i = 0; i < 60; i++){
    			minuteChoiceBox.getItems().add(i);
    		}

            //
            checkBox.setOnAction(e -> {
            	//System.out.println("check box action");

            	if (e.getSource() instanceof CheckBox) {
		            CheckBox checkBox = (CheckBox) e.getSource();

		            //System.out.println(lastItem.getPlayerNum() + " : " + hourChoiceBox);

		            hourChoiceBox.setDisable(!checkBox.isSelected());
					minuteChoiceBox.setDisable(!checkBox.isSelected());

					lastItem.setEarlyQuit(checkBox.isSelected());

					if(lastItem.getEarlyQuit()){
						lastItem.setEndTime(StockUtil.getTimeString(hourChoiceBox.getValue(), minuteChoiceBox.getValue()));
					}else{
						lastItem.setEndTime(StockUtil.getTimeString(endHourChoiceBox.getValue(), endMinuteChoiceBox.getValue()));

						hourChoiceBox.setValue(StockUtil.getHourInt(startTime));
			            minuteChoiceBox.setValue(StockUtil.getMinuteInt(startTime));
					}

					calculate();


            	}


            });






            //
            hourChoiceBox.setDisable(true);
            minuteChoiceBox.setDisable(true);

            //
            hourChoiceBox.setOnAction(e -> {
            	if(lastItem.getEarlyQuit()){
            		if(hourChoiceBox.getValue() != null && minuteChoiceBox.getValue() != null){
    					lastItem.setEndTime(StockUtil.getTimeString(hourChoiceBox.getValue(), minuteChoiceBox.getValue()));
    					calculate();
            		}

				}


            });

            minuteChoiceBox.setOnAction(e -> {
            	if(lastItem.getEarlyQuit()){
            		if(hourChoiceBox.getValue() != null && minuteChoiceBox.getValue() != null){
            			lastItem.setEndTime(StockUtil.getTimeString(hourChoiceBox.getValue(), minuteChoiceBox.getValue()));
    					calculate();
            		}

				}




            });





        }


        @Override
        protected void updateItem(Player item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                setGraphic(hbox);
                checkBox.setSelected(lastItem.getEarlyQuit());
                if(lastItem.getEarlyQuit()){

                	//
                	hourChoiceBox.setDisable(false);
                 	minuteChoiceBox.setDisable(false);

                 	//
                  	hourChoiceBox.setValue(StockUtil.getHourInt(lastItem.getEndTime()));
                    minuteChoiceBox.setValue(StockUtil.getMinuteInt(lastItem.getEndTime()));

                }else{
                	//
                	hourChoiceBox.setDisable(true);
                 	minuteChoiceBox.setDisable(true);

                 	//
                 	hourChoiceBox.setValue(StockUtil.getHourInt(startTime));
                    minuteChoiceBox.setValue(StockUtil.getMinuteInt(startTime));
                }





            }


        }

    }

}
