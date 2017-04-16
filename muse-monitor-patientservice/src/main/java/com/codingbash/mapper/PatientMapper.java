package com.codingbash.mapper;

import org.springframework.stereotype.Component;

import com.codingbash.model.Patient;
import com.codingbash.model.PatientTO;

@Component
public class PatientMapper {

	public Patient mapPatient(PatientTO patientTo){
		Patient patient = new Patient();
		patient.setAge(patientTo.getAge());
		patient.setDiagnostic(patientTo.getDiagnostic());
		patient.setGender(patientTo.getGender());
		patient.setName(patient.getName());
		patient.setPrimaryDoctor(patient.getPrimaryDoctor());
		patient.setRoomNumber(patient.getRoomNumber());
		return patient;
	}
}
