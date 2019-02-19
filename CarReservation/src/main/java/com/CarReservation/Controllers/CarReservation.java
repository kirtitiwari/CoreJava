package com.CarReservation.Controllers;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
		LocalDate localDate4 = LocalDate.of(2019, 03, 10);
		Date pickuptime4 = Date.from(localDate4.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate localdate4 = LocalDate.of(2019, 03, 15);
		Date returntime4 = Date.from(localdate4.atStartOfDay(ZoneId.systemDefault()).toInstant());

		Car rentalcar5 = new Car("abc138", "small", false, true, "white");
		LocalDate localDate5 = LocalDate.of(2019, 03, 01);
		Date pickuptime5 = Date.from(localDate5.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate localdate5 = LocalDate.of(2019, 03, 05);
		Date returntime5 = Date.from(localdate5.atStartOfDay(ZoneId.systemDefault()).toInstant());

		cars.put(rentalcar1.getRegNo(), new CarInventory(rentalcar1));
		cars.put(rentalcar2.getRegNo(), new CarInventory(rentalcar2));
		cars.put(rentalcar3.getRegNo(), new CarInventory(rentalcar3));
		cars.put(rentalcar4.getRegNo(), new CarInventory(rentalcar4, pickuptime4, returntime4, "New York"));
		cars.put(rentalcar5.getRegNo(), new CarInventory(rentalcar5, pickuptime5, returntime5));

	}

	/**
	 * AddNewCar - Method Add a new car into car inventory.
	 * 
	 * @param car The car need to add.
	 * @return display all cars into inventory with currently added car.
	 */
	public boolean addNewCar(CarInventory carData) {
		if (carData.getRentedCar().getRegNo() != null && !carData.getRentedCar().getRegNo().isEmpty()
				&& !cars.containsKey(carData.getRentedCar().getRegNo())) {
			this.cars.put(carData.getRentedCar().getRegNo(), carData);
			// display();
			return true;
		} else {
			System.out.println("Fail to add new car: already exists" + carData.toString() + " ");
		}
		return false;
	}

	/**
	 * Search for a car matching the specified search criteria.
	 * 
	 * @param searchCar This object contains the search criteria.
	 * @return A list of matching cars the searched car's description if a car with
	 *         the same features as searchedCar function will return a list of found
	 *         match , if no list will be empty/null.
	 */

	public List<CarInventory> search(Car searchCar, Date pickupDate, Date returnDate) {
		List<CarInventory> availableCars = new ArrayList<CarInventory>();
		CarInventory givenCar = new CarInventory(searchCar, pickupDate, returnDate);
		for (CarInventory car : cars.values()) {
			if (isExists(car, givenCar) && isAvailable(car, givenCar)) {
				availableCars.add(car);
			}

		}
		return availableCars;
	}

	/**
	 * Reserve a specific Car - If there is an existing car available in desired
	 * time frame.
	 * 
	 * @param searchcar The car that shall be marked as reserved
	 * @param pickup    time - car pickup time
	 * @param           return time - car return time
	 */
	public boolean reserveCar(Car searchCar, Date pickupDate, Date returnDate) {
		boolean reserved = false;
		CarInventory givenCar = new CarInventory(searchCar, pickupDate, returnDate);
		for (CarInventory car : cars.values()) {
			if (isExists(car, givenCar) && !car.isBooked() && isAvailable(car, givenCar)) {
				car.setBooked(true);
				car.setPickupDate(givenCar.getPickupDate());
				car.setReturnDate(givenCar.getReturnDate());
				cars.put(car.getRentedCar().getRegNo(), car);
				reserved = true;
				System.out.println("Your car is Reserved:" + car.toString());
				break;
			}

		}
		if (reserved == false)
			System.out.println("Your car is not available :" + givenCar.toString());
		return reserved;
	}

	/**
	 * Check GivenCar car and FoundCar pickup and return time overlapping condition.
	 * 
	 * @param givenCar car(required car) and ExistsCar car in car-inventory
	 * @return true if overlapping does not exists else return false
	 */
	private boolean isAvailable(CarInventory foundCar, CarInventory givenCar) {
		boolean available = false;
		if (givenCar.getPickupDate() == null || givenCar.getReturnDate() == null || foundCar.getPickupDate() == null
				|| givenCar.getReturnDate() == null) {
			available = false;
		} else if (givenCar.getPickupDate().after(foundCar.getPickupDate())
				&& givenCar.getPickupDate().before(foundCar.getReturnDate())
				|| givenCar.getReturnDate().after(foundCar.getPickupDate())
						&& givenCar.getReturnDate().before(foundCar.getReturnDate())
				|| givenCar.getPickupDate().before(foundCar.getPickupDate())
						&& givenCar.getReturnDate().after(foundCar.getReturnDate())
				|| givenCar.getPickupDate().equals(foundCar.getPickupDate())
						&& givenCar.getReturnDate().equals(foundCar.getReturnDate())) {
			available = false;
		} else {
			available = true;
		}
		return available;
	}

	/**
	 * Check Search for a car matching the specified search criteria like size,
	 * color,location
	 * 
	 * @param givenCar car(required car) and foundCar car in car-inventory
	 * @return true if exists or else false
	 */
	private boolean isExists(CarInventory foundCar, CarInventory givenCar) {

		if (givenCar.getRentedCar().getSize() != null && !givenCar.getRentedCar().getSize().isEmpty()
				&& !givenCar.getRentedCar().getSize().equals(foundCar.getRentedCar().getSize())) {
			return false;
		}
		if (givenCar.getRentedCar().getColor() != null && !givenCar.getRentedCar().getColor().isEmpty()
				&& !givenCar.getRentedCar().getColor().equals(foundCar.getRentedCar().getColor())) {
			return false;
		}
		if (givenCar.getRentedCar().isAC() != foundCar.getRentedCar().isAC()) {
			return false;
		}
		if (givenCar.getRentedCar().isFourWD() != foundCar.getRentedCar().isFourWD()) {
			return false;
		}
		return true;

	}

	/**
	 * Display all cars in current car-inventory
	 */

	public void display() {
		for (Entry<String, CarInventory> entry : cars.entrySet()) {
			System.out.println(entry.getValue());

		}

	}

}
