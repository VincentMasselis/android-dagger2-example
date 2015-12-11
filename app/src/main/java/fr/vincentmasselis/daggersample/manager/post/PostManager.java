package fr.vincentmasselis.daggersample.manager.post;

import java.util.Collection;
import java.util.concurrent.Future;

import fr.vincentmasselis.daggersample.model.Post;

/**
 * Interface qui défini les méthodes utilisés par un manager pour travailler avec des {@link Post}
 * Quand un object a besoin de faire des appels WS pour récupérer les posts, il doit se faire
 * injecter cette interface (un des principes de base de l'IOC). Cependant, Dagger2 n'est pas
 * capable de deviner quel sera l'implémentation de cette interface, pour ce faire, il faut alors
 * définir un binding disant que l'implémentation actuelle de {@link PostManager} est
 * {@link PostManagerImpl}. Ce "binding" est visible dans {@link fr.vincentmasselis.daggersample.manager.ManagerModule}.
 * Grâce à l'injection d'une interface au lieu de directement s'injecter l'implémentation, on peut
 * garder une certaine souplesse sur le comportement désiré de {@link PostManager}. Par exemple,
 * lors de tests unitaire on peut imaginer utiliser une implémentation différente de
 * {@link PostManagerImpl} qui réalise des opération supplémentaires que {@link PostManagerImpl} ne
 * fait pas déjà.
 *
 * @see PostManagerImpl
 * @see fr.vincentmasselis.daggersample.manager.ManagerModule
 */
public interface PostManager {
    Future<Post> getPost();

    Future<Collection<Post>> getPosts();
}
