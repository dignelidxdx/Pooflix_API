package ar.com.pooflix.api.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pooflix.api.entities.Actor;
import ar.com.pooflix.api.entities.Pelicula;
import ar.com.pooflix.api.entities.Premio;
import ar.com.pooflix.api.repos.PeliculaRepository;

@Service
public class PeliculaService {
    
    @Autowired
    PeliculaRepository peliRepo;

    public void crearPelicula(Pelicula pelicula){
        peliRepo.save(pelicula);
    }

    public List<Pelicula> listarPeliculas() {
        return peliRepo.findAll();
    }

    public Pelicula buscarPorId(ObjectId peliId) {
        return peliRepo.findBy_id(peliId);
    }

    public Premio crearPremio(ObjectId peliId, String nombre, int anio, String categoria) {
        Premio premio = new Premio();
        Pelicula pelicula = buscarPorId(peliId);
        premio.setNombre(nombre);
        premio.setAnio(anio);
        premio.setCategoria(categoria);
        pelicula.getPremios().add(premio);
        this.crearPelicula(pelicula);

        return premio;
    }

    public Actor crearActor(ObjectId peliId, String nombre, Integer edad, String nivel, Integer posicion) {
        Actor actor = new Actor();
        Pelicula pelicula = buscarPorId(peliId);

        actor.setName(nombre);
        actor.setEdad(edad);
        actor.setNivel(nivel);
        actor.setPosicion(posicion);
        pelicula.getActores().add(actor);

        crearPelicula(pelicula);

        return actor;
    }   

    public Actor crearGenero(ObjectId peliId, String nombre, Integer edad, String nivel, Integer posicion) {
        Actor actor = new Actor();
        Pelicula pelicula = buscarPorId(peliId);

        actor.setName(nombre);
        actor.setEdad(edad);
        actor.setNivel(nivel);
        actor.setPosicion(posicion);
        pelicula.getActores().add(actor);

        crearPelicula(pelicula);

        return actor;
    }




}
