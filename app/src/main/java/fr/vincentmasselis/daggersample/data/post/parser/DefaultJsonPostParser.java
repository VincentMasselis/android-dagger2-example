package fr.vincentmasselis.daggersample.data.post.parser;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Provider;

import fr.vincentmasselis.daggersample.data.utils.DefaultJsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

/**
 * Cette classe permet de parser une {@link String} ou un {@link JSONObject} en {@link Post}. Comme
 * elle est capable de faire les 2, elle implémente 2 interfaces différentes.
 *
 * @see StringParser
 * @see DefaultJsonParser
 */
public class DefaultJsonPostParser implements StringParser<Post>, DefaultJsonParser<Post> {

    private final Provider<Post> mPostProvider;

    /**
     * @param postProvider Ce provider permet de récupérer une nouvelle instance de {@link Post} à
     *                     chaque fois que l'on va en parser une nouveau.
     */
    @Inject
    public DefaultJsonPostParser(Provider<Post> postProvider) {
        mPostProvider = postProvider;
    }

    @Override
    public Post parse(String s) throws JSONException {
        JSONObject jsonObject = new JSONObject(s);
        return parse(jsonObject);
    }

    @Override
    public Post parse(JSONObject jsonObject) throws JSONException {
        Post post = mPostProvider.get();
        post.setUserId(jsonObject.getInt("userId"));
        post.setId(jsonObject.getInt("id"));
        post.setTitle(jsonObject.getString("title"));
        post.setBody(jsonObject.getString("body"));
        return post;
    }

}
