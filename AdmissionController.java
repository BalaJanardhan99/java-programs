package com.cg.mts.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

import com.cg.mts.dto.AdmissionDto;
import com.cg.mts.entities.Admission;
import com.cg.mts.entities.Applicant;
import com.cg.mts.exception.AdmissionNotFoundException;
import com.cg.mts.exception.AdmissionNotGrantedException;
import com.cg.mts.exception.ApplicantNotFoundException;
import com.cg.mts.exception.CourseNotFoundException;
import com.cg.mts.exception.NoSuchDateException;
import com.cg.mts.service.IAdmissionService;
import com.cg.mts.service.IApplicantService;

@RestController
@RequestMapping("/admission")
public class AdmissionController {
	
	@Autowired
	IAdmissionService admser;
	
	@Autowired
	IApplicantService appser;
	
	@PostMapping
	public ResponseEntity<String> addAdmissions(@RequestBody AdmissionDto adm){
		int appId = adm.getApplicantId();
		if(appser.viewApplicant(appId).isEmpty())
			throw new ApplicantNotFoundException();
		admser.addAdmission(adm);
		return new ResponseEntity<String>("A new Admission is added.",HttpStatus.OK);
	}
	
	@GetMapping("/getbycourse{courseId}")
	public ResponseEntity<List<Admission>> getAdmissionByCourseId(@PathVariable int courseId){
		List<Admission> adm = admser.showAllAdmissionsByCourseId(courseId);
		if(adm.isEmpty())
			throw new CourseNotFoundException();
		return new ResponseEntity<List<Admission>>(adm, HttpStatus.OK);
		
	}
	
	@GetMapping("/getBydate{date}")
	public ResponseEntity<List<Admission>> getAdmissionByDate(@RequestParam String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
		String da = date;
		LocalDate localDate = LocalDate.parse(da,formatter);
		List<Admission> admlist = admser.showAllAdmissionsByDate(localDate);
		if(admlist.isEmpty())
			throw new NoSuchDateException();
		return new ResponseEntity<List<Admission>>(admlist,HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> updateAdm(@RequestBody AdmissionDto admission){
		Admission adm = admser.viewAdmission(admission.getAdmissionId());
		if(adm == null)
			throw new AdmissionNotGrantedException();
		int appId = admission.getApplicantId();
		if(appser.viewApplicant(appId).isEmpty())
			throw new ApplicantNotFoundException();
		admser.updateAdmission(admission);
		return new ResponseEntity<String>("Updated the admission details.", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete{admissionId}")
	public ResponseEntity<String> deleteAdm(@RequestParam int admissionId){
		Admission adm = admser.viewAdmission(admissionId);
		if(adm == null)
			throw new AdmissionNotGrantedException();
		Applicant app = adm.getApplicant();
		admser.updateAdd(adm);
		admser.cancelAdmission(admissionId);
		appser.deleteApplicant(app.getApplicantId());
		return new ResponseEntity<String>("deleted the admission", HttpStatus.OK);
	}
	
	@GetMapping("/viewadmission{admissionid}")
	public ResponseEntity<Admission> viewAdmissionById(@PathVariable int admissionid){
		Admission adm = admser.viewAdmission(admissionid);
		if(adm == null)
			throw new AdmissionNotFoundException();
		return new ResponseEntity<Admission>(adm,HttpStatus.OK);
	}
	
}
