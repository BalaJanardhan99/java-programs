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
import org.springframework.web.bind.annotation.RestController;

import com.cg.mts.entities.Course;
import com.cg.mts.entities.UniversityStaffMember;
import com.cg.mts.exception.CourseNotFoundException;
import com.cg.mts.exception.StaffNotFoundException;
import com.cg.mts.service.ICourseService;
import com.cg.mts.service.IUniversityStaffService;
@RestController
@RequestMapping("/universitystaff")
public class UniversityStaffMemberController {
	@Autowired
	IUniversityStaffService service;
	@Autowired
	ICourseService ics;
	@PostMapping
	public ResponseEntity<String> addUniversityStaffMember(@RequestBody UniversityStaffMember member)
	{
		service.addStaff(member);
		return new ResponseEntity<String>("A new university Staff member is added.",HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<String> updateUniversityStaffMember(@RequestBody UniversityStaffMember member)
	{
		Optional<UniversityStaffMember> uniStafList=service.viewStaff(member.getStaffId());
		if(uniStafList.isEmpty())
			throw new StaffNotFoundException();
		service.updateStaff(member);
		return new ResponseEntity<String>("The university Staff member details are updated.",HttpStatus.OK);
	}
	
	@GetMapping("/{value}")
	public ResponseEntity<Optional<UniversityStaffMember>> viewUniversityStaffMember(@PathVariable int value) 
	{
		Optional<UniversityStaffMember> uniStafList=service.viewStaff(value);
		if(uniStafList.isEmpty())
			throw new StaffNotFoundException();
		return new ResponseEntity<Optional<UniversityStaffMember>>(uniStafList,HttpStatus.OK);
	}
	
	@DeleteMapping("/{value}")
	public ResponseEntity<String> deleteUniversityStaffMember(@PathVariable int value)
	{
		Optional<UniversityStaffMember> uniStafList=service.viewStaff(value);
		if(uniStafList.isEmpty())
			throw new StaffNotFoundException();
		service.removeStaff(value);
		return new ResponseEntity<String>("A university staff member is deleted.",HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<UniversityStaffMember>> viewAllUniversityStaffMember() 
	{
		List<UniversityStaffMember> uniStafList=service.viewAllStaffs();
		if(uniStafList.isEmpty())
			throw new StaffNotFoundException();
		return new ResponseEntity<List<UniversityStaffMember>>(uniStafList,HttpStatus.OK);
	}
	@PutMapping("/c")
	public ResponseEntity<String> updateCourse(@RequestBody Course c)
	{	
		Optional<Course> course = ics.viewCourse(c.getCourseId());
		if(course.isEmpty())
				throw new CourseNotFoundException();
		ics.updateCourse(c);
		return new ResponseEntity<String>("The Course details are updated successfully.",HttpStatus.OK);
	}
	@PostMapping("/c")
	public ResponseEntity<String> addCourses(@RequestBody Course cou){
		ics.addCourse(cou);
		return new ResponseEntity<String>("A new Course is added to the list of University Courses.",HttpStatus.OK);
	}
	@DeleteMapping("CourseById/{code}")
	public ResponseEntity<String> removeCourseById(@PathVariable int code)
	{
		Optional<Course> course = ics.viewCourse(code);
		if(course.isEmpty())
				throw new CourseNotFoundException();
		ics.removeCourse(code);
		return new ResponseEntity<String>("A Course is Deleted.",HttpStatus.OK);
	}
}