package com.CarReservation.Controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.CarReservation.Model.Car;
import com.CarReservation.Model.CarInventory;

/**
 * Contains all calls to the data store with cars that may be rented.
 */
public class CarReservation implements Serializable {

	private static final long serialVersionUID = 1L;
	private ConcurrentMap<String, CarInventory> cars = new ConcurrentHashMap<String, CarInventory>();

	/**
	 * Creates a new instance representing a particular CarResrvation(default
	 * Constructor)
	 */
	public CarReservation() {
		addNewCar();
	}

	/**
	 * By default add some cars into carInventory data for testing
	 */
	private void addNewCar() {
		// TODO Auto-generated method stub
		Car rentalcar1 = new Car("abc123", "medium", true, true, "red");
		Car rentalcar2 = new Car("abc124", "large", false, true, "black");
		Car rentalcar3 = new Car("abc125", "small", false, true, "blue");
		Car rentalcar4 = new Car("abc132", "medium", false, true, "red");
		Car rentalcar5 = new Car("abc138", "small", false, true, "white");
		cars.put(rentalcar1.getRegNo(), new CarInventory(rentalcar1));
		cars.put(rentalcar2.getRegNo(), new CarInventory(rentalcar2));
		cars.put(rentalcar3.getRegNo(), new CarInventory(rentalcar3));
		cars.put(rentalcar4.getRegNo(), new CarInventory(rentalcar4, new Date(2019, 2, 10), "New York"));
		cars.put(rentalcar5.getRegNo(), new CarInventory(rentalcar5, new Date(2019, 2, 1), "New York"));
	}

	/**
	 * Add a new car into car inventory.
	 * 
	 * @param car
	 *            The car need to add with default pickup time(current time),
	 *            default return time(null) and default location(Arizona)
	 * @return display all cars into inventory with currently added car.
	 */
	public boolean addNewCar(Car carData) {
		if (!cars.containsKey(carData.getRegNo())) {
			this.cars.put(carData.getRegNo(), new CarInventory(carData));
			// display();
			return true;
		} else {
			System.out.println("Fail to add new car: already exists" + carData.toString() + " ");
		}
		return false;
	}

	/**
	 * AddNewCar - Method overloading Add a new car into car inventory.
	 * 
	 * @param car
	 *            The car need to add with given pickup time, given
	 *            (specific)return time(null) and given location(Arizona)
	 * @return display all cars into inventory with currently added car.
	 */
	public boolean addNewCar(CarInventory carData) {
		if (carData.getRentedCar().getRegNo() != null && !carData.getRentedCar().getRegNo().isEmpty()
				&& !cars.containsKey(carData.getRentedCar().getRegNo())) {
			this.cars.put(carData.getRentedCar().getRegNo(), carData);
			//display();
			return true;
		} else {
			System.out.println("Fail to add new car: already exists" + carData.toString() + " ");
		}
		return false;
	}

	/**
	 * Search for a car matching the specified search criteria.
	 * 
	 * @param searched
	 *            This object contains the search criteria.
	 * @return A list of matching cars the searched car's description if a car
	 *         with the same features as searchedCar function will return a list
	 *         of found match , if no list will be empty/null.
	 */
	public List<CarInventory> findAvailableCar(CarInventory searched) {
		List<CarInventory> availableCars = new ArrayList<CarInventory>();
		for (CarInventory car : cars.values()) {
			if (matches(car, searched) && !car.isBooked() && isOverlapping(car, searched)) {
				availableCars.add(car);
			}
		}
		return availableCars;
	}

	/**
	 * Check searched car and found car pickup and return time overlapping
	 * condition.
	 * 
	 * @param searched
	 *            car(required car) and found car.
	 * @return true if overlapping does not exists else return false
	 */
	private boolean isOverlapping(CarInventory found, CarInventory searched) {
		if (found.getReturnDate() != null && found.getPickupDate() != null) {
			if (searched.getPickupDate().equals(found.getPickupDate())
					&& searched.getRentedCar().equals(found.getRentedCar()))
				return true;
			if (searched.getPickupDate().before(found.getReturnDate())
					&& searched.getPickupDate().after(found.getPickupDate())
					&& searched.getReturnDate().after(found.getPickupDate())
					&& searched.getReturnDate().before(found.getReturnDate()))
				return true;
		} else {
			if (searched.getPickupDate().after(found.getPickupDate())
					|| searched.getPickupDate().equals(found.getPickupDate()))
				return true;
		}
		return false;
	}

	/**
	 * Check Search for a car matching the specified search criteria like size,
	 * color,location
	 * 
	 * @param searched
	 *            car(required car) and found car.
	 * @return true if exists or else false
	 */
	private boolean matches(CarInventory found, CarInventory searched) {

		if (searched.getRentedCar().getSize() != null && !searched.getRentedCar().getSize().isEmpty()
				&& !searched.getRentedCar().getSize().equals(found.getRentedCar().getSize())) {
			return false;
		}
		if (searched.getRentedCar().getColor() != null && !searched.getRentedCar().getColor().isEmpty()
				&& !searched.getRentedCar().getColor().equals(found.getRentedCar().getColor())) {
			return false;
		}
		if (searched.getLocation() != null && !searched.getLocation().isEmpty()
				&& searched.getLocation().equals(found.getLocation())) {
			return false;
		}
		return true;

	}

	/**
	 * Rent a specific Car - If there is an existing car with the registration
	 * number of the specified car, set its booked property to true and set pick
	 * up and return time
	 * 
	 * @param car
	 *            The car that shall be marked as rented, pickup time , return
	 *            time.
	 * @param pickup
	 *            time - car pickup time
	 * @param return
	 *            time - car return time
	 */
	public boolean rentCar(CarInventory car, Date pickuptime, Date returntime) {
		CarInventory carToBook = findCarByRegNo(car);
		if (!carToBook.isBooked()) {
		carToBook.setPickupDate(pickuptime);
		carToBook.setReturnDate(returntime);
		carToBook.setBooked(true);
		cars.put(carToBook.getRentedCar().getRegNo(), carToBook);
		return true;
		}
		return false;
	}

	/**
	 * If there is an existing car with the registration number of the specified
	 * car, set its booked property to false and set pickup date as now and
	 * return date null
	 * 
	 * @param car
	 *            The car that shall be marked as return.
	 */

	public void returnCar(CarInventory car) {
		CarInventory carToReturn = findCarByRegNo(car);
		if (carToReturn.isBooked()) {
			carToReturn.setBooked(false);
			carToReturn.setPickupDate(new Date());
			carToReturn.setReturnDate(null);
			cars.put(carToReturn.getRentedCar().getRegNo(), carToReturn);
		}
		display();
	}

	/**
	 * Search for a car by reg no number .
	 *
	 * @param searchedCar
	 *            this object contain car reg no
	 * @return car object if found or return null
	 */
	public CarInventory findCarByRegNo(CarInventory car) {
		CarInventory foundcar = null;
		if (car != null && !car.getRentedCar().getRegNo().isEmpty() && car.getRentedCar().getRegNo() != null) {
			if (cars.containsKey(car.getRentedCar().getRegNo())) {
				foundcar = cars.get(car.getRentedCar().getRegNo());
				return foundcar;
			} 
		}
		return foundcar;
	}

	public void display() {
		for (Entry<String, CarInventory> entry : cars.entrySet()) {
			System.out.println(entry.getValue());

		}

	}

}
