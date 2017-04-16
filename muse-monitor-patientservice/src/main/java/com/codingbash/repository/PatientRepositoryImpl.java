package com.codingbash.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.codingbash.model.Patient;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

	@Autowired
	private PatientMongoRepository repo;

	@Override
	public Patient registerNewUser(Patient patient) {
		return repo.save(patient);
	}

	@Override
	public Patient retrievePatient(String patientId) {
		return repo.findByMongoId(patientId);
	}

	@Override
	public List<Patient> retrieveAllPatients() {
		return repo.findAll();
	}

}
