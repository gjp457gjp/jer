package com.example.repository;

import com.mongodb.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Created by fangxiao on 15/10/10.
 * <p/>
 * 实现MongoDb查询的基类
 */
@Component
public class BaseMgRepository {

    @Autowired
    public MongoTemplate mongoTemplate;



    //查询所有
    public <T> List<T> findAll(Class<T> cls) {
        return this.mongoTemplate.findAll(cls);
    }

    ;

    //查询符合条件的所有
    public <T> List<T> findList(Query query, Class<T> cls) {
        return this.mongoTemplate.find(query, cls);
    }

    ;

    public <T> List<T> findListDistinct(Query query, String collection, String distinctKey) {
        return mongoTemplate.getCollection(collection).distinct(distinctKey, query.getQueryObject());
    }

    //查询符合条件的某个
    public <T> T findOne(Query query, Class<T> cls) {
        return this.mongoTemplate.findOne(query, cls);
    }

    ;

    //插入一条数据
    public <T> void insert(T cls) {
//        ObjectId id = new ObjectId();
//        documentBuilder.add("_id", id);
        this.mongoTemplate.insert(cls);
    }

    ;

    //保存一条数据      有则改之,无则加之
    public <T> void save(T cls) {
//        ObjectId id = new ObjectId();
//        documentBuilder.add("_id", id);
        this.mongoTemplate.save(cls);
    }

    ;
    //批量保存数据
    public <T> void insertAll(Collection<T> list) {
        this.mongoTemplate.insertAll(list);
    }




    //更新数据
    public <T> WriteResult update(Query query, Update update, Class<T> cls) {
        return this.mongoTemplate.updateMulti(query, update, cls);
    }

    ;

    //更新数据
    public <T> WriteResult updateMulti(Query query, Update update, Class<T> cls) {
        return this.mongoTemplate.updateMulti(query, update, cls);
    }

    ;

    //查找并更新数据
    public <T> T findAndModify(Query query, Update update, Class<T> cls) {
        return this.mongoTemplate.findAndModify(query, update, cls);
    }

    ;


    //删除数据
    public <T> void delete(Query query, Class<T> cls) {

        this.mongoTemplate.remove(query, cls);
    }

    ;

    //查找并删除数据
    public <T> T findAndRemove(Query query, Class<T> cls) {
        return this.mongoTemplate.findAndRemove(query, cls);
    }

    ;

    public long count(Query query, Class cls) {
        return this.mongoTemplate.count(query, cls);
    }

    ;

    public long countDistinct(Query query, String collection, String distinctKey) {
        return mongoTemplate.getCollection(collection).distinct(distinctKey, query.getQueryObject()).size();
    }


}
