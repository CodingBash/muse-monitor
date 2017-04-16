package com.codingbash.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.codingbash.model.Patient;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

	@Autowired
	private RestTemplate restTemplate;

	public static final String SEARCH_PATIENT_BY_ID_URL = "http://muse-monitor-patientservice.herokuapp.com/patients/{patientId}";
	public static final String RETRIEVE_ALL_PATIENTS_URL = "http://muse-monitor-patientservice.herokuapp.com/patients";

	@Override
	public Patient retrievePatient(String patientId) {
		ResponseEntity<Patient> response = restTemplate.getForEntity(SEARCH_PATIENT_BY_ID_URL, Patient.class,
				patientId);
		return response.getBody();
	}

	@Override
	public List<Patient> retrieveAllPatients() {
		ResponseEntity<Patient[]> response = restTemplate.getForEntity(RETRIEVE_ALL_PATIENTS_URL, Patient[].class);
		return Arrays.asList(response.getBody());
	}

}
