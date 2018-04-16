package com.example.api;

import com.example.bll.YouQingLinkBll;
import com.example.entity.YouQingLink;
import com.example.resource.BasicContext;
import com.example.util.MapConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/youqing")
public class YouQingLinkApi extends BasicContext {

    @Autowired
    private YouQingLinkBll youQingLinkBll;

    @GET
    @Path("list")
    public Map<String,Object> list(@Context UriInfo uriInfo)throws ParseException {
        MultivaluedMap<String,String> queryParams = uriInfo.getQueryParameters();
        Map<String,Object> args = this.youQingLinkBll.formatEntityMap(MapConverter.toStringMap(queryParams));

        Map<String, Object> result = new HashMap<String, Object>();

        List<YouQingLink> youQingLinkList = this.youQingLinkBll.findList(args);
        if(youQingLinkList == null || youQingLinkList.size() == 0){
            result.put("list", youQingLinkList);
            result.put("count", 0);
            return result;
        }

        long count = this.youQingLinkBll.count(args);
        result.put("list", youQingLinkList);
        result.put("count", count);
        return result;
    }

    @GET
    @Path("{id}")
    public YouQingLink detail(@PathParam("id") String id){
        return this.youQingLinkBll.findById(id);
    }

    @POST
    @Path("add")
    @Consumes("application/x-www-form-urlencoded")
    public Map<String, Object> add(MultivaluedMap<String, String> formParams){
        Map<String, Object> args = this.youQingLinkBll.formatEntityMap(MapConverter.toStringMap(formParams));

        String name = (String) args.get("name");
        name = name == null ? "" : name.trim();

        String href = (String) args.get("href");
        href = href == null ? "" : href.trim();

        String explain = (String) args.get("explain");
        explain = explain == null ? "" : explain.trim();

        String position = (String) args.get("position");
        position = position == null ? "" : position.trim();

        String viewStr = (String) args.get("view");
        boolean view = viewStr.equals("true");

        YouQingLink youQingLink = new YouQingLink();
        youQingLink.setName(name);
        youQingLink.setHref(href);
        youQingLink.setExplain(explain);
        youQingLink.setPosition(position);
        youQingLink.setView(view);

        this.youQingLinkBll.insert(youQingLink);

        Map<String, Object> resp = new HashMap<>();
        resp.put("data",youQingLink);
        return resp;
    }

    @PUT
    @Path("update/{id}")
    @Consumes("application/x-www-form-urlencoded")
    public Map<String, Object> update(@PathParam("id") String id, MultivaluedMap<String, String> formParams) {
        Map<String, Object> args = this.youQingLinkBll.formatEntityMap(MapConverter.toStringMap(formParams));
        args.put("id", id);
        this.youQingLinkBll.updateDynamic(args);
        Map<String, Object> resp = new HashMap<>();
        resp.put("STATUS", "SUCCESS");
        return resp;
    }

    @DELETE
    @Path("{id}")
    public Map<String, Object> delete(@PathParam("id") String id) {
        this.youQingLinkBll.delete(id);
        Map<String, Object> resp = new HashMap<>();
        resp.put("status", "SUCCESS");
        return resp;
    }
}
