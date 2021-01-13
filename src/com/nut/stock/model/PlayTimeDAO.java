package com.nut.stock.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;


import com.nut.stock.util.DBUtil;
import com.nut.stock.util.StockUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.util.Pair;

public class PlayTimeDAO {

	//------------------------ select --------------------------------------------

	public static void loadPlayYears(ObservableList<PlayYear> playYears) throws ClassNotFoundException, SQLException{
		String selectStmt =
				"select " +
				"strftime('%Y', date) as year, " +
				"count(distinct date) as total_date, " +
				"sum(hour) as total_hour, " +
				"count(*) as total_player, " +
				"sum(cost) as total_cost, " +
				"min(paid) as all_paid " +

				"from play " +
				"group by year " +
				"order by year ";

        ResultSet result = DBUtil.dbExecuteQuery(selectStmt);

        try {
            while(result.next()){
            	String year = result.getString("year");
            	int cost = result.getInt("total_cost");
            	int totalPlayer = result.getInt("total_player");
            	float totalHour = result.getFloat("total_hour");
            	boolean paid = result.getBoolean("all_paid");

            	for(PlayYear playYear : playYears){
            		if(playYear.getYear().equals(year)){
            			playYear.setCost(cost);
            			playYear.setTotalPlayer(totalPlayer);
            			playYear.setTotalHour(totalHour);
            			playYear.setPaid(paid);
            			break;
            		}
            	}
    		}

        } catch (SQLException e) {
            System.out.println("error");
            //Return exception
            throw e;
        };

	}

	public static void loadPlayMonths(String year, ObservableList<PlayMonth> playMonths) throws ClassNotFoundException, SQLException{
		String yearStr = "'" + year +"'";

		String selectStmt =
				"select " +
				"strftime('%Y', date) as year, " +
				"strftime('%m', date) as month, " +
				"count(distinct date) as total_date, " +
				"sum(hour) as total_hour, " +
				"count(*) as total_player, " +
				"sum(cost) as total_cost, " +
				"min(paid) as all_paid " +

				"from play " +
				"where year = "+ yearStr + " " +
				"group by month ";

        ResultSet result = DBUtil.dbExecuteQuery(selectStmt);

        try {
            while(result.next()){
            	String month = result.getString("month");
            	int cost = result.getInt("total_cost");
            	int totalPlayer = result.getInt("total_player");
            	float totalHour = result.getFloat("total_hour");
            	boolean paid = result.getBoolean("all_paid");

            	for(PlayMonth playMonth : playMonths){
            		if(playMonth.getMonth().equals(month)){
            			playMonth.setCost(cost);
            			playMonth.setTotalPlayer(totalPlayer);
            			playMonth.setTotalHour(totalHour);
            			playMonth.setPaid(paid);
            			break;
            		}
            	}
    		}

        } catch (SQLException e) {
            System.out.println("error");
            //Return exception
            throw e;
        };

	}


	public static void loadPlayDays(String year, String month, ObservableList<PlayDay> playDays) throws ClassNotFoundException, SQLException{
		String yearStr = "'" + year +"'";
		String monthStr = "'" + month +"'";


		String selectStmt =
				"select " +
				"strftime('%Y', date) as year, " +
				"strftime('%m', date) as month, " +
				"strftime('%d', date) as day, " +
				"sum(hour) as total_hour, " +
				"count(*) as total_player, " +
				"sum(cost) as total_cost, " +
				"min(paid) as all_paid, " +
				"count(DISTINCT group_num) AS total_group " +

				"from play " +
				"where year = " + yearStr + " and month = " + monthStr + " " +
				"group by day ";

        ResultSet result = DBUtil.dbExecuteQuery(selectStmt);

        try {
            while(result.next()){
            	String day = result.getString("day");
            	int cost = result.getInt("total_cost");
            	int totalGroup = result.getInt("total_group");
            	int totalPlayer = result.getInt("total_player");
            	float totalHour = result.getFloat("total_hour");
            	boolean paid = result.getBoolean("all_paid");

            	for(PlayDay playDay : playDays){
            		if(playDay.getDay().equals(day)){
            			playDay.setCost(cost);
            			playDay.setTotalGroup(totalGroup);
            			playDay.setTotalPlayer(totalPlayer);
            			playDay.setTotalHour(totalHour);
            			playDay.setPaid(paid);
            			break;
            		}
            	}
    		}

        } catch (SQLException e) {
            System.out.println("error");
            //Return exception
            throw e;
        };

	}

	public static void loadPlayGroups(String year, String month, String day, ObservableList<PlayGroup> playSuits) throws ClassNotFoundException, SQLException{
		String dateStr = "'"+year +"-"+month+"-"+day+"'";


		String selectStmt =
				"select " +
				"group_num, " +
				"start_time, " +
				"MAX(CASE WHEN early_quit = 0 THEN end_time ELSE null END) as end_time, " +
				"sum(cost) as total_cost, " +
				"count(*) as total_player, " +
				"sum(hour) as total_hour, " +
				"sum(hour) / count(*) as duration_average, " +
				"min(paid) as all_paid " +

				"from play " +
				"where date = " + dateStr +
				"group by group_num ";

        ResultSet result = DBUtil.dbExecuteQuery(selectStmt);

        try {
            while(result.next()){
            	int groupNum = result.getInt("group_num");
            	String startTime = result.getString("start_time");
            	String endTime = result.getString("end_time");
            	int cost = result.getInt("total_cost");
            	int totalPlayer = result.getInt("total_player");
            	float totalHour = result.getFloat("total_hour");
            	boolean paid = result.getBoolean("all_paid");

            	PlayGroup playSuit = new PlayGroup();
            	playSuits.add(playSuit);

            	playSuit.setGroupNum(groupNum);
            	playSuit.setStartTime(startTime);
            	playSuit.setEndTime(endTime);
            	playSuit.setCost(cost);
            	playSuit.setTotalPlayer(totalPlayer);
            	playSuit.setTotalHour(totalHour);
            	playSuit.setPaid(paid);


    		}

        } catch (SQLException e) {
            System.out.println("error");
            //Return exception
            throw e;
        };

	}

	public static void loadPlayers(String year, String month, String day, int groupNum, ObservableList<Player> customers) throws ClassNotFoundException, SQLException{


		String dateStr = "'"+year +"-"+month+"-"+day+"'";


		String selectStmt =
				"select " +
				"customer_id, " +
				"early_quit, " +
				"start_time, " +
				"end_time, " +
				"player_num, " +
				"group_num, " +
				"date, " +
				"hour, " +
				"discount, " +
				"cost, " +
				"paid, " +
				"play_all_day " +

				"from play " +
				"where date = " + dateStr + " and group_num = " + groupNum;

        ResultSet result = DBUtil.dbExecuteQuery(selectStmt);

        try {
            while(result.next()){
            	int playerNum = result.getInt("player_num");
            	String startTime = result.getString("start_time");
            	String endTime = result.getString("end_time");
            	boolean playAllDay = result.getBoolean("play_all_day");
            	boolean earlyQuit = result.getBoolean("early_quit");
            	int discount = result.getInt("discount");
            	int cost = result.getInt("cost");
            	float totalHour = result.getFloat("hour");
            	boolean paid = result.getBoolean("paid");

            	Player customer = new Player();
            	customers.add(customer);

            	customer.setPlayerNum(playerNum);
            	customer.setStartTime(startTime);
            	customer.setEndTime(endTime);
            	customer.setPlayAllDay(playAllDay);
            	customer.setEarlyQuit(earlyQuit);
            	customer.setDiscount(discount);
            	customer.setCost(cost);
            	customer.setTotalHour(totalHour);
            	customer.setPaid(paid);


    		}

        } catch (SQLException e) {
            System.out.println("error");
            //Return exception
            throw e;
        };

	}

	//------------------------ update --------------------------------------------

	public static void addPlayer(String date, String startTime, String endTime, int groupNum, int playerNum) throws ClassNotFoundException, SQLException{
		String dateStr = "'" + date + "'";
		String startTimeStr = "'" + startTime + "'";
		String endTimeStr = "'" + endTime + "'";

		String updateStmt = "insert into play (date, group_num, player_num, start_time, end_time) " +
				"values ("+dateStr +", " + groupNum +", "+ playerNum + ", " +startTimeStr + ", "+endTimeStr +")";

		DBUtil.dbExecuteUpdate(updateStmt);
	}

	public static void deletePlayer(String date, int groupNum, int playerNum) throws ClassNotFoundException, SQLException{
		String dateStr = "'" + date + "'";

		String updateStmt = "delete from play " +
							"where date = " + dateStr + " and group_num = " + groupNum + " and player_num = " +playerNum;

		DBUtil.dbExecuteUpdate(updateStmt);
	}

	public static void deletePlayGroup(String date, int groupNum) throws ClassNotFoundException, SQLException{
		String dateStr = "'" + date + "'";

		String updateStmt = "delete from play " +
							"where date = " + dateStr + " and group_num = " + groupNum;

		DBUtil.dbExecuteUpdate(updateStmt);
	}

	public static void updatePlayer(String date, int groupNum, int playerNum, boolean playAllDay, boolean earlyQuit,
			String startTime, String endTime, float hour, int discount, int cost, boolean paid) throws ClassNotFoundException, SQLException{

		String dateStr = "'" + date + "'";
		String startTimeStr = "'" + startTime + "'";
		String endTimeStr = "'" + endTime + "'";

		String updateStmt =
				"update play set " +
				"date = " + dateStr + ", " +
				"group_num = " + groupNum + ", " +
				"player_num = " + playerNum + ", " +
				"play_all_day = " + playAllDay + ", " +
				"early_quit = " + earlyQuit + ", " +
				"start_time = " + startTimeStr + ", " +
				"end_time = " + endTimeStr + ", " +
				"hour = " + hour + ", " +
				"discount = " + discount + ", " +
				"cost = " + cost + ", " +
				"paid = " + paid + " " +

				"where date = " + dateStr + " and group_num = " + groupNum + " and player_num = " + playerNum;

		DBUtil.dbExecuteUpdate(updateStmt);
	}








}
