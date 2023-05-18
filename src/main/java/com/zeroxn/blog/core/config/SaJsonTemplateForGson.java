package com.zeroxn.blog.core.config;

import cn.dev33.satoken.json.SaJsonTemplate;
import com.google.gson.Gson;

import java.util.Map;

/**
 *Sa-Token的Gson序列化器
 */
public class SaJsonTemplateForGson implements SaJsonTemplate {
    public Gson gson = new Gson();
    public SaJsonTemplateForGson(){};
    @Override
    public String toJsonString(Object o) {
        //转为json
        return gson.toJson(o);
    }

    @Override
    public Map<String, Object> parseJsonToMap(String s) {
        return gson.fromJson(s, Map.class);
    }
}
