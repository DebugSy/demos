package com.shiy.yarn.client;


/**
 * Created by DebugSy on 2017/6/19.
 */
public class ResourceControlUtils {

    public static long getLongFromString(String str) {
        long resourceSize = 0L;

        if (str == null){
            return resourceSize;
        }

        if (str.toLowerCase().endsWith("m")) {
            resourceSize = Long.parseLong(str.substring(0, str.length() - 1));
        } else if (str.toLowerCase().endsWith("g")) {
            resourceSize = Long.parseLong(str.substring(0, str.length() - 1)) * 1024;
        } else if (str.toLowerCase().endsWith("t")) {
            resourceSize = Long.parseLong(str.substring(0, str.length() - 1)) * 1024 * 1024;
        } else {
            resourceSize = Long.parseLong(str);
        }
        return resourceSize;
    }

}
