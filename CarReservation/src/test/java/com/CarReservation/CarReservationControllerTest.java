package com.CarReservation;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
		LocalDate localDate = LocalDate.of(2019, 02, 11);
		Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CarInventory carData = new CarInventory(new Car(regNo, size, AC, fourWD, color), pickuptime, "Boston");
		boolean result = c1.addNewCar(carData);
		c1.display();
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
		LocalDate localDate = LocalDate.of(2019, 02, 11);
		Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		CarInventory carData = new CarInventory(new Car("abc123", "medium", true, true, "red"), pickuptime,
				"Arizona");
		boolean result = c1.addNewCar(carData);
		boolean expResult = false;
		assertEquals("Adding a New car with duplicate reg no ", expResult, result);
	}


	/**
	 * TestCase 5 Test the search Cars
	 * method(com.CarReservation.Controllers.CarReservation) search list of
	 * available cars for specific given type car at a desired date and time for
	 * a given number of days)
	 */
	@Test
	public void testFindAvailableCarMatch() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", true, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 02);
		Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate localdate = LocalDate.of(2019, 03, 20);
		Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);
		System.out.println(" TestCase 5 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 1;
		assertEquals("Searched cars are found", expResult, result);

	}	

	/**
	 * TestCase 6 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void testFindAvailableCarNoMatch() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "large", false, false, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 02);
		Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate localdate = LocalDate.of(2019, 03, 20);
		Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);
		System.out.println(" TestCase 6 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 0;
		assertEquals("Searched car was not found", expResult, result);
	}

	/**
	 * TestCase 7 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void test1FindAvailableCarNoMatchInDesiredTime() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 10);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 15);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);
		System.out.println(" TestCase 7 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 0;
		assertEquals("Searched car was not found", expResult, result);
	}

	/**
	 * TestCase 8 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void test2FindAvailableCarNoMatchInDesiredTime() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 10);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 14);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);
		System.out.println(" TestCase 8 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 0;
		assertEquals("Searched car was not found", expResult, result);
	}
	
	/**
	 * TestCase 9 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void test3FindAvailableCarNoMatchInDesiredTime() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 11);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 15);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);
		System.out.println(" TestCase 9 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 0;
		assertEquals("Searched car was not found", expResult, result);
	}

	/**
	 * TestCase 10 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void test4FindAvailableCarNoMatchInDesiredTime() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 9);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 16);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);

		System.out.println(" TestCase 10 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 0;
		assertEquals("Searched car was not found", expResult, result);
	}


	/**
	 * TestCase 11 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void test5FindAvailableCarNoMatchInDesiredTime() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 16);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 20);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		List<CarInventory> availableCars = c1.search(car, pickuptime, returntime);
		System.out.println(" TestCase 11 result:" + Arrays.toString(availableCars.toArray()));
		int result = availableCars.size();
		int expResult = 1;
		assertEquals("Searched car was not found", expResult, result);
	}

	/**
	 * TestCase 12 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void testReservedCarInDesiredTime() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 16);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 20);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		boolean result = c1.reserveCar(car, pickuptime, returntime);
		boolean expResult = true;
		assertEquals("Your car is reserved ", expResult, result);
	}
	
	/**
	 * TestCase 13 method(com.CarReservation.Controllers.CarReservation) No Match
	 * found for given search car desired pickup date, return date
	 */
	@Test
	public void testReservedCarNotFound() {
		CarReservation c1 = new CarReservation();
		Car car = new Car("", "medium", false, true, "red");
		LocalDate localDate = LocalDate.of(2019, 03, 9);
	Date pickuptime= Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	LocalDate localdate = LocalDate.of(2019, 03, 16);
	Date returntime= Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	boolean result = c1.reserveCar(car, pickuptime, returntime);
	boolean expResult = false;
	assertEquals("Your car is reserved ", expResult, result);
	}

}
