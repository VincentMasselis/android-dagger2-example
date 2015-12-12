package fr.vincentmasselis.daggersample.data.post;

import java.util.Collection;
import java.util.concurrent.Future;

import fr.vincentmasselis.daggersample.model.Post;

/**
 * Interface qui définit les méthodes utilisées par un Endpoint pour récupérer les
 * posts d'un web service.
 * Quand un object a besoin de faire des appels WS pour récupérer les posts, il doit se faire
 * injecter cette interface (un des principes de base de l'IOC). Cependant, Dagger2 n'est pas
 * capable de deviner quel sera l'implémentation de cette interface, pour ce faire, il faut
 * définir un binding disant que l'implémentation actuelle de {@link PostEndpoint} est
 * {@link PostEndpointImpl}. Ce "binding" est visible dans {@link PostModule}.
 * Grâce à l'injection d'une interface au lieu de directement s'injecter l'implémentation, on peut
 * garder une certaine souplesse sur le comportement désiré de {@link PostEndpoint}. Par exemple,
 * lors de tests unitaire on peut imaginer utiliser une implémentation différente de
 * {@link PostEndpointImpl} qui ne fait pas d'appel WS mais qui retourne directement la valeur
 * au lieu de charger le WS.
 *
 * @see PostEndpointImpl
 * @see PostModule
 */
public interface PostEndpoint {
    Future<Post> getPost();

    Future<Collection<Post>> getPosts();
}
