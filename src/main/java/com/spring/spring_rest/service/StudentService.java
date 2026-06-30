package com.spring.spring_rest.service;

import java.util.List;

import com.spring.spring_rest.dto.StudentDto;

public interface StudentService {
	
	StudentDto createStudent(StudentDto studentDto);
	StudentDto getStudentById(Long id);
	List<StudentDto> getAllStudents();
	StudentDto updateStudent(Long theId, StudentDto studentDto);
	StudentDto patchStudent(Long theId, StudentDto studentDto);
	void deleteStudentByid(Long theId);
}
