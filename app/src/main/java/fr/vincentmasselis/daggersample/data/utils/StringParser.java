package fr.vincentmasselis.daggersample.data.utils;

import org.json.JSONException;

/**
 * Interface pour parser qui prend en entré une string et en sortie un object parsé {@link T}
 * Le fonctionnement est identique à {@link DefaultJsonParser} et {@link FastJsonParser} sauf que le
 * format d'entré ici est une string
 *
 * @param <T> Type d'objet de sortie attendu
 */
public interface StringParser<T> {
    T parse(String s) throws JSONException;
}
