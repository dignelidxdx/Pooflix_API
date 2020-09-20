package ar.com.pooflix.api.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pooflix.api.entities.Actor;

@Repository
public interface ActorRepository extends MongoRepository<Actor, ObjectId> {
    Actor findByNombre(String nombre);
}
