package net.skhu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.skhu.entity.Department;
import net.skhu.entity.Student;
import net.skhu.model.StudentEdit;
import net.skhu.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public StudentEdit findOne(int id) {
        Student studentEntity = studentRepository.findById(id).get();
        return toEditModel(studentEntity);
    }

    public Student findByStudentNo(String studentNo) {
        return studentRepository.findByStudentNo(studentNo);
    }

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public void insert(StudentEdit studentEdit) {
        Student student = toEntity(studentEdit);
        studentRepository.save(student);
    }

    public void update(StudentEdit studentEdit) {
        Student student = toEntity(studentEdit);
        studentRepository.save(student);
    }

    public void delete(int id) {
        studentRepository.deleteById(id);
    }

    public Student toEntity(StudentEdit studentEdit) {
        Student studentEntity = new Student();
        studentEntity.setId(studentEdit.getId());
        studentEntity.setStudentNo(studentEdit.getStudentNo());
        studentEntity.setName(studentEdit.getName());
        Department department = new Department();
        department.setId(studentEdit.getDepartmentId());
        studentEntity.setDepartment(department);
        studentEntity.setEmail(studentEdit.getEmail());
        studentEntity.setPhone(studentEdit.getPhone());
        studentEntity.setSex(studentEdit.getSex());
        return studentEntity;
    }

    public StudentEdit toEditModel(Student studentEntity) {
        StudentEdit studentEdit = new StudentEdit();
        studentEdit.setId(studentEntity.getId());
        studentEdit.setStudentNo(studentEntity.getStudentNo());
        studentEdit.setName(studentEntity.getName());
        studentEdit.setDepartmentId(studentEntity.getDepartment().getId());
        studentEdit.setEmail(studentEntity.getEmail());
        studentEdit.setPhone(studentEntity.getPhone());
        studentEdit.setSex(studentEntity.getSex());
        return studentEdit;
    }
}

