package com.quangnv.uet.service;

import java.io.FileNotFoundException;

import com.quangnv.uet.dto.StudentDto;
import com.quangnv.uet.dto.StudentListDto;
import com.quangnv.uet.exception.ResourceNotFoundException;

import net.sf.jasperreports.engine.JRException;

public interface StudentService {

	public StudentDto addStudent(StudentDto studentDto);

	public StudentDto editStudent(StudentDto studentDto);

	public void deleteStudent(String studentId);

	public StudentListDto getAllStudent(int page, int size, String key);

	public StudentDto getStudentById(String studentId) throws ResourceNotFoundException;

	public String exportReport(String format) throws FileNotFoundException, JRException;

}
