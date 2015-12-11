package fr.vincentmasselis.daggersample.data.post.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import fr.vincentmasselis.daggersample.data.utils.FastJsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

public class FastJsonPostArrayParser implements StringParser<Collection<Post>> {

    private final FastJsonParser<Post> mPostParser;

    @Inject
    public FastJsonPostArrayParser(FastJsonParser<Post> postParser) {
        mPostParser = postParser;
    }

    @Override
    public Collection<Post> parse(String s) throws JSONException {
        JSONArray jsonArray = JSON.parseArray(s);
        Collection<Post> posts = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            posts.add(mPostParser.parse(jsonArray.getJSONObject(i)));
        }
        return posts;
    }
}
