package fr.vincentmasselis.daggersample;

import android.app.Application;

import fr.vincentmasselis.daggersample.manager.DaggerManagerComponent;
import fr.vincentmasselis.daggersample.manager.ManagerComponent;
import fr.vincentmasselis.daggersample.manager.ManagerModule;

public class MyApplication extends Application {

    private ManagerComponent mManagerComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mManagerComponent = DaggerManagerComponent.builder()
                .managerModule(new ManagerModule())
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public static ManagerComponent getManagerComponent(Application application) {
        return ((MyApplication) application).mManagerComponent;
    }
}
