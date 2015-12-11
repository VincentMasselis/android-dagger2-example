package fr.vincentmasselis.daggersample.ui.utils;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {
    private final Context mContext;

    public UiModule(Context context) {
        mContext = context;
    }

    @Provides
    public Context context() {
        return mContext;
    }
}
