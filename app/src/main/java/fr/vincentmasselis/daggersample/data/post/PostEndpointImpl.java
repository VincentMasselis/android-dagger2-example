package fr.vincentmasselis.daggersample.data.post;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Collection;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Provider;

import fr.vincentmasselis.daggersample.data.post.parser.DefaultJson;
import fr.vincentmasselis.daggersample.data.post.parser.DefaultJsonPostParser;
import fr.vincentmasselis.daggersample.data.post.parser.FastJsonPostParser;
import fr.vincentmasselis.daggersample.data.utils.RequestFuture;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

/**
 * Implémentation concrète de {@link PostEndpoint} qui fait des appels web service pour récupérer
 * des {@link Post}.
 *
 * @see PostEndpoint
 */
public class PostEndpointImpl implements PostEndpoint {
    private final OkHttpClient mOkHttpClient;
    private final Provider<StringParser<Post>> mPostParserProvider;
    private final Provider<StringParser<Collection<Post>>> mPostArrayParserProvider;

    /**
     * Cette classe a besoin de 3 éléments pour fonctionner.
     * <p/>
     * Si l'on regarde attentivement, on constate que le 2ème paramètre est annoté avec
     * {@link DefaultJson}. Cette annotation permet de choisir quelle implémentation nous voulons
     * utiliser pour {@link StringParser<Post>}. Si nous utilisons {@link DefaultJson} alors ce sera
     * {@link DefaultJsonPostParser} qui sera l'implémentation. En revanche, si nous utilisons
     * {@link fr.vincentmasselis.daggersample.data.post.parser.FastJson} alors ce sera
     * {@link FastJsonPostParser} qui sera utilisé.
     * Pour éviter que le même parser travaille en même temps sur 2 Json différents (ce qui peut
     * arriver si vous faîte appel, en même temps, au WS sur 2 thread différents) nous utilisons un
     * provider qui nous fournit une nouvelle instance d'objet à chaque fois que l'on souhaite
     * récupérer un parser.
     * Ainsi, si 2 appels sont effectués en même temps, la parsing est réalisé
     * par 2 objects différents ce qui limite les effets négatifs des appels concurrentiels mal
     * gérés.
     *
     * @param okHttpClient            Permet de réaliser des appels sur les web service
     * @param postParserProvider      Interface qui permet de parser 1 post json en objet
     *                                {@link Post}
     * @param postArrayParserProvider Interface qui permet de parser une liste json de {@link Post}
     *                                en {@link Collection<Post>}
     * @see StringParser<Post>
     * @see DefaultJson
     * @see DefaultJsonPostParser
     * @see fr.vincentmasselis.daggersample.data.post.parser.FastJson
     * @see FastJsonPostParser
     * @see Provider
     */
    @Inject
    public PostEndpointImpl(OkHttpClient okHttpClient,
                            @DefaultJson Provider<StringParser<Post>> postParserProvider,//Changez @DefaultJson par @FastJson pour utiliser l'autre implémentation
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
