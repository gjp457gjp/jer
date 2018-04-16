package com.example.api;


import com.example.bll.ProjectBll;
import com.example.bll.StudentBll;
import com.example.entity.Project;
import com.example.entity.Student;
import com.example.entity.Teacher;
import com.example.util.BaseException;
import com.example.util.ExcelUtil;
import com.example.util.MapConverter;
import com.mongodb.util.JSON;
import com.sun.jersey.spi.container.ContainerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Path("student")
public class StudentApi {

    @Autowired
    private StudentBll studentBll;

    @Autowired
    private ProjectBll projectBll;

    @GET
    @Path("list")
    public Map<String, Object> list(@QueryParam("sname") String sname
            , @QueryParam("currentPage") Integer currentPage, @QueryParam("pageSize") Integer pageSize) {
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(sname)) {
            args.put("sname", sname);
        }
        if (currentPage != null && pageSize != null) {
            args.put("start", (currentPage - 1) * pageSize);
            args.put("rows", pageSize);
        }
        List<Student> students = this.studentBll.find(args);

        List<Map<String, Object>> jsonArray = new ArrayList<>();
        for(Student student : students){
            Map<String, Object> jsonObject = new HashMap<>();
            Project project = this.projectBll.findById(student.getPid());
            if(student.getPid() != null && project != null){
                String title =project.getTitle();
                jsonObject.put("pid", student.getPid());
                jsonObject.put("title",title);
            }
            jsonObject.put("id",student.getId());
            jsonObject.put("sno",student.getSno());
            jsonObject.put("sname",student.getSname());
            jsonObject.put("sex",student.getSex());
            jsonObject.put("age",student.getAge());
            jsonObject.put("phone",student.getPhone());
            jsonObject.put("qqnum",student.getQqnum());
            jsonObject.put("major",student.getMajor());

            jsonArray.add(jsonObject);
        }
        int total = (int) this.count(sname);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", jsonArray);
        map.put("total", total);
        return map;
    }

    public StudentApi() {
        super();
    }

    @GET
    @Path("count")
    public long count(@QueryParam("sname") String sname) {
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(sname)) {
            args.put("sname", sname);
        }
        long count = this.studentBll.count(args);
        return count;
    }

    @POST
    @Path("add")
    public int addStudent(@BeanParam Student student) throws BaseException {
        try {
            return this.studentBll.insert(student);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @GET
    @Path("details/{id}")
    public Map<String, Object> getStudent(@PathParam("id") Integer id) throws BaseException {
        try {
            Map<String, Object> jsonObject = new HashMap<>();
            Student student = this.studentBll.findById(id);
            Project project = this.projectBll.findById(student.getPid());
            if(student.getPid() != null && project != null){
                jsonObject.put("pid", student.getPid());
                jsonObject.put("title",project.getTitle());
            }
            jsonObject.put("id",student.getId());
            jsonObject.put("sno",student.getSno());
            jsonObject.put("sname",student.getSname());
            jsonObject.put("sex",student.getSex());
            jsonObject.put("age",student.getAge());
            jsonObject.put("phone",student.getPhone());
            jsonObject.put("qqnum",student.getQqnum());
            jsonObject.put("major",student.getMajor());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", jsonObject);
            return map;
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("details/{sno}")
    public Map<String, Object> getStudent(@PathParam("sno") Long sno) throws BaseException {
        try {
            Map<String, Object> jsonObject = new HashMap<>();
            Student student = this.studentBll.findBySid(sno);
            Project project = this.projectBll.findById(student.getPid());
            if(student.getPid() != null && project != null){
                jsonObject.put("pid", student.getPid());
                jsonObject.put("title",project.getTitle());
            }
            jsonObject.put("id",student.getId());
            jsonObject.put("sno",student.getSno());
            jsonObject.put("sname",student.getSname());
            jsonObject.put("sex",student.getSex());
            jsonObject.put("age",student.getAge());
            jsonObject.put("phone",student.getPhone());
            jsonObject.put("qqnum",student.getQqnum());
            jsonObject.put("major",student.getMajor());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", jsonObject);
            return map;
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @GET
    @Path("search/{sname}")
    public Student getStudent(@PathParam("sname") String sname) throws BaseException {
        try {
            return this.studentBll.findBySname(sname);
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("update/{id}")
    public int updateStudent(@PathParam("id") Integer id, @BeanParam Student student,@Context UriInfo uriInfo) throws BaseException {
        try {
            student.setId(id);
            return this.studentBll.update(student);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }


    @POST
    @Path("delete")
    public int deleteTeacher(@QueryParam("ids") String ids) throws BaseException {
        try {
            return this.studentBll.delete(ids);
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @GET
    @Path("export")
    public void export(@QueryParam("sname") String sname, @Context HttpServletResponse response) throws IOException {
        try {
            Map<String, Object> args = new HashMap<String, Object>();
            if (StringUtils.isNotEmpty(sname)) {
                args.put("sname", sname);
            }
            List<Student> students = this.studentBll.find(args);
            if (students.size() > 0) {
                //标题
                List<String> titles = new ArrayList<String>();
                titles.add("学号");
                titles.add("学生姓名");
                titles.add("年龄");
                titles.add("性别");
                titles.add("专业");
                titles.add("QQ号码");
                titles.add("手机号码");
                titles.add("课题");
                List<List<String>> datas = new ArrayList<List<String>>();
                //数据
                for (int i = 0; i < students.size(); i++) {
                    List<String> data = new ArrayList<String>();
                    Student student = students.get(i);

                    data.add(student.getSno().toString());
                    data.add(student.getSname());
                    data.add(student.getAge().toString());
                    data.add(student.getSex() == 0 ? "男" : "女");
                    data.add(student.getMajor());
                    data.add(student.getQqnum().toString());
                    data.add(student.getPhone().toString());
                    Project project = projectBll.findById(student.getPid());
                    if(project != null){
                        data.add(project.getTitle());
                    }
                    datas.add(data);
                }
                ExcelUtil.excelExportExcel(titles, datas, response);
            } else {
                PrintWriter writer = response.getWriter();
                writer.write("失败");
                writer.flush();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            PrintWriter writer = response.getWriter();
            writer.write("失败");
            writer.flush();
        }
    }
}
