package fr.vincentmasselis.daggersample.manager;

import fr.vincentmasselis.daggersample.ApplicationModule;
import fr.vincentmasselis.daggersample.data.DataModule;
import fr.vincentmasselis.daggersample.manager.post.PostManager;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DataModule.class, ManagerModule.class, ApplicationModule.class})
public interface ManagerComponent {
    PostManager daggerLocationManager();
}
