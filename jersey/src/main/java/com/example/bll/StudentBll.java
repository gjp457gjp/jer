package com.example.bll;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class StudentBll {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> find(Map<String, Object> args) {
        return this.studentRepository.find(args);
    }

    public long count(Map<String, Object> args) {
        return this.studentRepository.count(args);
    }

    public int insert(Student student) {
        return this.studentRepository.insert(student);
    }

    public Student findById(Integer id) {
        return this.studentRepository.findById(id);
    }

    public Student findBySid(Long sno) {
        return this.studentRepository.findBySid(sno);
    }

    public Student findBySname(String sname) {
        return this.studentRepository.findBySname(sname);
    }

    public int update(Student student) {
        return this.studentRepository.update(student);
    }

    public int delete(String ids) {
        String[] idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++){
            this.studentRepository.delete(Integer.parseInt(idStr[i]));
        }
        return 1;
    }
}
