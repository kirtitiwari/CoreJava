package com.CarReservation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.CarReservation.Controllers.CarReservation;
import com.CarReservation.Model.Car;
import com.CarReservation.Model.CarInventory;

/**
 * Car Rental System
 *
 */
public class Main 
{
    public static void main( String[] args )
    {
        System.out.println( "Welcome to Car Rental System!" );
        CarReservation c1 = new CarReservation();
        c1.display();
        
    }
}
