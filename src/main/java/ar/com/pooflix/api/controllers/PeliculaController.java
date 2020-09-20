package ar.com.pooflix.api.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.pooflix.api.entities.Pelicula;
import ar.com.pooflix.api.models.request.PeliculaRequest;
import ar.com.pooflix.api.models.response.GenericResponse;
import ar.com.pooflix.api.service.PeliculaService;


@RestController
public class PeliculaController {

    @Autowired
    PeliculaService pS;

    @PostMapping("/pelicula")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody PeliculaRequest peliReq) {

        Pelicula pelicula = pS.crearPelicula(peliReq.titulo, peliReq.generos, peliReq.director, peliReq.actores,
                peliReq.filmadaEnImax);
        if (pelicula == null) {
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = pelicula.get_id();
        gR.mensaje = "La pelicula fue añadida exitosamente";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/peliculas")
    public ResponseEntity<List<Pelicula>> traerTodasLasPeliculas() {

        return ResponseEntity.ok(pS.obtenerPeliculas());
    }

    @GetMapping("/peliculas/{id}")
    public ResponseEntity<Pelicula> traerPeliculaPorId(@PathVariable ObjectId id) {
        return ResponseEntity.ok(pS.buscarPorId(id));
    }

    @GetMapping("/peliculas/titulo/{titulo}")
    public ResponseEntity<Pelicula> traerPeliculaPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(pS.buscarPorTitulo(titulo));
    }

    // /peliculas?genero=Ciencia%20Ficción
    @GetMapping("/peliculas/genero/{genero}")
    public ResponseEntity<List<Pelicula>> traerPeliculasPorGenero(@PathVariable String genero) {

        return ResponseEntity.ok(pS.buscarPorGenero(genero));

    }

    @PutMapping("/peliculas/{id}")
    public ResponseEntity<GenericResponse> modificarPelicula(@PathVariable ObjectId id, PeliculaRequest modifPeli) {
        Pelicula pelicula = pS.buscarPorId(id);
        if (pelicula == null) {
            return ResponseEntity.notFound().build();
        }

        else {

            Pelicula peliModificada = pS.modificarPelicula(pelicula, modifPeli);
            if (peliModificada == null) {
                return ResponseEntity.badRequest().build();
            } else {
                GenericResponse gR = new GenericResponse();
                gR.id = peliModificada.get_id();
                gR.isOk = true;
                gR.mensaje = "Los datos de la pelicula fueron actualizados exitosamente";

                return ResponseEntity.ok(gR);

            }
        }
    }


@PutMapping("/peliculas/calificacion/{id}")
ResponseEntity<GenericResponse> calificarLaPelicula(@PathVariable ObjectId id, double calificacion){

    Pelicula pelicula = pS.buscarPorId(id);
    if(pelicula ==null){
        return ResponseEntity.notFound().build();
    }else{
       Pelicula peliCalificada = pS.calificarPelicula(pelicula,calificacion);
       GenericResponse gR = new GenericResponse();
       gR.id = peliCalificada.get_id();
       gR.isOk = true;
       gR.mensaje = "enviaste tu calificacion exitosamente, el puntaje de la pelicula es: "+ peliCalificada.getCalificacion();
       return ResponseEntity.ok(gR);
    }

}




}