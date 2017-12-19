package com.java.demo.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ThinkPad on 2017/8/7.
 */
public class JacksonMain {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"id\" : \"2328e894-eac0-44c8-907b-967d30789706\",\n" +
                "  \"name\" : \"shiy_test_client_workflow\",\n" +
                "  \"flowType\" : \"workflow\",\n" +
                "  \"steps\" : [ {\n" +
                "    \"id\" : \"dataflow_0\",\n" +
                "    \"name\" : \"shiy-test-client-submit\",\n" +
                "    \"type\" : \"dataflow\",\n" +
                "    \"x\" : 432,\n" +
                "    \"y\" : 240,\n" +
                "    \"inputConfigurations\" : [ ],\n" +
                "    \"outputConfigurations\" : [ ],\n" +
                "    \"otherConfigurations\" : {\n" +
                "      \"source0_dataset\" : \"shiy_common\",\n" +
                "      \"endpoints\" : [ {\n" +
                "        \"name\" : \"\",\n" +
                "        \"properties\" : \"\"\n" +
                "      } ],\n" +
                "      \"age\" : \"2\",\n" +
                "      \"年龄\" : \"2\",\n" +
                "      \"dataflowId$\" : \"92613801-8bfc-4c07-879a-b8cd2e043eeb\"\n" +
                "    }\n" +
                "  }, {\n" +
                "    \"id\" : \"dataflow_1\",\n" +
                "    \"name\" : \"shiy-test-client-submit-workflow\",\n" +
                "    \"type\" : \"dataflow\",\n" +
                "    \"x\" : 1000,\n" +
                "    \"y\" : 260,\n" +
                "    \"inputConfigurations\" : [ ],\n" +
                "    \"outputConfigurations\" : [ ],\n" +
                "    \"otherConfigurations\" : {\n" +
                "      \"source0_dataset\" : \"shiy_common\",\n" +
                "      \"endpoints\" : [ {\n" +
                "        \"name\" : \"\",\n" +
                "        \"properties\" : \"\"\n" +
                "      } ],\n" +
                "      \"age\" : \"3\",\n" +
                "      \"年龄\" : \"22\",\n" +
                "      \"dataflowId$\" : \"cb1a0da4-6087-4dc8-9733-8c1aeaf70a1d\"\n" +
                "    }\n" +
                "  } ],\n" +
                "  \"links\" : [ {\n" +
                "    \"name\" : \"\",\n" +
                "    \"source\" : \"dataflow_0\",\n" +
                "    \"sourceOutput\" : \"output\",\n" +
                "    \"target\" : \"dataflow_1\",\n" +
                "    \"targetInput\" : \"input\"\n" +
                "  } ],\n" +
                "  \"oid\" : \"cc8e97a6-90bf-42a5-8a05-53b25889f863\",\n" +
                "  \"creator\" : \"admin\",\n" +
                "  \"createTime\" : 1501060930480,\n" +
                "  \"lastModifier\" : \"admin\",\n" +
                "  \"lastModifiedTime\" : 1501063584613,\n" +
                "  \"owner\" : \"admin\",\n" +
                "  \"version\" : 5,\n" +
                "  \"enabled\" : 1,\n" +
                "  \"parameters\" : [ {\n" +
                "    \"name\" : \"dataset1\",\n" +
                "    \"category\" : \"ref\",\n" +
                "    \"refs\" : [ \"dataflow_0.source0_dataset\" ],\n" +
                "    \"defaultVal\" : \"shiy_common\",\n" +
                "    \"description\" : \"\"\n" +
                "  }, {\n" +
                "    \"name\" : \"dataset2\",\n" +
                "    \"category\" : \"ref\",\n" +
                "    \"refs\" : [ \"dataflow_1.source0_dataset\" ],\n" +
                "    \"defaultVal\" : \"shiy_common\",\n" +
                "    \"description\" : \"\"\n" +
                "  }, {\n" +
                "    \"name\" : \"age1\",\n" +
                "    \"category\" : \"ref\",\n" +
                "    \"refs\" : [ \"dataflow_0.age\" ],\n" +
                "    \"defaultVal\" : \"2\",\n" +
                "    \"description\" : \"\"\n" +
                "  }, {\n" +
                "    \"name\" : \"age2\",\n" +
                "    \"category\" : \"ref\",\n" +
                "    \"refs\" : [ \"dataflow_1.age\" ],\n" +
                "    \"defaultVal\" : \"3\",\n" +
                "    \"description\" : \"\"\n" +
                "  } ],\n" +
                "  \"moduleVersion\" : 0,\n" +
                "  \"solrdocVersion\" : \"1573979249316986880\",\n" +
                "  \"expiredPeriod\" : 0\n" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(json, Map.class);
            System.out.println(map.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
