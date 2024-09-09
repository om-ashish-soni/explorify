package com.learn.explorify.repository;

import com.learn.explorify.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event,String> {
    Optional<Event> findByName(String name);
}
