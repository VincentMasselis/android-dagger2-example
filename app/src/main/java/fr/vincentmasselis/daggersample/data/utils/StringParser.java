package fr.vincentmasselis.daggersample.data.utils;

import org.json.JSONException;

/**
 * Interface pour parser qui prend en entrée une {@link java.lang.String} et en sortie un object
 * parsé {@link T}
 * Le fonctionnement est identique à {@link DefaultJsonParser} et {@link FastJsonParser} sauf que le
 * format d'entrée ici est une {@link java.lang.String}
 *
 * @param <T> Type d'objet de sortie attendu
 */
public interface StringParser<T> {
    T parse(String s) throws JSONException;
}
