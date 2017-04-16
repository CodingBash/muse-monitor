package com.codingbash.repository;

import java.util.List;

import com.codingbash.model.Patient;

public interface PatientRepository {

	public Patient registerNewUser(Patient memeAccount);

	public Patient retrievePatient(String patientId);
	
	public List<Patient> retrieveAllPatients();
}