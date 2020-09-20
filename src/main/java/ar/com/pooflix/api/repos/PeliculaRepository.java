package ar.com.pooflix.api.repos;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ar.com.pooflix.api.entities.Pelicula;

@Repository
public interface PeliculaRepository extends MongoRepository<Pelicula, ObjectId> {
    Pelicula findBy_id(ObjectId _id);

    Pelicula findByTitulo(String titulo);

}
