package fr.vincentmasselis.daggersample.data;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.vincentmasselis.daggersample.data.post.PostModule;

/**
 * Ce module défini un object injectable qui pourra être utilisé par tous les Endpoint de
 * l'application. Comme le rôle des endpoints est de faire un appel réseaux, ce module défini
 * l'objet injectable {@link OkHttpClient} pour que les endpoints puissent faires des appels réseaux
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
