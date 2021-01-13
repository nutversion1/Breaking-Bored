package com.nut.stock.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayDay {

	private StringProperty day;
	private StringProperty dayOfWeek;
	private IntegerProperty cost;
	private IntegerProperty totalGroup;
	private IntegerProperty totalPlayer;
	private FloatProperty totalHour;
	private BooleanProperty paid;

	public PlayDay(String day, String dayOfWeek, int cost, int totalGroup, int totalPlayer, float totalHour, boolean paid) {
		this.day = new SimpleStringProperty(this, "day", day);
		this.dayOfWeek = new SimpleStringProperty(this, "dayOfWeek", dayOfWeek);
		this.cost = new SimpleIntegerProperty(this, "cost", cost);
		this.totalGroup = new SimpleIntegerProperty(this, "totalGroup", totalGroup);
		this.totalPlayer = new SimpleIntegerProperty(this, "totalPlayer", totalPlayer);
		this.totalHour = new SimpleFloatProperty(this, "totalHour", totalHour);
		this.paid = new SimpleBooleanProperty(this, "paid", paid);
	}

	public final String getDay(){
		return day.get();
	}

	public final void setDay(String value){
		day.set(value);
	}

	public final StringProperty dayProperty(){
		return day;
	}

	public final String getDayOfWeek(){
		return  dayOfWeek.get();
	}

	public final void setDayOfWeek(String value){
		 dayOfWeek.set(value);
	}

	public final StringProperty dayOfWeekProperty(){
		return dayOfWeek;
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

	public final Integer getTotalGroup(){
		return totalGroup.get();
	}

	public final void setTotalGroup(int value){
		totalGroup.set(value);
	}

	public final IntegerProperty totalGroupProperty(){
		return totalGroup;
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
