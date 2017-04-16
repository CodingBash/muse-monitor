package com.codingbash.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.codingbash.model.Patient;

public interface PatientMongoRepository extends MongoRepository<Patient, String> {

	public Patient findByMongoId(String mongoId);

}