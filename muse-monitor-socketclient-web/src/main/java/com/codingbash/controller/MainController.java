package com.codingbash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value="/patients", method = RequestMethod.GET)
	public ModelAndView viewPatients(){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("patientList");
		
		/*
		 * TODO: Business logic
		 */
		
		return mav;
	}
	
	@RequestMapping(value="/patients/{patientId}", method = RequestMethod.GET)
	public ModelAndView viewPatientInformation(@PathVariable String patientId){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("patient");
		
		/*
		 * TODO: Business logic
		 */
		
		return mav;
	}
}
