package fr.vincentmasselis.daggersample;


import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

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