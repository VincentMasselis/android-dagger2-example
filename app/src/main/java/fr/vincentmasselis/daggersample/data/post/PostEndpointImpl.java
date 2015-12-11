package fr.vincentmasselis.daggersample.data.post;

import fr.vincentmasselis.daggersample.data.post.parser.JsonObject;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;
import fr.vincentmasselis.daggersample.utils.RequestFuture;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Collection;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Provider;

public class PostEndpointImpl implements PostEndpoint {


    private final OkHttpClient mOkHttpClient;
    private final Provider<StringParser<Post>> mPostParserProvider;
    private final Provider<StringParser<Collection<Post>>> mPostArrayParserProvider;

    @Inject
    public PostEndpointImpl(OkHttpClient okHttpClient,
                            @JsonObject Provider<StringParser<Post>> postParserProvider,
                            Provider<StringParser<Collection<Post>>> postArrayParserProvider) {
        mOkHttpClient = okHttpClient;
        mPostParserProvider = postParserProvider;
        mPostArrayParserProvider = postArrayParserProvider;
    }

    @Override
    public Future<Post> getPost() {
        return RequestFuture.newFuture(
                mOkHttpClient.newCall(
                        new Request.Builder()
                                .url("http://jsonplaceholder.typicode.com/posts/1")
                                .get()
                                .build()),
                mPostParserProvider.get()
        );
    }

    @Override
    public Future<Collection<Post>> getPosts() {
        return RequestFuture.newFuture(
                mOkHttpClient.newCall(
                        new Request.Builder()
                                .url("http://jsonplaceholder.typicode.com/posts")
                                .get()
                                .build()),
                mPostArrayParserProvider.get()
        );
    }
}
