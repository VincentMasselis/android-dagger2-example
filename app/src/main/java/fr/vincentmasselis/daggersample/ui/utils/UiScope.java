package fr.vincentmasselis.daggersample.ui.utils;

import javax.inject.Scope;

/**
 * Quand vous décidez de créer un component, vous pouvez lui adjoindre une dépendence vers un autre
 * component (comme le fait {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent}
 * Cependant, ce système a ses limites, ainsi, pour des raisons évidentes, un component en singleton
 * ne peut pas dépendre vers un autre component singleton. Dans notre projet,
 * {@link fr.vincentmasselis.daggersample.manager.ManagerComponent} est un singleton, de ce fait, il
 * faut dire à Dagger2 que {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent}
 * (ou que {@link fr.vincentmasselis.daggersample.ui.MyFragment.MyFragmentComponent}) dispose d'un
 * {@link Scope} précis différent de {@link javax.inject.Singleton}.
 * Ainsi pour que cela puisse compiler, il faut annoter
 * {@link fr.vincentmasselis.daggersample.ui.MyActivity.MyActivityComponent}
 * et {@link fr.vincentmasselis.daggersample.ui.MyFragment.MyFragmentComponent} avec {@link UiScope}
 * pour indiquer à Dagger2 que ces components ont un cycle de vie.
 */
@Scope
public @interface UiScope {
}
