package com.example.bll;

import com.example.entity.Project;
import com.example.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProjectBll {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> find(Map<String, Object> args) {
        return this.projectRepository.find(args);
    }

    public long count(Map<String, Object> args) {
        return this.projectRepository.count(args);
    }

    public int insert(Project project) {
        return this.projectRepository.insert(project);
    }

    public Project findById(Integer id) {
        return this.projectRepository.findById(id);
    }

    public int update(Project project) {
        return this.projectRepository.update(project);
    }

    public int delete(String ids) {
        String[] idStr = ids.split(",");
        for (int i = 0; i < idStr.length; i++){
            this.projectRepository.delete(Integer.parseInt(idStr[i]));
        }
        return 1;
    }
}
