package com.CarReservation;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.CarReservation.Controllers.CarReservation;
import com.CarReservation.Model.Car;
import com.CarReservation.Model.CarInventory;

/**
 * Unit test for CarReservation.Controllers.
 */
@RunWith(JUnit4.class)
public class CarReservationControllerTest {

	/**
	 * TestCase 1 Test the equals method(com.CarReservation.Model.Car.java) with
	 * two Car object same reg no.
	 */
	@Test
	public void testEqualsCarObject() {
		String regNo = "ABC123";
		String size = "medium";
		boolean AC = true;
		boolean fourWD = false;
		String color = "red";
		Car instance = new Car(regNo, size, AC, fourWD, color);
		Car equalInstance = new Car(regNo, size, AC, fourWD, color);
		boolean expResult = true;
		boolean result = instance.equals(equalInstance);
		assertEquals("Car instances with same reg no are equal.", expResult, result);
	}

	/**
	 * TestCase 2 Test the equals method(com.CarReservation.Model.Car.java) with
	 * two Car object Different reg no.
	 */
	@Test
	public void testNotEqualRegNo() {
		String regNo = "ABC123";
		String size = "medium";
		boolean AC = true;
		boolean fourWD = false;
		String color = "red";
		Car instance = new Car("CD145", size, AC, fourWD, color);
		Car equalInstance = new Car(regNo, size, AC, fourWD, color);
		boolean expResult = false;
		boolean result = instance.equals(equalInstance);
		assertEquals("Car instances with Different reg no are not equal.", expResult, result);
	}

	/**
	 * TestCase 3 Test the addNewCar method
	 * (com.CarReservation.Controllers.CarReservation) Add new CarInventory
	 * object with unique reg no.
	 */
	@Test
	public void testAddNewCar() {
		String regNo = "ABC140";
		String size = "medium";
		boolean AC = true;
		boolean fourWD = false;
		String color = "red";
		CarReservation c1 = new CarReservation();
		CarInventory carData = new CarInventory(new Car(regNo, size, AC, fourWD, color), new Date(2019, 2, 11), "");
		boolean result = c1.addNewCar(carData);
		boolean expResult = true;
		assertEquals("Adding a New car with unique reg no ", expResult, result);
	}

	/**
	 * TestCase 4 Test the addNewCar method
	 * (com.CarReservation.Controllers.CarReservation) Add new CarInventory
	 * object with duplicate reg no.
	 */
	@Test
	public void testAlreadyAddedCar() {
		CarReservation c1 = new CarReservation();
		CarInventory carData = new CarInventory(new Car("abc123", "medium", true, true, "red"), new Date(2019, 2, 11),
				"");
		boolean result = c1.addNewCar(carData);
		boolean expResult = false;
		assertEquals("Adding a New car with duplicate reg no ", expResult, result);
	}

	/**
	 * TestCase 5 Test the findCarByRegNo
	 * method(com.CarReservation.Controllers.CarReservation) search car
	 * Inventory for given Car object by car reg no
	 */
	@Test
	public void testCarNotExists() {
		CarReservation c1 = new CarReservation();
		CarInventory carData = new CarInventory(new Car("DEF245", "medium", true, true, "red"), new Date(2019, 2, 11),
				"");
		boolean result = (c1.findCarByRegNo(carData) == null) ? true : false;
		boolean expResult = true;
		assertEquals("Given Car is Not Exists ", expResult, result);
	}

	/**
	 * TestCase 6 Test the findAvailableCar
	 * method(com.CarReservation.Controllers.CarReservation) search list of
	 * available cars for specific given type car at a desired date and time for
	 * a given number of days)
	 */
	@Test
	public void testFindAvailableCarMatch() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, false, "red");
		CarInventory searchedCar = new CarInventory(car, new Date(2019, 2, 2), new Date(2019, 2, 20), "");
		List<CarInventory> availableCars = c1.findAvailableCar(searchedCar);
		System.out.println(" TestCase 6 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 1;
		assertEquals("Searched cars are found", expResult, result);

	}

	/**
	 * TestCase 7 Test the findAvailableCar
	 * method(com.CarReservation.Controllers.CarReservation) search list of
	 * available cars for desired pickup date, return date and location
	 */
	@Test
	public void testFindAllAvailableCarMatch() {
		CarReservation c1 = new CarReservation();
		CarInventory searchedCar = new CarInventory(new Car(), new Date(2019, 2, 2), new Date(2019, 2, 3), "Arizona");
		List<CarInventory> availableCars = c1.findAvailableCar(searchedCar);
		System.out.println(" TestCase 7 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 1;
		assertEquals("Searched cars are not found", expResult, result);

	}

	/**
	 * TestCase 8 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void testFindAvailableCarNoMatch() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "Lagre", false, false, "red");
		CarInventory searchedCar = new CarInventory(car, new Date(2019, 2, 2), new Date(2019, 2, 20), "");
		List<CarInventory> availableCars = c1.findAvailableCar(searchedCar);
		System.out.println(" TestCase 8 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 0;
		assertEquals("Searched car was not found", expResult, result);
	}

	/**
	 * TestCase 9 Test the returnCar(CarInventory car)
	 * method(com.CarReservation.Controllers.CarReservation) Booking specific
	 * search car
	 */
	@Test
	public void testReservCarSuccess() {
		CarReservation c1 = new CarReservation();
		CarInventory searchedCar = new CarInventory(new Car(), new Date(2019, 2, 2), new Date(2019, 2, 3), "Arizona");
		List<CarInventory> availableCars = c1.findAvailableCar(searchedCar);
		CarInventory RentalCar = availableCars.get(0);
		boolean result = c1.rentCar(RentalCar, searchedCar.getPickupDate(), searchedCar.getReturnDate());
		boolean expResult = true;
		assertEquals("Booking Specific car ", expResult, result);
	}


}
