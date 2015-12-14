package fr.vincentmasselis.daggersample.manager;

import javax.inject.Singleton;

import dagger.Component;
import fr.vincentmasselis.daggersample.ApplicationModule;
import fr.vincentmasselis.daggersample.data.EndpointModule;
import fr.vincentmasselis.daggersample.manager.post.PostManager;

/**
 * Définit un graphe de données qui englobe toute la gestion des Endpoint et des Manager. Celui-ci
 * ne doit être instancé qu'une fois, d'où l'utilisation de l'annotation {@link Singleton}.
 * Il est instancié dans {@link fr.vincentmasselis.daggersample.MyApplication} et reste en mémoire
 * tant que l'application est active.
 *
 * @see fr.vincentmasselis.daggersample.MyApplication
 * @see EndpointModule
 * @see ManagerModule
 * @see ApplicationModule
 */
@Singleton
@Component(modules = {EndpointModule.class, ManagerModule.class, ApplicationModule.class})
public interface ManagerComponent {
    /**
     * Un graphe de données est une entitée fermée dans lequel les objects vivent, s'instancient, etc...
     * Mais si vous avez plusieurs graphes de données dans votre application, alors il est normal que
     * certains graphes aient besoin d'objets qui sont instanciés dans d'autre graphes.
     * Pour rendre exploitable par un graphe des objets qui sont définis par un autre graphe, il est
     * nécessaire (dans l'autre graphe) d'ajouter une méthode de ce type.
     * Ainsi en déclarant cette méthode, je dis que n'importe quel graphe de l'extérieur (comme
     * {@link fr.vincentmasselis.daggersample.ui.MyFragment.MyFragmentComponent ou
     * {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent}} peut s'injecter
     * {@link PostManager} qui a été instancé dans le graphe {@link ManagerComponent}.
     */
    PostManager postManager();
}
