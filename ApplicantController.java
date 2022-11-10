package com.cg.mts.controller;

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

import com.cg.mts.exception.ApplicantNotFoundException;
import com.cg.mts.exception.StatusNotFoundException;
import com.cg.mts.service.IApplicantService;
import java.util.List;
import java.util.Optional;

import com.cg.mts.entities.*;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {

	@Autowired
	IApplicantService appliser;

	@PostMapping("/add applicant")
	public ResponseEntity<String> addApplicants(@RequestBody Applicant applicant) {
		appliser.addApplicant(applicant);
		return new ResponseEntity<String>("A new applicant is added.", HttpStatus.OK);

	}

	@GetMapping("/{view by status}")
	public ResponseEntity<List<Applicant>> viewByStatus(@RequestParam AdmissionStatus status) {
		List<Applicant> li = appliser.viewAllApplicantsByStatus(status);
		if(li.isEmpty())
			throw new StatusNotFoundException();
		return new ResponseEntity<List<Applicant>>(li, HttpStatus.OK);
	}

	@GetMapping("viewid/{ApplicantId}")
	public ResponseEntity<Optional<Applicant>> viewById(@PathVariable int ApplicantId) {
		Optional<Applicant> newApplicant = appliser.viewApplicant(ApplicantId);
		if (newApplicant.isEmpty())
			throw new ApplicantNotFoundException();
		return new ResponseEntity<Optional<Applicant>>(newApplicant, HttpStatus.OK);

	}

	@DeleteMapping("delete/{applicantId}")
	public ResponseEntity<String> deleteApplicant(@PathVariable int applicantId) {
		Optional<Applicant> newApplicant = appliser.viewApplicant(applicantId);
		if (newApplicant.isEmpty())
			throw new ApplicantNotFoundException();
		appliser.deleteApplicant(applicantId);
		return new ResponseEntity<String>("The Applicant is deleted.", HttpStatus.OK);
	}

	@PutMapping("update/{applicant}")
	public ResponseEntity<String> updateApplicant(@RequestBody Applicant applicant) {
		Optional<Applicant> newApplicant = appliser.viewApplicant(applicant.getApplicantId());
		if (newApplicant.isEmpty())
			throw new ApplicantNotFoundException();
		appliser.updateApplicant(applicant);
		return new ResponseEntity<String>("The Applicant details are updated.", HttpStatus.OK);
	}
}
