package com.quangnv.uet.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quangnv.uet.dto.StudentDto;
import com.quangnv.uet.dto.StudentListDto;
import com.quangnv.uet.exception.ResourceNotFoundException;
import com.quangnv.uet.service.StudentService;

@RestController
@RequestMapping(value = "/student")
@CrossOrigin(value = "http://localhost:4200/")
public class StudentApi {
	@Autowired
	private StudentService studentService;

	@GetMapping(value = "/list")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StudentListDto> getListStudent(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(name = "key", defaultValue = "") String key) {
		StudentListDto studentListDto = studentService.getAllStudent(page, size, key);
		return new ResponseEntity<StudentListDto>(studentListDto, HttpStatus.OK);
	}

	@GetMapping(value = "/{studentId}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable("studentId") String studentId)
			throws ResourceNotFoundException {
		StudentDto studentDto = studentService.getStudentById(studentId);
		return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
	}

	@PostMapping(value = "/add")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<StudentDto> addStudent(@RequestBody StudentDto studentDto) {
		studentDto = studentService.addStudent(studentDto);
		return new ResponseEntity<StudentDto>(studentDto, HttpStatus.CREATED);

	}

	@PutMapping(value = "/edit")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<StudentDto> editStudent(@RequestBody StudentDto studentDto) {
		studentDto = studentService.editStudent(studentDto);
		return new ResponseEntity<StudentDto>(studentDto, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public void delete(@RequestParam("studentId") String studentId) {
		studentService.deleteStudent(studentId);
	}

}
