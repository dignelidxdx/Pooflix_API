package ar.com.pooflix.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pooflix.api.entities.Actor;
import ar.com.pooflix.api.entities.Director;
import ar.com.pooflix.api.entities.Genero;
import ar.com.pooflix.api.entities.Pelicula;
import ar.com.pooflix.api.models.request.PeliculaRequest;
import ar.com.pooflix.api.repos.PeliculaRepository;

@Service
public class PeliculaService {

    @Autowired
    PeliculaRepository peliRepo;

    public boolean crearNuevaPelicula(Pelicula pelicula) {
        peliRepo.save(pelicula);
        return true;
    }

    public Pelicula crearPelicula(String titulo, List<Genero> generos, Director director, List<Actor> actores,
            boolean filmadaEnImax) {
        if (existePelicula(titulo)) {
            return null;
        } else {
            Pelicula pelicula = new Pelicula();
            pelicula.setTitulo(titulo);
            pelicula.setGeneros(generos);
            pelicula.setDirector(director);
            pelicula.setActores(actores);
            pelicula.setFilmadaEnImax(filmadaEnImax);

            boolean peliculaCreada = crearNuevaPelicula(pelicula);
            if (peliculaCreada)
                return pelicula;
            else
                return null;
        }

    }

    boolean existePelicula(String titulo) {
        Pelicula pelicula = peliRepo.findByTitulo(titulo);
        if (pelicula != null)
            return true;
        else
            return false;
    }

    public Pelicula buscarPorTitulo(String titulo) {
        Pelicula pelicula = peliRepo.findByTitulo(titulo);
        return pelicula;
    }

    public List<Pelicula> obtenerPeliculas() {
        return peliRepo.findAll();
    }

    public Pelicula buscarPorId(ObjectId idPelicula) {
        Optional<Pelicula> opPelicula = peliRepo.findById(idPelicula);

        if (opPelicula.isPresent())
            return opPelicula.get();
        else
            return null;
    }

    public boolean tieneGenero(Pelicula pelicula, String genero) {
        return pelicula.getGeneros().stream().filter(g -> g.getNombreGenero().equals(genero)).count() > 0;
    }

    public List<Pelicula> buscarPorGenero(String genero) {

        return obtenerPeliculas().stream().filter(p -> this.tieneGenero(p, genero)).collect(Collectors.toList());
        
        /*
         * return obtenerPeliculas().stream() .filter(p-> p.getGeneros().stream()
         * .filter(g -> g.getNombreGenero().equals(genero)).count() >
         * 0).collect(Collectors.toList());
         */
    }

    public Pelicula modificarPelicula(Pelicula pelicula, PeliculaRequest modifPelicula) {

        pelicula.setTitulo(modifPelicula.titulo);
        pelicula.setGeneros(modifPelicula.generos);
        pelicula.setActores(modifPelicula.actores);
        pelicula.setDirector(modifPelicula.director);
        pelicula.setFilmadaEnImax(modifPelicula.filmadaEnImax);

        boolean peliculaModificada = this.crearNuevaPelicula(pelicula);
        if (peliculaModificada) {
            return pelicula;
        } else {
            return null;
        }

    }

    public Pelicula calificarPelicula(Pelicula peli, Double calificacion) {

        peli.setCalificacion(calificacion);
        boolean peliCalificada = this.crearNuevaPelicula(peli);
        if (peliCalificada) {
            return peli;

        } else {
            return null;
        }

    }

}