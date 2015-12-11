package fr.vincentmasselis.daggersample.data.post.parser;

import fr.vincentmasselis.daggersample.data.utils.JsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class PostArrayParser implements StringParser<Collection<Post>> {

    private final JsonParser<Post> mPostParser;

    @Inject
    public PostArrayParser(JsonParser<Post> postParser) {
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