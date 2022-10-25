package com.bingshan.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author bingshan
 * @date 2022/4/18 20:39
 */
public class TestGson {
    public static void main(String[] args) {
        //Map
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name","wwl");
        jsonObject.addProperty("age","25");
        jsonObject.addProperty("height","170.2");
        jsonObject.addProperty("data","170.2");
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("key1","value1");
        jsonObject1.addProperty("key2","value2");
        jsonObject.add("data", jsonObject1);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(jsonObject);
        System.out.println(json);
        Map<String, Object> o = gson.fromJson(json, new TypeToken<TreeMap<String, Object>>() {}.getType());
        System.out.println(o.toString());

        System.out.println("----------------");

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("Name","wwl");
        jsonObject2.addProperty("AGe","25");
        Gson gson2 = new GsonBuilder().create();
        String json2 = gson2.toJson(jsonObject2);
        System.out.println(json2);
    }
}
