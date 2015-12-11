package fr.vincentmasselis.daggersample.data.post.parser;

import fr.vincentmasselis.daggersample.data.utils.JsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Provider;

public class PostParser implements StringParser<Post>, JsonParser<Post> {

    private final Provider<Post> mPostProvider;

    @Inject
    public PostParser(Provider<Post> postProvider) {
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
