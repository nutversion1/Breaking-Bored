package com.nut.stock.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
	//private StringProperty date;
	//private IntegerProperty groupNum;
	private IntegerProperty playerNum;
	private StringProperty name;
	private BooleanProperty playAllDay;
	private BooleanProperty earlyQuit;
	private StringProperty startTime;
	private StringProperty endTime;
	private IntegerProperty discount;
	private IntegerProperty cost;
	private FloatProperty duration;
	private FloatProperty totalHour;
	private BooleanProperty paid;

	public Player() {
		this(0, "ไม่รู้จัก", "00:00:00", "00:00:00", false, false, 0, 0, 0, 0, false);
	}

	public Player(int playerNum, String name, String startTime, String endTime, boolean playAllDay, boolean earlyQuit, int discount, int cost, float duration, float totalHour, boolean paid) {
		this.playerNum = new SimpleIntegerProperty(this, "playerNum", playerNum);
		this.name = new SimpleStringProperty(this, "name", name);
		this.startTime = new SimpleStringProperty(this, "startTime", startTime);
		this.endTime = new SimpleStringProperty(this, "endTime", endTime);
		this.playAllDay = new SimpleBooleanProperty(this, "playAllDay", playAllDay);
		this.earlyQuit = new SimpleBooleanProperty(this, "earlyQuit", earlyQuit);
		this.discount = new SimpleIntegerProperty(this, "discount", discount);
		this.cost = new SimpleIntegerProperty(this, "cost", cost);
		this.duration = new SimpleFloatProperty(this, "duration", duration);
		this.totalHour = new SimpleFloatProperty(this, "totalHour", totalHour);
		this.paid = new SimpleBooleanProperty(this, "paid", paid);
	}

	/*public final String getDate(){
		return date.get();
	}

	public final void setDate(String value){
		date.set(value);
	}

	public final StringProperty dateProperty(){
		return date;
	}

	public final int getGroupNum(){
		return groupNum.get();
	}

	public final void setGroupNum(int value){
		groupNum.set(value);
	}

	public final IntegerProperty groupNumProperty(){
		return groupNum;
	}*/

	public final int getPlayerNum(){
		return playerNum.get();
	}

	public final void setPlayerNum(int value){
		playerNum.set(value);
	}

	public final IntegerProperty playerNumProperty(){
		return playerNum;
	}

	public final String getName(){
		return name.get();
	}

	public final void setName(String value){
		name.set(value);
	}

	public final StringProperty nameProperty(){
		return name;
	}

	public final String getStartTime(){
		return startTime.get();
	}

	public final void setStartTime(String value){
		startTime.set(value);
	}

	public final StringProperty startTimeProperty(){
		return startTime;
	}

	public final String getEndTime(){
		return endTime.get();
	}

	public final void setEndTime(String value){
		//System.out.println("set end time: "+value);
		endTime.set(value);
	}

	public final StringProperty endTimeProperty(){
		return endTime;
	}

	public final boolean getPlayAllDay(){
		return playAllDay.get();
	}

	public final void setPlayAllDay(boolean value){
		playAllDay.set(value);
	}

	public final BooleanProperty playAllDayProperty(){
		return playAllDay;
	}

	public final boolean getEarlyQuit(){
		return earlyQuit.get();
	}

	public final void setEarlyQuit(boolean value){
		earlyQuit.set(value);
	}

	public final BooleanProperty earlyQuitProperty(){
		return earlyQuit;
	}

	public final int getDiscount(){
		return discount.get();
	}

	public final void setDiscount(int value){
		discount.set(value);
	}

	public final IntegerProperty discountProperty(){
		return discount;
	}

	public final Integer getCost(){
		return cost.get();
	}

	public final void setCost(int value){
		cost.set(value);
	}

	public final IntegerProperty costProperty(){
		return cost;
	}

	public final Float getDuration(){
		return duration.get();
	}

	public final void setDuration(float value){
		duration.set(value);
	}

	public final FloatProperty durationProperty(){
		return duration;
	}

	public final Float getTotalHour(){
		return totalHour.get();
	}

	public final void setTotalHour(float value){
		totalHour.set(value);
	}

	public final FloatProperty totalHourProperty(){
		return totalHour;
	}

	public final Boolean getPaid(){
		return paid.get();
	}

	public final void setPaid(boolean value){
		paid.set(value);
	}

	public final BooleanProperty paid(){
		return paid;
	}

}
