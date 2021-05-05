package com.springBoot.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springBoot.petclinic.service.PetClinicService;

@Controller
public class PetClinicController {

	@Autowired
	private PetClinicService petClinicService;
	
	@RequestMapping("/owners")
	public ModelAndView getOwners() {
		ModelAndView mav= new ModelAndView();
		mav.addObject("owners",petClinicService.findOwners());
		mav.setViewName("owners");
		return mav;
	}
	
	@RequestMapping("/welcome")
	@ResponseBody
	public String Welcome() {
		return "welcome to Petclinic World";
	}
	
}
