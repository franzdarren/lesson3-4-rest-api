package com.spring.spring_rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.spring_rest.dto.StudentDto;
import com.spring.spring_rest.entity.Student;
import com.spring.spring_rest.service.StudentService;

//import javax.validation.Valid;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins="*")
public class StudentRestController {
	
	List<Student> theStudents = new ArrayList<>();
	
	private StudentService studentService;
	
	public StudentRestController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@PostMapping
	public ResponseEntity<StudentDto> createStudents(@Valid 
			@RequestBody StudentDto theStudentDto) {
		StudentDto createdStudent = studentService.createStudent(theStudentDto);
		return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<StudentDto>> getStudents() {
		List<StudentDto> students = studentService.getAllStudents();
		return ResponseEntity.ok(students);
	}
	@GetMapping("/{studentId}")
	public ResponseEntity<StudentDto> getStudent(@PathVariable Long studentId) {
		StudentDto studentDto = studentService.getStudentById(studentId);
		return ResponseEntity.ok(studentDto);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<StudentDto> updateStudent(@PathVariable Long id,
			@Valid @RequestBody StudentDto studentDto) {
		StudentDto updatedStudent = studentService.updateStudent(id, studentDto);
		return ResponseEntity.ok(updatedStudent);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<StudentDto> patchStudent(@PathVariable Long id, 
			@Valid @RequestBody StudentDto studentDto) {
		StudentDto updatedStudent = studentService.patchStudent(id, studentDto);
		return ResponseEntity.ok(updatedStudent);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteStudentById(@PathVariable Long id) {
		studentService.deleteStudentByid(id);
		return ResponseEntity.ok("Student deleted successfully");
	}

}	


//private List<Student> theStudents;
//
//@PostConstruct
//public void initialize() {
//	theStudents = new ArrayList<>();
//	
//	theStudents.add(new Student("asd","sdadw"));
//	theStudents.add(new Student("dwdw","dd"));
//	theStudents.add(new Student("aa","aaaa"));
//}

