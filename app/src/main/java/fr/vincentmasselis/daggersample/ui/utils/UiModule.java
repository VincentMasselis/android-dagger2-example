package fr.vincentmasselis.daggersample.ui.utils;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Ce module définit l'objet injectable Context. Ainsi si
 * {@link fr.vincentmasselis.daggersample.manager.ManagerComponent} dispose dans son graphe d'un
 * Context, il s'agit en réalité de {@link fr.vincentmasselis.daggersample.MyApplication}. Dans le
 * cas de {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent} et
 * {@link fr.vincentmasselis.daggersample.ui.MyFragment.MyFragmentComponent} le context contenu dans
 * le graphe n'est pas {@link fr.vincentmasselis.daggersample.MyApplication} mais
 * {@link fr.vincentmasselis.daggersample.ui.MyActivity}.
 * Ainsi tout les objets contenus dans le
 * graphe {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent} ou
 * {@link fr.vincentmasselis.daggersample.ui.MyFragment.MyFragmentComponent} auront une instance de
 * MyActivity si ils s'injectent un Context.
 */
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
