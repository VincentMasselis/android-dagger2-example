package fr.vincentmasselis.daggersample.data.utils;

import com.alibaba.fastjson.JSONObject;

public interface FastJsonParser<T> {
    T parse(JSONObject jsonObject);
}
