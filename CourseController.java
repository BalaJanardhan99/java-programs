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
import com.cg.mts.exception.CourseNotFoundException;
import com.cg.mts.exception.CoursesNotFoundException;
import com.cg.mts.service.ICourseService;

@RestController
@RequestMapping("/course")
public class CourseController {
	@Autowired
	ICourseService ics;
	
	@PostMapping
	public ResponseEntity<String> addCourses(@RequestBody Course cou){
		ics.addCourse(cou);
		return new ResponseEntity<String>("A new Course is added to list of courses.",HttpStatus.OK);
	}
	
	@DeleteMapping("/{code}")
	public ResponseEntity<String> removeCourseById(@PathVariable int code){
		Optional<Course> course = ics.viewCourse(code);
		if(course.isEmpty())
			throw new CourseNotFoundException();
		ics.removeCourse(code);
		return new ResponseEntity<String>("A course is deleted.",HttpStatus.OK);
	}
		
	@PutMapping
	public ResponseEntity<String> updateItem(@RequestBody Course course){
		Optional<Course> cours = ics.viewCourse(course.getCourseId());
		if(cours.isEmpty())
			throw new CourseNotFoundException();
		ics.updateCourse(course);
		return new ResponseEntity<String>("The course details are Updated.",HttpStatus.OK);
	}
	@GetMapping("/{code}")
	public ResponseEntity<Optional<Course>> getCourseById(@PathVariable int code){
		Optional<Course> course = ics.viewCourse(code);
		if(course.isEmpty())
			throw new CourseNotFoundException();
		return new ResponseEntity<Optional<Course>>(course,HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Course>> getCourses(){
		List<Course> itemList = ics.viewAllCourses();
		if(itemList.isEmpty())
			throw new CoursesNotFoundException();
		return new ResponseEntity<List<Course>>(itemList,HttpStatus.OK);
	}
}
