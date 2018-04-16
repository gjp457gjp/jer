package com.example.bll;

import com.example.entity.Teacher;
import com.example.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TeacherBll {

    @Autowired
    private TeacherRepository teacherRepository;

    public List<Teacher> find(Map<String, Object> args) {
        return this.teacherRepository.find(args);
    }

    public long count(Map<String, Object> args) {
        return this.teacherRepository.count(args);
    }

    public int insert(Teacher teacher) {
        return this.teacherRepository.insert(teacher);
    }

    public Teacher findById(Integer id) {
        return this.teacherRepository.findById(id);
    }
    public Teacher findByTid(Integer tno) {
        return this.teacherRepository.findByTid(tno);
    }

    public int update(Teacher teacher) {
        return this.teacherRepository.update(teacher);
    }

    public int delete(String ids) {
            String[] idStr = ids.split(",");
                for (int i = 0; i < idStr.length; i++){
                    this.teacherRepository.delete(Integer.parseInt(idStr[i]));
                }
            return 1;
    }
}
