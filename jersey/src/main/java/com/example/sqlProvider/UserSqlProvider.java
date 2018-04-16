package com.example.sqlProvider;

import com.example.entity.User;

import java.util.Map;

public class UserSqlProvider {

    public String query(Map<String, Object> args) {

        String select = "SELECT * FROM user WHERE 1 = 1";

        if(args == null) {
            return select;
        }

        String where = "";

        where += this.getCommonQueryWhere(args);

        String limit = null;
        if(args.containsKey("start") && args.containsKey("rows")){
            limit = ((Integer) args.get("start")).intValue() + ", " + ((Integer)args.get("rows")).intValue();

        }
        limit = (limit == null) ? "" : (" LIMIT " + limit);

//        System.out.println("base_info_category.query--------------------" + select + where + limit);

        return select + where + limit;
    }

    public String count(Map<String, Object> args){
        String sql = query(args);
        int idx = sql.lastIndexOf(" LIMIT ");
        if(idx != -1) {
            sql.substring(0, idx);
        }
        return sql.replace("*", "COUNT(1)");
    }



    private String getCommonQueryWhere(Map<String, Object> args) {
        String where = "";

        if(args.containsKey("username")) {
            where += " AND username = #{username}";
        }

        if(args.containsKey("pwd")) {
            where += " AND pwd = #{pwd}";
        }

        if(args.containsKey("state")) {
            where += " AND state = #{state}";
        }

        if(args.containsKey("url")) {
            where += " AND url = #{url}";
        }
        return where;
    }

}
