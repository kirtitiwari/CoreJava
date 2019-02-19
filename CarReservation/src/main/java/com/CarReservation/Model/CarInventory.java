package com.CarReservation.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;

public class CarInventory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Date pickupDate;
	private Date returnDate;
	private String location;
	private Car rentedCar;
	private boolean booked;
	
	public CarInventory(Car rentedCar){
		this.rentedCar = rentedCar;
		this.pickupDate= new Date();
		this.returnDate= CarInventory.returnDate();
		this.location = "Arizona";
		this.booked=false;
	}
	
	public CarInventory(Car rentedCar, Date pickupDate, String location){
		this.rentedCar = rentedCar;
		this.pickupDate= pickupDate;
		this.returnDate= CarInventory.returnDate(pickupDate);
		this.location = location;
		this.booked=false;
	}
	
	public CarInventory(Car rentedCar, Date pickupDate, Date returnDate){
		this.rentedCar = rentedCar;
		this.pickupDate= pickupDate;
		this.returnDate= returnDate;
		this.location = "";
		this.booked=false;
	}

	
	public CarInventory(Car rentedCar, Date pickupDate, Date returnDate, String location){
		this.rentedCar = rentedCar;
		this.pickupDate= pickupDate;
		this.returnDate= returnDate;
		this.location = location;
		this.booked=false;
	}


	public Date getPickupDate() {
		return pickupDate;
	}


	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}


	public Date getReturnDate() {
		return returnDate;
	}


	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Car getRentedCar() {
		return rentedCar;
	}

	public void setRentedCar(Car rentedCar) {
		this.rentedCar = rentedCar;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("rentedCar: " + rentedCar.toString() + ", ");

		builder.append("booked:" + booked + ", ");

		builder.append("Pickup time:" + pickupDate + ", ");

		builder.append("ReturnDate " + returnDate + ", ");

		builder.append("location: " + location);

		return builder.toString();

	}


	private static Date returnDate() {
		Date returnDate = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(returnDate);
		cal.add(Calendar.DATE, 4); // add 4 Days
		returnDate = cal.getTime();
		return returnDate;
		
	}
	
	private static Date returnDate(Date pickupDate) {
		Date returnDate = pickupDate; 
		Calendar cal = Calendar.getInstance();
		cal.setTime(returnDate);
		cal.add(Calendar.DATE, 4); // add 4 Days
		returnDate = cal.getTime();
		return returnDate;
		
	}
}
