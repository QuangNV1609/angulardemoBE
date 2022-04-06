package com.quangnv.uet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quangnv.uet.dto.StudentDto;
import com.quangnv.uet.dto.StudentListDto;
import com.quangnv.uet.entities.StudentEntity;
import com.quangnv.uet.exception.ResourceNotFoundException;
import com.quangnv.uet.repositories.StudentRepository;
import com.quangnv.uet.service.StudentService;

import lombok.extern.slf4j.Slf4j;

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
	public StudentDto editStudent(StudentDto studentDto) {
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

}
