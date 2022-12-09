package ru.netology.repository;

import ru.netology.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class PostRepository {

  private final ConcurrentHashMap<Long, Post> storage = new ConcurrentHashMap<>();

  public List<Post> all() {
    return storage.values().stream().toList();
  }

  public Optional<Post> getById(long id) {
    if (storage.containsKey(id)) {
      return Optional.of(storage.get(id));
    }
    return Optional.empty();
  }

  public Post save(Post post) {
    AtomicInteger id = new AtomicInteger((int) post.getId());
    if (id.get() == 0) {
      if (storage.size() > 0) {
        var idList = storage.keySet().stream().sorted().toList();
        post.setId(idList.get(idList.size() - 1) + 1); // use next free id
      } else {
        post.setId(1);
      }
    }
    storage.put(post.getId(), post);
    return post;
  }

  public void removeById(long id) {
    storage.remove(id);
  }

}
