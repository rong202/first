package com.youzan.ad;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @author Fei
 * @data 2019-04-26 下午 06:43
 */
public class qwe {
    public static void main(String[] args) {
    /*    key key = new key();
        key.setUnitId("1");
        key.setKeyword("baba");
        String s = JSON.toJSONString(key);
        Object parse = JSON.parse(s);
        System.out.println(  parse.toString());*/
    String str="{\n" +
            "\t\"id\":\"15\",\n" +
            "\t\"userId\":\"16\",\n" +
            "\t\"planName\":\"五一\",\n" +
            "\t\"startTime\":\"2019-04-26\",\n" +
            "\t\"endTime\":\"2019-05-10\"\n" +
            "}\n";

        JSONObject jsonObject = JSON.parseObject(str);
        HashMap hashMap = new HashMap();
        hashMap.put("1",str);
        System.out.println(hashMap);
        System.out.println(jsonObject);

    }

}
