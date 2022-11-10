package com.cg.mts.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Admission;
import com.cg.mts.entities.AdmissionCommiteeMember;
import com.cg.mts.exception.AdminMemberNotFoundException;
import com.cg.mts.exception.AdminMembersNotFoundException;
import com.cg.mts.exception.ApplicantNotFoundException;
import com.cg.mts.exception.RecordNotFoundException;
import com.cg.mts.service.IAdmissionCommiteeMemberService;

@RestController
@RequestMapping("/admcommitee")
public class AdmissionCommiteeMemberController {
	@Autowired
	IAdmissionCommiteeMemberService admService;
	
	@PostMapping
	public ResponseEntity<String> addAdmissionCommiteeMember(@RequestBody AdmissionCommiteeMember member){
		admService.addCommiteeMember(member);
		return new ResponseEntity<String>("A new Admission Commitee member is successfully added!.", HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<AdmissionCommiteeMember>> getAdmissionCommiteeMember(){
		List<AdmissionCommiteeMember> admComList = admService.viewAllCommiteeMembers();
		if(admComList.isEmpty())
			throw new AdminMembersNotFoundException();
		return new ResponseEntity<List<AdmissionCommiteeMember>>(admComList, HttpStatus.OK);
	}
	
	@DeleteMapping("delete/{value}")
	public ResponseEntity<String> deleteAdmissionCommiteeMember(@PathVariable int value) {
		Optional<AdmissionCommiteeMember> appId = admService.viewCommiteeMember(value);
		if(appId.isEmpty())
			throw new AdminMemberNotFoundException();
		admService.removeCommiteeMember(value);
		return new ResponseEntity<String>("The admission member is deleted.", HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> updateAdmissionCommiteeMember(@RequestBody AdmissionCommiteeMember member) {
		Optional<AdmissionCommiteeMember> appId = admService.viewCommiteeMember(member.getAdminId());
		if(appId.isEmpty())
			throw new RecordNotFoundException();
		admService.updateCommiteeMember(member);
		return new ResponseEntity<String>("The Admission commitee memeber details Updated.", HttpStatus.OK);
	}
	
	@GetMapping("getCommiteeMember/{code}")
	public ResponseEntity<Optional<AdmissionCommiteeMember>> getAdmissionCommiteeMemberByAdminId(@PathVariable int code){
		Optional<AdmissionCommiteeMember> acmById = admService.viewCommiteeMember(code);
		if(acmById.isEmpty())
			throw new AdminMemberNotFoundException();
		return new ResponseEntity<Optional<AdmissionCommiteeMember>>(acmById, HttpStatus.OK); 
	}
	
	@GetMapping("/Admissionstatus")
	public ResponseEntity<Optional<Admission>> viewAdmissionStatus(@RequestParam int applicantId){
		Optional<Admission> adm = admService.provideAdmissionResult(applicantId);
		if(adm.isEmpty())
			throw new ApplicantNotFoundException();
		return new ResponseEntity<Optional<Admission>>(adm, HttpStatus.OK);
	}
}
