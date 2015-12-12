package fr.vincentmasselis.daggersample;

import android.app.Application;

import fr.vincentmasselis.daggersample.data.EndpointModule;
import fr.vincentmasselis.daggersample.manager.DaggerManagerComponent;
import fr.vincentmasselis.daggersample.manager.ManagerComponent;
import fr.vincentmasselis.daggersample.manager.ManagerModule;

public class MyApplication extends Application {

    private ManagerComponent mManagerComponent;

    /**
     * Dès que l'application se crée on instancie le graphe de données {@link ManagerComponent}.
     * Pour que ce {@link dagger.Component} puisse fonctionner, il a besoin de 3 modules :
     * {@link ManagerModule}, {@link ApplicationModule} et {@link EndpointModule}.
     * Chacun de ces modules "bind" des interfaces à des implémentations si l'on souhaite se faire
     * injecter des interfaces.
     *
     * @see fr.vincentmasselis.daggersample.manager.post.PostManager
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mManagerComponent = DaggerManagerComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .managerModule(new ManagerModule())
                .endpointModule(new EndpointModule())
                .build();

    }

    public static ManagerComponent getManagerComponent(Application application) {
        return ((MyApplication) application).mManagerComponent;
    }
}