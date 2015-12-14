package fr.vincentmasselis.daggersample.data;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.vincentmasselis.daggersample.data.post.PostModule;

/**
 * Ce module définit un object injectable qui pourra être utilisé par tous les Endpoint de
 * l'application. Comme le rôle des endpoints est de faire un appel réseau, ce module définit
 * l'objet injectable {@link OkHttpClient} pour que les endpoints puissent faire des appels réseaux
 *
 * @see fr.vincentmasselis.daggersample.data.post.PostEndpoint
 */
@Module(includes = {PostModule.class})
public class EndpointModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}
