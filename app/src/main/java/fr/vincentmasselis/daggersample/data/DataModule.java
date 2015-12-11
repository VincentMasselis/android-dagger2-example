package fr.vincentmasselis.daggersample.data;

import fr.vincentmasselis.daggersample.data.post.PostModule;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {PostModule.class})
public class DataModule {

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }
}
