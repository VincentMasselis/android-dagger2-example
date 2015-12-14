package fr.vincentmasselis.daggersample.data.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface pour parser qui prend en entrée un {@link org.json.JSONObject} et en sortie un object parsé
 * {@link T}.
 * Cette interface prend en entrée un {@link JSONObject} de org.json.* contrairement à
 * {@link FastJsonParser} qui prend en paramètre {@link com.alibaba.fastjson.JSONObject}
 *
 * @param <T> Type d'objet de sortie attendu
 * @see FastJsonParser
 */
public interface DefaultJsonParser<T> {
    T parse(JSONObject jsonObject) throws JSONException;
}
