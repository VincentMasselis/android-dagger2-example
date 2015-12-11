package fr.vincentmasselis.daggersample.data.post;

import fr.vincentmasselis.daggersample.model.Post;

import java.util.Collection;
import java.util.concurrent.Future;

public interface PostEndpoint {
    Future<Post> getPost();

    Future<Collection<Post>> getPosts();
}
