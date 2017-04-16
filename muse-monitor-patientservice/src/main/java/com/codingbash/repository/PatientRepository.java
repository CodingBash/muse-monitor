package com.codingbash.repository;

import java.util.List;

import com.codingbash.model.Patient;

public interface PatientRepository {

	public Patient registerNewUser(Patient memeAccount);

	public Patient retrievePatientById(String patientId);
	
	public Patient retrievePatientByName(String patientName);
	
	public List<Patient> retrieveAllPatients();

}