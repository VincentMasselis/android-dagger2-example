package fr.vincentmasselis.daggersample.manager;

import fr.vincentmasselis.daggersample.manager.post.PostManager;
import fr.vincentmasselis.daggersample.manager.post.PostManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ManagerModule {

    @Provides
    @Singleton
    PostManager postManager(PostManagerImpl postManagerImpl) {
        return postManagerImpl;
    }
}
