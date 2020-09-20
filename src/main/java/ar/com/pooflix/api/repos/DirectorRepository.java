package ar.com.pooflix.api.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pooflix.api.entities.Director;

@Repository
public interface DirectorRepository extends MongoRepository<Director, ObjectId> {
    Director findBy_id(ObjectId _id);

    Director findByNombre(String nombre);
}
