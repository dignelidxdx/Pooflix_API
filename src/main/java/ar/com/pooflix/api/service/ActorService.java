package ar.com.pooflix.api.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pooflix.api.entities.Actor;
import ar.com.pooflix.api.repos.ActorRepository;


@Service
public class ActorService {

    @Autowired
    ActorRepository actorRepo;

    public boolean crearNuevoActor(Actor actor){
        actorRepo.save(actor);
        return true;
    }

    public Actor crearActor(String nombre, String nivel) {
        if (existeActor(nombre)) {
            return null;
        } else {
            Actor actor = new Actor();
            actor.setNivel(nivel);
            actor.setNombre(nombre);
            
            boolean actorCreado = crearNuevoActor(actor);
            if (actorCreado)
                return actor;
            else
                return null;
        }

    }

    boolean existeActor(String nombre) {
        Actor actor = actorRepo.findByNombre(nombre);
        if (actor != null)
            return true;
        else
            return false;
    }

    public List<Actor> obtenerActores(){
        return actorRepo.findAll();
    }

    public Actor buscarPorId(ObjectId idActor) {
        Optional<Actor> opActor = actorRepo.findById(idActor);

        if (opActor.isPresent())
            return opActor.get();
        else
            return null;
    }

}