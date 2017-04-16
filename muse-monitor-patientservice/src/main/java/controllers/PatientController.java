package controllers;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(value = "/patient", method = RequestMethod.POST)
	public Patient registerPatient(@RequestBody PatientTO patient) {
		Patient inPatient = patientMapper.mapPatient(patient);
		Patient outPatient = patientRepository.registerNewUser(inPatient);
		return outPatient;
	}

	@RequestMapping(value = "/patient/{patientId}", method = RequestMethod.GET)
	public Patient retrievePatient(@PathParam("patientId") String patientId) {
		Patient outPatient = patientRepository.retrievePatient(patientId);
		return outPatient;
	}

	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public List<Patient> retrieveAllPatients() {
		List<Patient> outPatientList = patientRepository.retrieveAllPatients();
		return outPatientList;
	}

}
