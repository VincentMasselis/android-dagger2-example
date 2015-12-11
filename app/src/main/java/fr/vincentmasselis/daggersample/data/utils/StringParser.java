package fr.vincentmasselis.daggersample.data.utils;

import org.json.JSONException;

public interface StringParser<T> {
    T parse(String s) throws JSONException;
}
