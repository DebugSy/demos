package com.hive.demo.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class LowerUDF extends UDF {

    public Text evaluate(Text s){
        if (s == null)
            return null;
        return new Text(s.toString().toUpperCase());
    }

}
