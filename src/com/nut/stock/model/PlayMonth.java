package com.nut.stock.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayMonth {

	private StringProperty month;
	private IntegerProperty cost;
	private IntegerProperty totalPlayer;
	private FloatProperty totalHour;
	private BooleanProperty paid;

	public PlayMonth(String month, int cost, int totalPlayer, float totalHour, boolean paid) {
		this.month = new SimpleStringProperty(this, "month", month);
		this.cost = new SimpleIntegerProperty(this, "cost", cost);
		this.totalPlayer = new SimpleIntegerProperty(this, "totalPlayer", totalPlayer);
		this.totalHour = new SimpleFloatProperty(this, "totalHour", totalHour);
		this.paid = new SimpleBooleanProperty(this, "paid", paid);
	}

	public final String getMonth(){
		return month.get();
	}

	public final void setMonth(String value){
		month.set(value);
	}

	public final StringProperty monthProperty(){
		return month;
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
