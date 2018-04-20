package com.example.api;

import com.example.bll.ProjectBll;
import com.example.bll.StudentBll;
import com.example.bll.TeacherBll;
import com.example.entity.Project;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.util.BaseException;
import com.example.util.MapConverter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("project")
public class ProjectApi {

    @Autowired
    private ProjectBll projectBll;
    @Autowired
    private TeacherBll teacherBll;
    @Autowired
    private StudentBll studentBll;

    @GET
    @Path("list")
    public Map<String, Object> list(@QueryParam("title") String title
            , @QueryParam("currentPage") Integer currentPage, @QueryParam("pageSize") Integer pageSize) throws JSONException {
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(title)) {
            args.put("title", title);
        }
        if (currentPage != null && pageSize != null) {
            args.put("start", (currentPage - 1) * pageSize);
            args.put("rows", pageSize);
        }
        List<Project> projects = this.projectBll.find(args);
        List<Map<String, Object>> jsonArray = new ArrayList<>();
        for (Project project : projects) {
            Map<String, Object> jsonObject = new HashMap<>();
            Teacher teacher = this.teacherBll.findByTid(project.getTid());
            if (project.getTid() != null && teacher != null) {
                String tname = teacher.getTname();
                jsonObject.put("tid", teacher.getTno());
                jsonObject.put("tname", tname);
            }
            Student student = this.studentBll.findBySid(project.getSid());
            if (project.getSid() != null && student != null) {
                String sname = student.getSname();
                jsonObject.put("sname", sname);
            }
            jsonObject.put("id", project.getId());
            jsonObject.put("num", project.getNum());
            jsonObject.put("pdesc", project.getPdesc());
            jsonObject.put("score", project.getScore());
            jsonObject.put("title", project.getTitle());
            jsonObject.put("util", project.getUtil());
            jsonArray.add(jsonObject);
        }
        int total = (int) this.count(title);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", jsonArray);
        map.put("total", total);
        return map;
    }

    @GET
    @Path("pidTitle")
    public Map<String, Object> pidTitle() throws JSONException {
        Map<String, Object> args = new HashMap<String, Object>();
        List<Project> projects = this.projectBll.find(args);
        List<Map<String, Object>> jsonArray = new ArrayList<>();
        for (Project project : projects) {
            Map<String, Object> pro = new HashMap<>();
            String pid = project.getId().toString();
            String title = project.getTitle();
            pro.put("pid", pid);
            pro.put("title", title);
            jsonArray.add(pro);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", jsonArray);
        return map;
    }


    @GET
    @Path("count")
    public long count(@QueryParam("title") String title) {
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(title)) {
            args.put("title", title);
        }
        long count = this.projectBll.count(args);
        return count;
    }

    @POST
    @Path("add")
    public int addProject(@BeanParam Project project) throws BaseException {
        try {
            return this.projectBll.insert(project);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @GET
    @Path("{id}")
    public Map<String, Object> getProject(@PathParam("id") Integer id) throws BaseException {
        try {
            Project project = this.projectBll.findById(id);
            Map<String, Object> jsonObject = new HashMap<>();
            Student student = this.studentBll.findBySid(project.getSid());
            if (project.getSid() != null && student != null) {
                String sname = student.getSname();
                jsonObject.put("sname", sname);
            }
            jsonObject.put("tid", project.getTid());
            jsonObject.put("id", project.getId());
            jsonObject.put("num", project.getNum());
            jsonObject.put("pdesc", project.getPdesc());
            jsonObject.put("score", project.getScore());
            jsonObject.put("title", project.getTitle());
            jsonObject.put("util", project.getUtil());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", jsonObject);
            return map;
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("update/{id}")
    public int updateProject(@PathParam("id") Integer id, @BeanParam Project project, @Context UriInfo uriInfo) throws BaseException {
        try {
            project.setId(id);
            MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
            Map<String, Object> args = MapConverter.toObjectMap(queryParams);
            String sname = (String) args.get("sname");
            if(sname != null) {
                Student student = studentBll.findBySname(sname);
                project.setSid(student.getSno());
            }
            return this.projectBll.update(project);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("select/{id}")
    public Map<String, Object> selectProject(@PathParam("id") Integer id, @QueryParam("name") String name) throws BaseException {
        try {
            Map<String, Object> map = new HashMap<>();
            Student student = this.studentBll.findBySname(name);
            if (student == null || student.getPid() != null) {
                map.put("result", "error");
                map.put("msg", "学生不存在或已选择课题！");
            } else {
                student.setPid(id);
                this.studentBll.update(student);
                Long sid = student.getSno();
                Project project = this.projectBll.findById(id);
                project.setSid(sid);
                this.projectBll.update(project);
                map.put("result", "success");
                map.put("msg", "恭喜您已经选题成功");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("score/{id}")
    public Map<String, Object> scoreProject(@PathParam("id") Integer id, @QueryParam("score") Integer score) throws BaseException {
        try {
            Map<String, Object> map = new HashMap<>();
            Project project = this.projectBll.findById(id);
            project.setScore(score);
            int result = this.projectBll.update(project);
            if(result == 1){
                map.put("result", "success");
                map.put("msg", "录入成绩成功");
            } else {
                map.put("result", "error");
                map.put("msg", "录入成绩失败");
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }


    @POST
    @Path("delete")
    public int deleteProject(@QueryParam("ids") String ids) throws BaseException {
        try {
            return this.projectBll.delete(ids);
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }
}
