package com.example.api;


import com.example.bll.UserBll;
import com.example.bll.UserInfoBll;
import com.example.entity.User;
import com.example.entity.UserInfo;
import com.example.util.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("user")
public class UserApi {

    @Autowired
    private UserBll userBll;
    @Autowired
    private UserInfoBll userInfoBll;

    @GET
    @Path("list")
    public List<User> list(@QueryParam("username") String username, @QueryParam("pwd") String pwd, @QueryParam("state") Integer state
            , @QueryParam("url") String url, @QueryParam("start") Integer start, @QueryParam("rows") Integer rows){
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(username)) {
            args.put("username", username);
        }
        if (StringUtils.isNotEmpty(pwd)) {
            args.put("pwd", pwd);
        }
        if (state != null) {
            args.put("state", state);
        }
        if (StringUtils.isNotEmpty(url)) {
            args.put("url", url);
        }
        if (start != null && rows != null) {
            args.put("start", start);
            args.put("rows", rows);
        }
        List<User> users = this.userBll.find(args);
        return users;
    }

    @GET
    @Path("count")
    public long count(@QueryParam("username") String username, @QueryParam("pwd") String pwd, @QueryParam("state") Integer state
            , @QueryParam("url") String url, @QueryParam("start") Integer start, @QueryParam("rows") Integer rows){
        Map<String, Object> args = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(username)) {
            args.put("username", username);
        }
        if (StringUtils.isNotEmpty(pwd)) {
            args.put("pwd", pwd);
        }
        if (state != null) {
            args.put("state", state);
        }
        if (StringUtils.isNotEmpty(url)) {
            args.put("url", url);
        }
        if (start != null && rows != null) {
            args.put("start", start);
            args.put("rows", rows);
        }
        long count = this.userBll.count(args);
        return count;
    }

    @POST
    @Path("login")
    public Map<String, Object> login(@QueryParam("username") String userName, @QueryParam("password") String password, @QueryParam("state") Integer state) throws  BaseException {
        try {
            Map<String, Object> map = new HashMap<>();
            User user = this.userBll.findByUserName(userName);
            if(user != null && user.getPwd() != null && user.getState() != null){
                if(user.getPwd().equals(password) && state == user.getState()){
                    map.put("result", "success");
                    map.put("msg", "登录成功！");
                } else {
                    map.put("result", "error");
                    map.put("msg", "登录失败，密码错误或身份认证失败！");
                }
            }else {
                map.put("result", "error");
                map.put("msg", "用户不存在！");
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @POST
    @Path("changePwd")
    public Map<String, Object> changePwd(@QueryParam("username") String userName, @QueryParam("oldPassword") String oldPassword, @QueryParam("newPassword1") String newPassword) throws  BaseException {
        try {
            Map<String, Object> map = new HashMap<>();
            User user = this.userBll.findByUserName(userName);
            if(user != null && user.getPwd() != null){
                if(user.getPwd() == oldPassword) {
                    user.setPwd(newPassword);
                    this.userBll.update(user);
                    map.put("status", 1);
                    map.put("msg", "修改密码成功！请重新登录");
                } else {
                    map.put("status", 0);
                    map.put("msg", "修改密码失败！");
                }
            }else {
                map.put("status", 0);
                map.put("msg", "用户不存在！");
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }


    @POST
    @Path("add")
    public Map<String, Object> addUser(@QueryParam("name") String name, @QueryParam("newPassword1") String password, @QueryParam("state") String state) throws BaseException {
        try {
            Map<String, Object> map = new HashMap<>();
            User user1 = this.userBll.findByUserName(name);
            if(user1 == null) {
                User user = new User();
                user.setPwd(password);
                user.setUsername(name);
                user.setState(Integer.parseInt(state));
                int result = this.userBll.insert(user);
                if(result == 1) {
                    map.put("status", 1);
                    map.put("msg", "注册成功");
                }else {
                    map.put("status", 0);
                    map.put("msg", "注册失败！");
                }
            }else {
                map.put("status", 0);
                map.put("msg", "用户已存在！");
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @GET
    @Path("{id}")
    public User getUser(@PathParam("id") Integer id) throws BaseException {
        try {
            return this.userBll.findById(id);
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }

    @PUT
    @Path("update/{id}")
    public int updateUser(@PathParam("id") Integer id, @BeanParam User user) throws BaseException {
        try {
            user.setId(id);
            return this.userBll.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BaseException(e, (long) 0);
        }
    }

    @DELETE
    @Path("delete/{id}")
    public int deleteUser(@PathParam("id") Integer id) throws BaseException {
        try {
            return this.userBll.delete(id);
        } catch (Exception e) {
            throw new BaseException(e, (long) 0);
        }
    }
}
