package fr.vincentmasselis.daggersample.data.utils;

import org.json.JSONException;
import org.json.JSONObject;

public interface JsonParser<T> {
    T parse(JSONObject jsonObject) throws JSONException;
}
