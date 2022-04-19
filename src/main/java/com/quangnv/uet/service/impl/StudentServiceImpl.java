package com.quangnv.uet.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.quangnv.uet.dto.StudentDto;
import com.quangnv.uet.dto.StudentListDto;
import com.quangnv.uet.entities.StudentEntity;
import com.quangnv.uet.exception.ResourceNotFoundException;
import com.quangnv.uet.repositories.StudentRepository;
import com.quangnv.uet.service.StudentService;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public StudentDto addStudent(StudentDto studentDto) {
		StudentEntity studentEntity = modelMapper.map(studentDto, StudentEntity.class);
		studentEntity.setId(UUID.randomUUID().toString());
		studentEntity = studentRepository.save(studentEntity);
		log.info("Create new Student: " + studentEntity.toString());
		return modelMapper.map(studentEntity, StudentDto.class);
	}

	@Override
	@Transactional
	public StudentDto editStudent(StudentDto studentDto) {
		if (studentRepository.existsById(studentDto.getId())) {
			studentRepository.deleteById(studentDto.getId());
		}

		StudentEntity studentEntity = modelMapper.map(studentDto, StudentEntity.class);
		studentEntity = studentRepository.save(studentEntity);
		log.info("Edit student with studentId: " + studentDto.getId());
		return modelMapper.map(studentEntity, StudentDto.class);
	}

	@Override
	public void deleteStudent(String studentId) {
		studentRepository.deleteById(studentId);
		log.info("Delete student with id: " + studentId);
	}

	@Override
	public StudentListDto getAllStudent(int page, int size, String key) {
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<StudentEntity> studentEntities = null;
		if (key.length() == 0) {
			studentEntities = studentRepository.findAll(pageable);
		} else {
			studentEntities = studentRepository.findStudentByKey(key, pageable);
		}
		List<StudentDto> studentDtos = new ArrayList<StudentDto>();
		for (StudentEntity studentEntity : studentEntities) {
			StudentDto studentDto = modelMapper.map(studentEntity, StudentDto.class);
			studentDtos.add(studentDto);
		}
		StudentListDto studentListDto = new StudentListDto();
		studentListDto.setStudentList(studentDtos);
		if (key.length() == 0) {
			studentListDto.setTotalPage((int) (Math.ceil((double) studentRepository.count() / size)));
		} else {
			studentListDto.setTotalPage((int) (Math.ceil((double) studentRepository.countByKey(key) / size)));
		}
		return studentListDto;
	}

	@Override
	public StudentDto getStudentById(String studentId) throws ResourceNotFoundException {
		Optional<StudentEntity> studentEntity = studentRepository.findById(studentId);
		if (studentEntity.isPresent()) {
			return modelMapper.map(studentEntity, StudentDto.class);
		} else {
			throw new ResourceNotFoundException(studentId + " not found!");
		}
	}

	@Override
	public String exportReport(String reportFormat) throws FileNotFoundException, JRException {
		String path = "C:\\Users\\asus\\OneDrive\\Máy tính";
		List<StudentEntity> studentList = studentRepository.findAll();
		File file = ResourceUtils.getFile("classpath:reports/student.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(studentList);
		Map<String, Object> parmeters = new HashMap<String, Object>();
		parmeters.put("createBy", "Java Techie");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parmeters, dataSource);
		String downloadUri = null;
		if (reportFormat.equalsIgnoreCase("html")) {
			downloadUri = "http://localhost:8080/student/download/students.html";
			JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\students.html");
		}
		if (reportFormat.equalsIgnoreCase("pdf")) {
			downloadUri = "http://localhost:8080/student/download/students.pdf";
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\students.pdf");
		}
		return downloadUri;
	}

}
