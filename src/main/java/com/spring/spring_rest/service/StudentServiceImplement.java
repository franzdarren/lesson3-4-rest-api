package com.spring.spring_rest.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.spring.spring_rest.dto.StudentDto;
import com.spring.spring_rest.entity.Student;
import com.spring.spring_rest.exception.StudentNotFoundException;
import com.spring.spring_rest.mapper.StudentMapper;
import com.spring.spring_rest.repository.StudentRepository;

@Service
public class StudentServiceImplement implements StudentService {

	private StudentRepository studentRepo;
	
	public StudentServiceImplement(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}
	
	@Override
	public StudentDto createStudent(StudentDto theStudentDto) {
		Student theStudent = StudentMapper.mapToStudent(theStudentDto);
		Student saved = studentRepo.save(theStudent);
		return StudentMapper.mapToStudentDto(saved);
	}

	public StudentRepository getStudentRepo() {
		return studentRepo;
	}

	public void setStudentRepo(StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
	}

	@Override
	public StudentDto getStudentById(Long theId) {
		Student student = studentRepo.findById(theId)
				.orElseThrow(()->
								new StudentNotFoundException("Student Id not found"));
		return StudentMapper.mapToStudentDto(student);
	}

	@Override
	public List<StudentDto> getAllStudents() {
		List<Student> students = studentRepo.findAll();
		return students.stream().map(
				(student)-> StudentMapper.mapToStudentDto(student))
				.collect(Collectors.toList());
	}

	@Override
	public StudentDto updateStudent(Long theId, StudentDto studentDto) {
		Student existingStudent = studentRepo.findById(theId)
				.orElseThrow(() -> new StudentNotFoundException
						("Student Id not found: "+theId));

		existingStudent.setFirstName(studentDto.getFirstName());
		existingStudent.setLastName(studentDto.getLastName());
		existingStudent.setEmail(studentDto.getEmail());

		Student updatedStudent = studentRepo.save(existingStudent);

		return StudentMapper.mapToStudentDto(updatedStudent);
	}

	@Override
	public StudentDto patchStudent(Long theId, StudentDto studentDto) {
		Student student = studentRepo.findById(theId)
				.orElseThrow(() -> new StudentNotFoundException(
						"Student Id not found: " + theId));
		
		if (studentDto.getFirstName() != null) {
			student.setFirstName(studentDto.getFirstName());
		}
		
		if (studentDto.getLastName() != null) {
			student.setLastName(studentDto.getLastName());
		}
		
		if (studentDto.getEmail() != null) {
			student.setEmail(studentDto.getEmail());
		}
		
		Student saved = studentRepo.save(student);
		return StudentMapper.mapToStudentDto(saved);
	}

	@Override
	public void deleteStudentByid(Long theId) {
		studentRepo.findById(theId)
		.orElseThrow(() -> new StudentNotFoundException(
				"Student Id not found: " + theId));
		
		studentRepo.deleteById(theId);
	}	

}
