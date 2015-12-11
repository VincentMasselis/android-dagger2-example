package fr.vincentmasselis.daggersample.data.post.parser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import fr.vincentmasselis.daggersample.data.utils.DefaultJsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

/**
 * Permet de parser une liste de {@link Post} json en {@link Collection<Post>}. Elle itère sur la
 * liste json et utilise {@link DefaultJsonParser<Post>} pour parser chacun des {@link Post}
 * unitairement.
 * Ce parser utilise {@link org.json.JSONObject} pour fonctionner.
 *
 * @see DefaultJsonParser
 * @see Post
 */
public class DefaultJsonPostArrayParser implements StringParser<Collection<Post>> {

    private final DefaultJsonParser<Post> mPostParser;

    @Inject
    public DefaultJsonPostArrayParser(DefaultJsonParser<Post> postParser) {
        mPostParser = postParser;
    }

    @Override
    public Collection<Post> parse(String s) throws JSONException {
        JSONArray jsonArray = new JSONArray(s);
        Collection<Post> posts = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            posts.add(mPostParser.parse(jsonArray.getJSONObject(i)));
        }
        return posts;
    }
}