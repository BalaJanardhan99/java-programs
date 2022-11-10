package com.cg.mts.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.exception.LoginFailedException;
import com.cg.mts.service.ILoginService;


@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	ILoginService service;
	
	@GetMapping("university{/staffId}{/password}")
	public ResponseEntity<String> universityStaffMemberLogin(@RequestParam int staffId,@RequestParam String password){
		boolean login = service.loginAsUniversityStaffMember(staffId,password);
		if(!login) 
			throw new LoginFailedException();
		return new ResponseEntity<String>("Welcome!You have Successfully Logged In!",HttpStatus.OK);
	}
	
	@GetMapping("admcommitee{/admId}")
	public ResponseEntity<String> admissionCommiteeLogin(@RequestParam int admId){
		boolean login = service.loginAsAdmissionCommiteeMember(admId);
		if(!login) 
			throw new LoginFailedException();
		return new ResponseEntity<String>("Welcome!You have Successfully Logged In!",HttpStatus.OK);
	}
}
