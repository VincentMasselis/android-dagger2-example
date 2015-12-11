package fr.vincentmasselis.daggersample.manager.post;

import fr.vincentmasselis.daggersample.model.Post;

import java.util.Collection;
import java.util.concurrent.Future;

public interface PostManager {
    Future<Post> getPost();

    Future<Collection<Post>> getPosts();
}
