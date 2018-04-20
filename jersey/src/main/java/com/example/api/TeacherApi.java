package com.example.api;


import com.example.bll.ProjectBll;
import com.example.bll.TeacherBll;
import com.example.entity.Project;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.util.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("teacher")
public class TeacherApi {

    @Autowired
    private TeacherBll teacherBll;
    @Autowired
    private ProjectBll projectBll;

    @GET
    @Path("list")
    public Map<String, Object> list(@QueryParam("tname") String tname
            , @QueryParam("currentPage") Integer currentPage, @QueryParam("pageSize") Integer pageSize){
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(tname)) {
            args.put("tname", tname);
        }
        if (currentPage != null && pageSize != null) {
            args.put("start", (currentPage-1)*pageSize);
            args.put("rows", pageSize);
        }
        List<Teacher> teachers = this.teacherBll.find(args);
        List<Map<String, Object>> jsonArray = new ArrayList<>();
        for(Teacher teacher : teachers){
            Map<String, Object> jsonObject = new HashMap<>();
            String[] pids = teacher.getPids().split(",");
            String titles = "";
            for(String pid : pids) {
                Project project =this.projectBll.findById(Integer.parseInt(pid));
                if(project.getTitle() != null && project != null){
                    String title = project.getTitle();
                    titles += title + ",";
                }
            }
            jsonObject.put("id",teacher.getId());
            jsonObject.put("tno",teacher.getTno());
            jsonObject.put("tname",teacher.getTname());
            jsonObject.put("phone",teacher.getPhone());
            jsonObject.put("qqnum",teacher.getQqnum());
            jsonObject.put("pids",titles.substring(0,titles.length()-1));
            jsonArray.add(jsonObject);
        }
        int total = (int) this.count(tname);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", jsonArray);
        map.put("total",total);
        return map;
    }

    @GET
    @Path("count")
    public long count(@QueryParam("tname") String tname){
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(tname)) {
            args.put("tname", tname);
        }
        long count = this.teacherBll.count(args);
        return count;
    }

    @POST
    @Path("add")
    @Consumes("application/json")
    public int addTeacher(@BeanParam Teacher teacher) throws BaseException {
        try {
            return this.teacherBll.insert(teacher);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @GET
    @Path("{id}")
    public Map<String, Object> getTeacher(@PathParam("id") Integer id) throws BaseException {
        try {
            Teacher teacher = this.teacherBll.findById(id);
            String pids[] = teacher.getPids().split(",");
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> jsonObject = new HashMap<>();
            String titles = "";
            for(String pid : pids) {
                Project project =this.projectBll.findById(Integer.parseInt(pid));
                if(project.getTitle() != null && project != null){
                    String title = project.getTitle();
                    titles += title + ",";
                }
            }
            jsonObject.put("id",teacher.getId());
            jsonObject.put("tno",teacher.getTno());
            jsonObject.put("tname",teacher.getTname());
            jsonObject.put("phone",teacher.getPhone());
            jsonObject.put("qqnum",teacher.getQqnum());
            jsonObject.put("pidTitle",titles.substring(0,titles.length()-1));
            jsonObject.put("pids",teacher.getPids().split(",") );
            map.put("result",jsonObject);
            return map;
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("details/{tid}")
    public Map<String, Object> getOneTeacher(@PathParam("tid") Integer tid) throws BaseException {
        try {
            Teacher teacher = this.teacherBll.findByTid(tid);
            String pids[] = teacher.getPids().split(",");
            String titles = "";
            for(String pid : pids) {
                Project project = this.projectBll.findById(Integer.parseInt(pid));
                if (project.getTitle() != null && project != null) {
                    String title = project.getTitle();
                    titles += title + ",";
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("id",teacher.getId());
            jsonObject.put("tno",teacher.getTno());
            jsonObject.put("tname",teacher.getTname());
            jsonObject.put("phone",teacher.getPhone());
            jsonObject.put("qqnum",teacher.getQqnum());
            jsonObject.put("pids",titles.substring(0,titles.length()-1));
            map.put("result",jsonObject);
            return map;
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("update/{id}")
    @Consumes("application/json")
    public int updateTeacher(@PathParam("id") Integer id, @BeanParam Teacher teacher) throws BaseException {
        try {
            teacher.setId(id);
            return this.teacherBll.update(teacher);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("delete")
    public int deleteTeacher(@QueryParam("ids") String ids) throws BaseException {
        try {
            return this.teacherBll.delete(ids);
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }
}
