package com.codingbash.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codingbash.mapper.PatientMapper;
import com.codingbash.model.Patient;
import com.codingbash.model.PatientTO;
import com.codingbash.repository.PatientRepository;

@RestController
public class PatientController {

	@Autowired
	private PatientMapper patientMapper;

	@Autowired
	private PatientRepository patientRepository;

	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public Patient registerPatient(@RequestBody PatientTO patient) {
		Patient inPatient = patientMapper.mapPatient(patient);
		Patient outPatient = patientRepository.registerNewUser(inPatient);
		return outPatient;
	}

	@RequestMapping(value = "/patients/{patientId}", method = RequestMethod.GET)
	public Patient retrievePatientById(@PathVariable("patientId") String patientId) {
		Patient outPatient = patientRepository.retrievePatientById(patientId);
		return outPatient;
	}

	/*
	 * TODO: I don't like this REST url design
	 */
	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public Patient retrievePatientByName(@RequestParam("patientName") String patientName) {
		Patient outPatient = patientRepository.retrievePatientByName(patientName);
		return outPatient;
	}

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<Patient> retrieveAllPatients() {
		List<Patient> outPatientList = patientRepository.retrieveAllPatients();
		return outPatientList;
	}

}
