package fr.vincentmasselis.daggersample.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import dagger.Component;
import fr.vincentmasselis.daggersample.MyApplication;
import fr.vincentmasselis.daggersample.R;
import fr.vincentmasselis.daggersample.manager.ManagerComponent;
import fr.vincentmasselis.daggersample.manager.post.PostManager;
import fr.vincentmasselis.daggersample.ui.utils.UiModule;
import fr.vincentmasselis.daggersample.ui.utils.UiScope;

/**
 * {@link android.app.Activity} principale de l'application. Elle ne fait rien de spécial hormis
 * instancier {@link MyFragment} pour l'ajouter à son layout.
 * Cette activité sert surtout d'exemple à montrer comment faire pour créer un graphe de données
 * basé sur {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent}.
 */
public class MyActivity extends AppCompatActivity {

    /**
     * Graphe de données qui dépend du cycle de vie de {@link MyActivity}. On le créé lors de
     * l'instanciation de {@link MyActivity} et ce graphe meurt quand l'Activity est désallouée.
     * Ce graphe dispose d'une dépendance vers un autre graphe de données nommé
     * {@link ManagerComponent} pour que le graphe
     * {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent} puisse
     * récupérer une instance de {@link PostManager}. Cela est possible grâce à la méthode
     * {@link ManagerComponent#postManager()}, lisez la doc pour comprendre pourquoi cette méthode
     * existe et à quoi elle sert. Dans notre cas, elle permet de pouvoir récupérer une instance de
     * {@link PostManager} définie par le graphe {@link ManagerComponent} alors que l'on se trouve
     * dans le graphe {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent} !
     * Je me répète un peu, mais c'est assez compliqué à comprendre.
     * En plus de la dépendance vers {@link ManagerComponent}, ce graphe utilise {@link UiModule}
     * pour définir dans le graphe le Context actuel qui est une Activity.
     */
    @UiScope
    @Component(dependencies = ManagerComponent.class, modules = UiModule.class)
    public interface MyActivityComponent {
        /**
         * La creation d'une instance de {@link MyActivity} est gérée automatiquement par le système
         * Android. De ce fait, l'instance ne se trouve dans aucun graphe de données ce qui est
         * problématique si l'on souhaite s'injecter une dépendance.
         * Pour palier à ce problème cette méthode a été conçue pour l'injecter, à postériori de la
         * construction les dépendances.
         */
        void inject(MyActivity activity);
    }

    @Inject
    PostManager mPostManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyActivityComponent component = DaggerMyActivity_MyActivityComponent.builder()
                // Comme MyActivityComponent contient une dépendance vers ManagerComponent,
                // il faut donc récupérer le graphe de données de ManagerComponent déjà instancié
                // par l'application, ce graphe est conservé par MyApplication
                .managerComponent(MyApplication.getManagerComponent(getApplication()))
                        // Voir MyActivityComponent
                .uiModule(new UiModule(this))
                .build();
        //C'est ici que mPostManager est injecté à MyActivity.
        component.inject(this);

        setContentView(R.layout.activity_main);

        if (getSupportFragmentManager().findFragmentById(R.id.my_fragment_container) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.my_fragment_container, MyFragment.newInstance())
                    .commit();
        }
    }
}
