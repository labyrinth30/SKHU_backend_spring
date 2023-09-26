package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import net.skhu.dto.Student;
import net.skhu.mapper.StudentMapper;
import net.skhu.model.StudentEdit;

@Service
public class StudentService {
	@Autowired
	StudentMapper studentMapper;

	public StudentEdit findOne(int id) {
		Student studentDto = studentMapper.findOne(id);
		return toEditModel(studentDto);
	}

	public Student findByStudentNo(String studentNo) {
		return studentMapper.findByStudentNo(studentNo);
	}

	public List<Student> findAll() {
		return studentMapper.findAll();
	}

	public void insert(StudentEdit studentEdit) {
		Student student = toDto(studentEdit);
		studentMapper.insert(student);
	}

	public void update(StudentEdit studentEdit) {
		Student student = toDto(studentEdit);
		studentMapper.update(student);
	}

	public void delete(int id) {
		studentMapper.delete(id);
	}

	ModelMapper modelMapper = new ModelMapper();
	 public Student toDto(StudentEdit studentEdit) {
	 return modelMapper.map(studentEdit, Student.class);
	 }
	 public StudentEdit toEditModel(Student studentDto) {
	 return modelMapper.map(studentDto, StudentEdit.class);
	 }
	 
	public boolean hasErrors(StudentEdit studentEdit, BindingResult bindingResult) {
		 // 이 메소드는 studentEdit 모델 객체에 들어있는 데이터에 오류가 있으면 true 를 리턴해야 한다.
		 if (bindingResult.hasErrors()) return true;
		 // 이미 spring form validation 에러가 있다면 그냥 true 를 리턴한다.
		 Student student2 = findByStudentNo(studentEdit.getStudentNo());
		 if (student2 != null && student2.getId() != studentEdit.getId()) {
		 bindingResult.rejectValue("studentNo", null, "학번이 중복됩니다.");
		 return true; // 학번이 중복된다면 true 를 리턴한다.
		 }
		 return false; // 여기까지 왔다면 studentEdit 모델 객체의 데이터에 오류가 없으므로,
		// false 를 리턴한다.
		}
}