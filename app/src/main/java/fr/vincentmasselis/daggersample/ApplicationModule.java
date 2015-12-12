package fr.vincentmasselis.daggersample;


import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Ce module définit l'objet injectable Context. Il est utilisé par
 * {@link fr.vincentmasselis.daggersample.manager.ManagerComponent} afin que tous les objets du
 * graphe puissent s'injecter un {@link android.content.Context} qui sera, dans ce cas,
 * {@link MyApplication}
 */
@Module
public class ApplicationModule {
    private final Context mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    public Context context() {
        return mApplication;
    }
}