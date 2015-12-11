package fr.vincentmasselis.daggersample.data.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Interface pour parser qui prend en entré un JSONObject et en sortie un object parsé {@link T}
 * Cette interface prend en entré un {@link JSONObject} de com.alibaba.fastjson.* contrairement à
 * {@link DefaultJsonParser} qui prend en paramètre {@link org.json.JSONObject}
 *
 * @param <T> Type d'objet de sortie attendu
 * @see DefaultJsonParser
 */
public interface FastJsonParser<T> {
    T parse(JSONObject jsonObject);
}
