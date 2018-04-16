package com.example.bll;

import com.example.entity.YouQingLink;
import com.example.repository.BaseMgRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class YouQingLinkBll {

    @Autowired
    private BaseMgRepository baseMgRepository;

    public List<YouQingLink> findList(Map<String, Object> args) {
        Query query = new Query();

        if (args.containsKey("name")) {
            query.addCriteria(new Criteria("name").regex(".*?" + (String) args.get("name") + ".*"));
        }
        if (args.containsKey("href")) {
            query.addCriteria(new Criteria("href").regex(".*?" + (String) args.get("href") + ".*"));
        }
        if (args.containsKey("explain")) {
            query.addCriteria(new Criteria("explain").regex(".*?" + (String) args.get("explain") + ".*"));
        }
        if (args.containsKey("position")) {
            query.addCriteria(new Criteria("position").regex(".*?" + (String) args.get("position") + ".*"));
        }
        if (args.containsKey("view")) {
            query.addCriteria(new Criteria("view").in(true , false));
        }
        if(args.containsKey("sorter")) {
            String sorter = (String)args.get("sorter");
            String[] s = sorter.split(" ");
            String key = s[0];
            String direct = s[1];
            if("ASC".equals(direct)) {
                Sort.Order order = new Sort.Order(Sort.Direction.ASC, key);
                query.with(new Sort(order));
            } else {
                Sort.Order order = new Sort.Order(Sort.Direction.DESC, key);
                query.with(new Sort(order));
            }
        } else {
            Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
            query.with(new Sort(order));
        }
        return this.baseMgRepository.findList(query, YouQingLink.class);
    }
    public long count(Map<String,Object> args) {
        Query query = new Query();

        if (args.containsKey("name")) {
            query.addCriteria(new Criteria("name").regex(".*?" + (String) args.get("name") + ".*"));
        }
        if (args.containsKey("href")) {
            query.addCriteria(new Criteria("href").regex(".*?" + (String) args.get("href") + ".*"));
        }
        if (args.containsKey("explain")) {
            query.addCriteria(new Criteria("explain").regex(".*?" + (String) args.get("explain") + ".*"));
        }
        if (args.containsKey("position")) {
            query.addCriteria(new Criteria("position").regex(".*?" + (String) args.get("position") + ".*"));
        }
        if (args.containsKey("view")) {
            query.addCriteria(new Criteria("view").in(true , false));
        }
        return this.baseMgRepository.count(query, YouQingLink.class);
    }

    public YouQingLink findById(String id){
        Query query = new Query();
        query.addCriteria(new Criteria("id").is(id));
        return this.baseMgRepository.findOne(query, YouQingLink.class);
    }

    public void insert(YouQingLink youQingLink){
        this.baseMgRepository.insert(youQingLink);
    }

    //修改
    public void updateDynamic(Map<String, Object> args){
        Query query=new Query();
        query.addCriteria(new Criteria("_id").is(args.get("id")));
        Update update=new Update();
        for(Map.Entry<String, Object> entry : args.entrySet()) {
            String key = entry.getKey();
            if("id".equalsIgnoreCase(key)) {
                continue;
            }
            update.set(key, entry.getValue());
        }
        this.baseMgRepository.update(query, update, YouQingLink.class);
    }

    public void delete(String id){
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(id));
        this.baseMgRepository.delete(query, YouQingLink.class);
    }

    public Map<String, Object> formatEntityMap(Map<String, String> map) {
        Map<String,Object> entityMap = new HashMap<String, Object>();

        if(map == null){
            return entityMap;
        }

        for(Map.Entry<String, String> entry : map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if(StringUtils.isEmpty(value)){
                continue;
            }
            if("start".equals(key)){
                entityMap.put(key, Integer.parseInt(value));
                continue;
            }
            if("rows".equals(key)){
                entityMap.put(key, Integer.parseInt(value));
                continue;
            }

            entityMap.put(key, value);
        }
        return entityMap;
    }
}
