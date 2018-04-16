package com.example.sqlProvider;

import java.util.Map;

public class TeacherSqlProvider {

    public String query(Map<String, Object> args) {

        String select = "SELECT * FROM teacher WHERE 1 = 1";

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

        if(args.containsKey("tno")) {
            where += " AND tno = #{tno}";
        }

        if(args.containsKey("tname")) {
            where += " AND tname like'%";
            where += args.get("tname");
            where += "%'";
        }

        if(args.containsKey("qqnum")) {
            where += " AND qqnum = #{qqnum}";
        }

        if(args.containsKey("phone")) {
            where += " AND phone = #{phone}";
        }

        if(args.containsKey("pids")) {
            where += " AND pids = #{pids}";
        }
        return where;
    }

}
