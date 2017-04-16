package com.codingbash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.codingbash.model.Patient;
import com.codingbash.repository.PatientRepository;

@Controller
public class MainController {

	@Autowired
	private PatientRepository patientRepository;

	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public ModelAndView viewPatients() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("patientList");

		List<Patient> patientList = patientRepository.retrieveAllPatients();

		mav.addObject("patientList", patientList);

		return mav;
	}

	@RequestMapping(value = "/patients/{patientId}", method = RequestMethod.GET)
	public ModelAndView viewPatientInformation(@PathVariable String patientId) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("patient");

		Patient patient = patientRepository.retrievePatient(patientId);
		List<Patient> patientList = patientRepository.retrieveAllPatients();

		mav.addObject("patient", patient);
		mav.addObject("patientList", patientList);
		
		return mav;
	}
}
