package com.nut.stock.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayGroup {
	private IntegerProperty groupNum;
	private StringProperty startTime;
	private StringProperty endTime;
	private IntegerProperty cost;
	private IntegerProperty totalPlayer;
	private FloatProperty totalHour;
	private BooleanProperty paid;

	public PlayGroup(){
		this(0, "00:00:00", "00:00:00", 0, 0, 0, false);
	}

	public PlayGroup(int groupNum, String startTime, String endTime, int cost, int totalPlayer, float totalHour, boolean paid) {
		this.groupNum = new SimpleIntegerProperty(this, "groupNum", groupNum);
		this.startTime = new SimpleStringProperty(this, "startTime", startTime);
		this.endTime = new SimpleStringProperty(this, "endTime", endTime);
		this.cost = new SimpleIntegerProperty(this, "cost", cost);
		this.totalPlayer = new SimpleIntegerProperty(this, "totalPlayer", totalPlayer);
		this.totalHour = new SimpleFloatProperty(this, "totalHour", totalHour);
		this.paid = new SimpleBooleanProperty(this, "paid", paid);
	}

	public final int getGroupNum(){
		return groupNum.get();
	}

	public final void setGroupNum(int value){
		groupNum.set(value);
	}

	public final IntegerProperty groupNumProperty(){
		return groupNum;
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
		return  endTime.get();
	}

	public final void setEndTime(String value){
		 endTime.set(value);
	}

	public final StringProperty endTimeProperty(){
		return  endTime;
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

	public final Integer getTotalPlayer(){
		return totalPlayer.get();
	}

	public final void setTotalPlayer(int value){
		totalPlayer.set(value);
	}

	public final IntegerProperty totalPlayerProperty(){
		return totalPlayer;
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
