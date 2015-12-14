package fr.vincentmasselis.daggersample.data.post;

import com.squareup.okhttp.OkHttpClient;

import java.util.Collection;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.vincentmasselis.daggersample.BuildConfig;
import fr.vincentmasselis.daggersample.data.post.parser.DefaultJson;
import fr.vincentmasselis.daggersample.data.post.parser.DefaultJsonPostArrayParser;
import fr.vincentmasselis.daggersample.data.post.parser.DefaultJsonPostParser;
import fr.vincentmasselis.daggersample.data.post.parser.FastJson;
import fr.vincentmasselis.daggersample.data.post.parser.FastJsonPostArrayParser;
import fr.vincentmasselis.daggersample.data.post.parser.FastJsonPostParser;
import fr.vincentmasselis.daggersample.data.utils.DefaultJsonParser;
import fr.vincentmasselis.daggersample.data.utils.FastJsonParser;
import fr.vincentmasselis.daggersample.data.utils.StringParser;
import fr.vincentmasselis.daggersample.model.Post;

/**
 * Ce module définit tous les objects injectables nécessaires pour faire fonctionner le
 * {@link PostEndpoint}
 */
@Module
public class PostModule {

    /**
     * {@link fr.vincentmasselis.daggersample.manager.post.PostManagerImpl utilise {@link PostEndpoint}}
     * pour fonctionner. Mais {@link PostEndpoint} est une interface! Pour dire à Dagger2 quelle
     * implementation utiliser nous devons écrire ce genre de méthode.
     *
     * @param postEndpointImpl Implémentation de {@link PostEndpoint}
     * @see fr.vincentmasselis.daggersample.manager.post.PostManagerImpl#mPostEndpoint
     */
    @Provides
    @Singleton
    public PostEndpoint postEndpoint(PostEndpointImpl postEndpointImpl) {
        return postEndpointImpl;
    }

    //////////////// Parsers avec l'api de base

    /**
     * Permet de définir une implémentation pour {@link StringParser<Post>}, dans le cas présent il
     * s'agit de {@link DefaultJsonPostParser} mais il existe une 2ème implémentation
     * {@link #fastJsonStringPostParser(FastJsonPostParser)} qui utilise {@link FastJsonParser}
     * comme implémentation.
     * Pour différencier c'est 2 implémentations de la même interface, il est neccéssaire de les
     * annoter différement. Ici c'est annoté avec {@link DefaultJson} contrairement à
     * {@link #fastJsonStringPostParser(FastJsonPostParser)} qui est annoté {@link FastJson}.
     */
    @Provides
    @DefaultJson
    public StringParser<Post> defaultJsonStringPostParser(DefaultJsonPostParser defaultJsonPostParser) {
        return defaultJsonPostParser;
    }

    /**
     * Permet de définir une implémentation pour {@link DefaultJsonParser<Post>}, ici c'est
     * {@link DefaultJsonPostParser} que l'on utilise
     */
    @Provides
    public DefaultJsonParser<Post> defaultJsonParserPost(DefaultJsonPostParser defaultJsonPostParser) {
        return defaultJsonPostParser;
    }

    ////////////////Parsers avec FastJson

    /**
     * Permet de définir une implémentation pour {@link StringParser<Post>}, dans le cas présent il
     * s'agit de {@link FastJsonPostParser} mais il existe une 2ème implémentation
     * {@link #defaultJsonStringPostParser(DefaultJsonPostParser)} qui utilise {@link DefaultJsonPostParser}
     * comme implémentation.
     * Pour différencier c'est 2 implémentations de la même interface, il est neccéssaire de les
     * annoter différement. Ici c'est annoté avec {@link FastJson} contrairement à
     * {@link #defaultJsonStringPostParser(DefaultJsonPostParser)} qui est annoté {@link DefaultJson}.
     */
    @Provides
    @FastJson
    public StringParser<Post> fastJsonStringPostParser(FastJsonPostParser postParser) {
        return postParser;
    }

    /**
     * Permet de définir une implémentation pour {@link FastJsonParser<Post>}, ici c'est
     * {@link FastJsonPostParser} que l'on utilise
     */
    @Provides
    public FastJsonParser<Post> fastJsonParserPost(FastJsonPostParser postParser) {
        return postParser;
    }

    /**
     * Définie une implémentation pour parser les listes de {@link Post} reçues en json.
     * {@link #defaultJsonStringPostParser(DefaultJsonPostParser)} et
     * {@link #fastJsonStringPostParser(FastJsonPostParser)} implémentent la même interface mais
     * grâce à l'utilisation des annotations {@link FastJson} et {@link DefaultJson}, on peut choisir
     * quelle implémentation nous souhaitons utiliser (voir
     * {@link PostEndpointImpl#PostEndpointImpl(OkHttpClient, Provider, Provider)}) mais dans le cas
     * où on veut changer l'implémentation à l'exécution, il suffit d'ajouter une condition ici.
     * Par exemple dans cette méthode, si nous sommes en configuration de debug, le parser utilisé
     * est l'implémentation qui utilise FastJson, dans l'autre cas c'est DefaultJson.
     */
    @Provides
    public StringParser<Collection<Post>> postsParser(DefaultJsonPostArrayParser defaultJsonPostArrayParser, FastJsonPostArrayParser fastJsonPostArrayParser) {
        if (BuildConfig.DEBUG) {
            return fastJsonPostArrayParser;
        } else {
            return defaultJsonPostArrayParser;
        }
    }
}