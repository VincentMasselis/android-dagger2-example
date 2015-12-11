package fr.vincentmasselis.daggersample;


import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Ce module défini l'objet injectable Context. Ce module est utilisé par
 * {@link fr.vincentmasselis.daggersample.manager.ManagerComponent} afin que tout les objets du
 * graphe puisse s'injecter un Context qui sera dans ce cas {@link MyApplication}
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