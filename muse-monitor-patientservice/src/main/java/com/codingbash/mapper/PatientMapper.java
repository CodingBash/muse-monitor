package com.codingbash.mapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.codingbash.model.Patient;
import com.codingbash.model.PatientTO;

@Component
public class PatientMapper {

	public Patient mapPatient(PatientTO patientTo){
		Patient patient = new Patient();
		patient.setAge(patientTo.getAge());
		patient.setDiagnostic(patientTo.getDiagnostic());
		patient.setGender(StringUtils.trim(patientTo.getGender()));
		patient.setName(StringUtils.trim(patientTo.getName()));
		patient.setPrimaryDoctor(StringUtils.trim(patientTo.getPrimaryDoctor()));
		patient.setRoomNumber(patientTo.getRoomNumber());
		return patient;
	}
}
