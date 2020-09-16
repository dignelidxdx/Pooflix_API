package ar.com.pooflix.api.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ar.com.pooflix.api.entities.Director;

public interface DirectorRepository extends MongoRepository<Director, ObjectId> {
    Director findBy_id(ObjectId _id);
}
