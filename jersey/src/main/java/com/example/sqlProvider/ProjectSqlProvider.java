package com.example.sqlProvider;

import java.util.Map;

public class ProjectSqlProvider {

    public String query(Map<String, Object> args) {

        String select = "SELECT * FROM project WHERE 1 = 1";

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

        if(args.containsKey("id")) {
            where += " AND id = #{id}";
        }

        if(args.containsKey("title")) {
            where += " AND title like'%";
            where += args.get("title");
            where += "%'";
        }

        if(args.containsKey("pdesc")) {
            where += " AND pdesc = #{pdesc}";
        }

        if(args.containsKey("util")) {
            where += " AND util = #{util}";
        }

        if(args.containsKey("tid")) {
            where += " AND tid = #{tid}";
        }

        if(args.containsKey("sid")) {
            where += " AND sid = #{sid}";
        }

        if(args.containsKey("num")) {
            where += " AND num = #{num}";
        }

        if(args.containsKey("score")) {
            where += " AND score = #{score}";
        }
        return where;
    }

}
