package com.codingbash.model;

public class Patient {

	private String mongoId;
	private String name;
	private int roomNumber;
	private String gender;
	private String diagnostic;
	private int age;
	private String primaryDoctor;

	public String getMongoId() {
		return mongoId;
	}

	public void setMongoId(String mongoId) {
		this.mongoId = mongoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDiagnostic() {
		return diagnostic;
	}

	public void setDiagnostic(String diagnostic) {
		this.diagnostic = diagnostic;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPrimaryDoctor() {
		return primaryDoctor;
	}

	public void setPrimaryDoctor(String primaryDoctor) {
		this.primaryDoctor = primaryDoctor;
	}

}
