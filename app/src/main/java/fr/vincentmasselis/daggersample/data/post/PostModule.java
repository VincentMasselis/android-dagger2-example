package fr.vincentmasselis.daggersample.data.post;

import java.util.Collection;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.vincentmasselis.daggersample.BuildConfig;
import fr.vincentmasselis.daggersample.data.post.parser.FastJson;
import fr.vincentmasselis.daggersample.data.post.parser.FastJsonPostArrayParser;
import fr.vincentmasselis.daggersample.data.post.parser.FastJsonPostParser;
import fr.vincentmasselis.daggersample.data.post.parser.JsonObject;
import fr.vincentmasselis.daggersample.data.post.parser.PostArrayParser;
import fr.vincentmasselis.daggersample.data.post.parser.PostParser;
import fr.vincentmasselis.daggersample.data.utils.FastJsonParser;
import fr.vincentmasselis.daggersample.data.utils.JsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

@Module
public class PostModule {

    @Provides
    @Singleton
    public PostEndpoint postEndpoint(PostEndpointImpl postEndpointImpl) {
        return postEndpointImpl;
    }

    //Parsers avec JSONObject
    @Provides
    @JsonObject
    public StringParser<Post> jsonObjectPostParser(PostParser postParser) {
        return postParser;
    }

    @Provides
    public JsonParser<Post> jsonParserPost(PostParser postParser) {
        return postParser;
    }

    //Parsers avec FastJson
    @Provides
    @FastJson
    public StringParser<Post> fastJsonPostParser(FastJsonPostParser postParser) {
        return postParser;
    }

    @Provides
    public FastJsonParser<Post> fastJsonParserPost(FastJsonPostParser postParser) {
        return postParser;
    }

    @Provides
    public StringParser<Collection<Post>> postsParser(PostArrayParser postArrayParser, FastJsonPostArrayParser fastJsonPostArrayParser) {
        if (BuildConfig.DEBUG) {
            return fastJsonPostArrayParser;
        } else {
            return postArrayParser;
        }
    }
}