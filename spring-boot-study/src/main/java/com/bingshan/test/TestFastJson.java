package com.bingshan.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author bingshan
 * @date 2022/4/18 20:39
 */
public class TestFastJson {

    public static void main(String[] args) {
        //Map
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", "John");
        data.put("sex", "male");
        data.put("age", 22);
        data.put("is_student", true);
        data.put("hobbies", new String[] {"hiking", "swimming"});

        JSONObject obj = new JSONObject(data);

        // 或是下面这种写法，将 java 对象转换为 json 对象
        //JSONObject obj1 = JSONObject.parseObject(String.valueOf(data));
        System.out.println("----------------");

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("Name","wwl");
        jsonObject2.addProperty("AGe","25");
        Gson gson2 = new GsonBuilder().create();
        String json2 = gson2.toJson(jsonObject2);
        System.out.println(json2);

        System.out.println("----------------");
        Account account = new Account();
        account.setAccountId("11");
        account.setAccountName("name");
        account.setPHoNe("hser");;
        String accountStr = JSON.toJSONString(account);
        System.out.println(accountStr);
        Account account1 = JSON.parseObject(accountStr, Account.class);
        System.out.println(account1.toString());
    }
}
