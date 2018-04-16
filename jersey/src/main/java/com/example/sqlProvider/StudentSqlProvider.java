package com.example.sqlProvider;

import java.util.Map;

public class StudentSqlProvider {

    public String query(Map<String, Object> args) {

        String select = "SELECT * FROM student WHERE 1 = 1";

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

        if(args.containsKey("sno")) {
            where += " AND sno = #{sno}";
        }

        if(args.containsKey("sname")) {
            where += " AND sname like'%";
            where += args.get("sname");
            where += "%'";
        }

        if(args.containsKey("title")) {
            where += " AND title = #{title}";
        }

        if(args.containsKey("age")) {
            where += " AND age = #{age}";
        }

        if(args.containsKey("sex")) {
            where += " AND sex = #{sex}";
        }

        if(args.containsKey("major")) {
            where += " AND major = #{major}";
        }

        if(args.containsKey("qqnum")) {
            where += " AND qqnum = #{qqnum}";
        }

        if(args.containsKey("phone")) {
            where += " AND phone = #{phone}";
        }

        if(args.containsKey("pid")) {
            where += " AND pid = #{pid}";
        }
        return where;
    }

}
