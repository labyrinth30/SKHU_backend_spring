package net.skhu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.skhu.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>  {

    List<Student> findBySugangsLectureTitle(String title);
    List<Student> findByDepartmentProfessorsName(String name);
}

