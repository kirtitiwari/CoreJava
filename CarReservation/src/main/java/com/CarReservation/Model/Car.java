package com.CarReservation.Model;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Class Car attributes */

	private String regNo;

	private String size;

	private boolean AC;

	private boolean fourWD;

	private String color;

	/**
	 * Creates a new instance representing a particular car(parameterized Constructor)
	 * 
	 * @param regNo
	 *            The car's registration number.
	 * @param size
	 *            The size of the car, e.g., <code>medium hatchback</code>.
	 * @param AC
	 *            if the car has air condition.
	 * @param fourWD
	 *            if the car has four wheel drive.
	 * @param color
	 *            The color of the car.
	 * 
	 */

	public Car(String regNo, String size, boolean AC, boolean fourWD,

	String color) {

		this.size = size;

		this.AC = AC;

		this.fourWD = fourWD;

		this.color = color;

		this.regNo = regNo;

	}

	/**
	 * Creates a new instance representing a particular car(default Constructor)
	 */
	public Car() {

	}

	@Override

	public String toString() {

		StringBuilder builder = new StringBuilder();

		builder.append("regNo: " + regNo + ", ");

		builder.append("size: " + size + ", ");

		builder.append("AC: " + AC + ", ");

		builder.append("4WD: " + fourWD + ", ");

		builder.append("color: " + color);

		return builder.toString();

	}

	@Override

	public int hashCode() {

		int hash = 20;

		hash = 89 * hash + Objects.hashCode(this.regNo);

		return hash;

	}

	@Override

	public boolean equals(Object obj) {

		if (this == obj) {

			return true;

		}

		if (obj == null) {

			return false;

		}

		if (getClass() != obj.getClass()) {

			return false;

		}

		final Car other = (Car) obj;

		if (!Objects.equals(this.regNo, other.regNo)) {

			return false;

		}

		return true;

	}
	
	/**
	 * Getter and Setter for all attributes of Class Car
	 */	

	public String getRegNo() {

		return regNo;

	}

	public String getColor() {

		return color;

	}

	public boolean isFourWD() {

		return fourWD;

	}

	public boolean isAC() {

		return AC;

	}

	public String getSize() {

		return size;

	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setAC(boolean aC) {
		AC = aC;
	}

	public void setFourWD(boolean fourWD) {
		this.fourWD = fourWD;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
