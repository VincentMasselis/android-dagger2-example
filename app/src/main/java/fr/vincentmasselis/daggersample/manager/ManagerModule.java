package fr.vincentmasselis.daggersample.manager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import fr.vincentmasselis.daggersample.manager.post.PostManager;
import fr.vincentmasselis.daggersample.manager.post.PostManagerImpl;

/**
 * Ce module définit tous les objects injectables nécessaires pour faire fonctionner les managers de
 * l'application (dans notre exemple il n'y en a qu'un)
 */
@Module
public class ManagerModule {

    /**
     * {@link fr.vincentmasselis.daggersample.ui.MyActivity utilise
     * {@link PostManager}} pour fonctionner. Mais {@link PostManager} est une interface!
     * Pour dire à Dagger2 quelle implementation utiliser nous devons écrire ce genre de méthode.
     *
     * @param postManagerImpl Implémentation de {@link PostManager}
     * @see fr.vincentmasselis.daggersample.ui.MyActivity#mPostManager
     */
    @Provides
    @Singleton
    PostManager postManager(PostManagerImpl postManagerImpl) {
        return postManagerImpl;
    }
}
