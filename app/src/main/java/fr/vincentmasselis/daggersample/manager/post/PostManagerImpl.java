package fr.vincentmasselis.daggersample.manager.post;

import java.util.Collection;
import java.util.concurrent.Future;

import javax.inject.Inject;

import fr.vincentmasselis.daggersample.data.post.PostEndpoint;
import fr.vincentmasselis.daggersample.model.Post;

/**
 * Implémentation basique de {@link PostManager} qui montre comment faire pour s'injecter une
 * instance de {@link PostEndpoint}.
 */
public class PostManagerImpl implements PostManager {

    private final PostEndpoint mPostEndpoint;

    /**
     * Pour pouvoir avoir une instance de {@link PostEndpoint} dans son code, il faut ajouter
     * l'annotation {@link Inject} au constructeur et ajouter en paramètre du constructeur une
     * variable du type {@link PostEndpoint}. Ensuite il suffit de garder cette valeur en tant que
     * membre de classe.
     *
     * @param postEndpoint Object à injecter. En codant cette classe vous n'avez en réalité aucune
     *                     certitude de l'implémentation {@link PostEndpoint} utilisée. Dans ce
     *                     projet d'exemple il n'existe qu'une seule implémentation mais il est
     *                     possible qu'il y en ait plusieures dans le futur pour différentes raisons.
     */
    @Inject
    public PostManagerImpl(PostEndpoint postEndpoint) {
        this.mPostEndpoint = postEndpoint;
    }

    public Future<Post> getPost() {
        return mPostEndpoint.getPost();
    }

    @Override
    public Future<Collection<Post>> getPosts() {
        return mPostEndpoint.getPosts();
    }
}
