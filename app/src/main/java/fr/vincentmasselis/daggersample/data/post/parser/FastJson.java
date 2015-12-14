package fr.vincentmasselis.daggersample.data.post.parser;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Annotation qui permet d'indentifier quelle implémentation de
 * {@link fr.vincentmasselis.daggersample.data.utils.StringParser<fr.vincentmasselis.daggersample.model.Post>}
 * on souhaite récupérer.
 * Dans le cas présent ce sera l'implémentation {@link FastJsonPostParser}.
 *
 * @see fr.vincentmasselis.daggersample.data.utils.StringParser<fr.vincentmasselis.daggersample.model.Post>
 * @see FastJsonPostParser
 * @see fr.vincentmasselis.daggersample.data.post.PostModule
 * @see DefaultJson
 */
@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FastJson {
}
