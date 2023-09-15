package net.skhu.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.dto.Professor;
import net.skhu.mapper.ProfessorMapper;
import net.skhu.model.ProfessorEdit;

@Service
public class ProfessorService {
	@Autowired
	ProfessorMapper professorMapper;
	ModelMapper modelMapper = new ModelMapper();

	public ProfessorEdit findOne(int id) {
		Professor professorDto = professorMapper.findOne(id);
		return toEditModel(professorDto);
	}

	public Professor findByPhone(String phone) {
		return professorMapper.findByPhone(phone);
	}

	public List<Professor> findAll() {
		return professorMapper.findAll();
	}

	public void insert(ProfessorEdit professorEdit) {
		Professor student = toDto(professorEdit);
		professorMapper.insert(student);
	}

	public void update(ProfessorEdit professorEdit) {
		Professor professor = toDto(professorEdit);
		professorMapper.update(professor);
	}

	public void delete(int id) {
		professorMapper.delete(id);
	}
	public boolean hasErrors(ProfessorEdit professorEdit, BindingResult bindingResult) {
		 // 이 메소드는 studentEdit 모델 객체에 들어있는 데이터에 오류가 있으면 true 를 리턴해야 한다.
		 if (bindingResult.hasErrors()) return true;
		 // 이미 spring form validation 에러가 있다면 그냥 true 를 리턴한다.
		 Professor professor2 = findByPhone(professorEdit.getPhone());
		 if (professor2 != null && professor2.getPhone() != professorEdit.getPhone()) {
		 bindingResult.rejectValue("phone", null, "전화번호가 중복됩니다.");
		 return true; // 학번이 중복된다면 true 를 리턴한다.
		 }
		 return false;
	}

	public Professor toDto(ProfessorEdit professorEdit) {
		return modelMapper.map(professorEdit, Professor.class);
	}

	public ProfessorEdit toEditModel(Professor professorDto) {
		return modelMapper.map(professorDto, ProfessorEdit.class);
	}
}