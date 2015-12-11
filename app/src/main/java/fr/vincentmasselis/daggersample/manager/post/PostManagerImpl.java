package fr.vincentmasselis.daggersample.manager.post;

import fr.vincentmasselis.daggersample.data.post.PostEndpoint;
import fr.vincentmasselis.daggersample.model.Post;

import java.util.Collection;
import java.util.concurrent.Future;

import javax.inject.Inject;

public class PostManagerImpl implements PostManager {

    private final PostEndpoint mPostEndpoint;

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
