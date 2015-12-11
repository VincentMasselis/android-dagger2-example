package fr.vincentmasselis.daggersample.data.post.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import fr.vincentmasselis.daggersample.data.utils.FastJsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

import javax.inject.Inject;
import javax.inject.Provider;

public class FastJsonPostParser implements StringParser<Post>, FastJsonParser<Post> {

    private final Provider<Post> mPostProvider;

    @Inject
    public FastJsonPostParser(Provider<Post> postProvider) {
        mPostProvider = postProvider;
    }

    @Override
    public Post parse(String s) throws JSONException {
        JSONObject jsonObject = JSON.parseObject(s);
        return parse(jsonObject);
    }

    @Override
    public Post parse(JSONObject jsonObject) {
        Post post = mPostProvider.get();
        post.setUserId(jsonObject.getIntValue("userId"));
        post.setId(jsonObject.getIntValue("id"));
        post.setTitle(jsonObject.getString("title"));
        post.setBody(jsonObject.getString("body"));
        return post;
    }
}
