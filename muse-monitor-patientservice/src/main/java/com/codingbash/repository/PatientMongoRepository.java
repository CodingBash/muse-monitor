package com.codingbash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.codingbash.model.Patient;

public interface PatientMongoRepository extends MongoRepository<Patient, String> {

	public Patient findByMongoId(String mongoId);
	
	public Patient findByName(String patientName);

}