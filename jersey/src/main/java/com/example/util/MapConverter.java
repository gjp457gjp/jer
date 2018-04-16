package com.example.util;

import javax.ws.rs.core.MultivaluedMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxian'ge on 15/12/17.
 */
public class MapConverter {

    public static Map<String, String> toStringMap(MultivaluedMap<String, String> multivaluedMap) {

        if(multivaluedMap == null) {
            return new HashMap<String, String>();
        }

        Map<String, String> map = new HashMap<String, String>();

        for (Map.Entry<String, List<String>> e : multivaluedMap.entrySet()) {
            String key = e.getKey();
            String value = "";
            int i = 0;
            for (String v : e.getValue()) {
                if(i == 0) {
                    value = v;
                } else {
                    value += "," + v;
                }
                i++;
            }
            map.put(key, value);
        }

        return map;
    }
    public static Map<String, Object> toObjectMap(MultivaluedMap<String, String> multivaluedMap) {

        if(multivaluedMap == null) {
            return new HashMap<String, Object>();
        }

        Map<String, Object> map = new HashMap<String, Object>();

        for (Map.Entry<String, List<String>> e : multivaluedMap.entrySet()) {
            String key = e.getKey();
            String value = "";
            int i = 0;
            for (Object v : e.getValue()) {
                if(i == 0) {
                    value = (String)v;
                } else {
                    value += "," + v;
                }
                i++;
            }
            map.put(key, value);
        }

        return map;
    }

    public static Map<String, List<String>> toListMap(MultivaluedMap<String, String> multivaluedMap) {

        if(multivaluedMap == null) {
            return null;
        }

        Map<String, List<String>> map = new HashMap<String, List<String>>();

        for (Map.Entry<String, List<String>> e : multivaluedMap.entrySet()) {
            String key = e.getKey();
            List<String> value = e.getValue();
            map.put(key, value);
        }

        return map;
    }

    public static Map<String, Object> toMap(MultivaluedMap<String, String> multivaluedMap) {

        if(multivaluedMap == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<String, Object>();

        for (Map.Entry<String, List<String>> e : multivaluedMap.entrySet()) {
            String key = e.getKey();
            String value = "";
            int i = 0;
            for (String v : e.getValue()) {
                if(i == 0) {
                    value = v;
                } else {
                    value += "," + v;
                }
                i++;
            }
            map.put(key, value);
        }

        return map;
    }

}
