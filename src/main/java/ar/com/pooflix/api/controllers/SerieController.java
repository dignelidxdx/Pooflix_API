package ar.com.pooflix.api.controllers;

import java.util.*;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.pooflix.api.entities.Capitulo;
import ar.com.pooflix.api.entities.Serie;
import ar.com.pooflix.api.entities.Temporada;
import ar.com.pooflix.api.models.request.SerieRequest;
import ar.com.pooflix.api.models.response.GenericResponse;
import ar.com.pooflix.api.service.SerieService;

@RestController
public class SerieController {

    @Autowired
    SerieService sS;

    @PostMapping("/series")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody SerieRequest serieReq) {

        Serie serie = sS.crearSerie(serieReq.titulo, serieReq.generos, serieReq.director, serieReq.actores,
                serieReq.temporadas);
        if (serie == null) {
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = serie.get_id();
        gR.mensaje = "La Serie fue añadida exitosamente";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/series")
    public ResponseEntity<List<Serie>> traerTodasLasSeries() {

        return ResponseEntity.ok(sS.obtenerSerie());
    }

    @GetMapping("/series/{id}")
    public ResponseEntity<Serie> traerSeriePorId(@PathVariable ObjectId id) {

        return ResponseEntity.ok(sS.buscarPorId(id));
    }

    @GetMapping("/series/titulo/{titulo}")
    public ResponseEntity<Serie> traerSeriePorTitulo(@PathVariable String titulo) {

        return ResponseEntity.ok(sS.buscarPorTitulo(titulo));
    }

    // /series?genero=Ciencia%20Ficción
    @GetMapping("/series/genero/{genero}")
    public ResponseEntity<List<Serie>> traerSeriesPorGenero(@PathVariable String genero) {

        return ResponseEntity.ok(sS.buscarPorGenero(genero));

    }

    @GetMapping("/series/temporadas/{id}")
    public ResponseEntity<List<Temporada>> traerTemporadasPorSerieId(@PathVariable ObjectId id) {

        return ResponseEntity.ok(sS.traerTemporadasPorSerieId(id));
    }

    //
    @GetMapping("/series/{id}/episodios")
    public ResponseEntity<List<Capitulo>> traerEpisodios(@PathVariable ObjectId id) {

        return ResponseEntity.ok(sS.obtenerEpisodiosSerie(id));

    }

    // @GetMapping("/series/{id}/temporadas/{nroTemporada}/episodios/{nroEpisodio}")
    @GetMapping("/series/{id}/episodios/{nroTemporada}-{nroEpisodio}")
    public ResponseEntity<Capitulo> traerEpisodio(@PathVariable ObjectId id, @PathVariable int nroTemporada,
            @PathVariable int nroEpisodio) {

        Capitulo episodio = sS.obtenerEpisodioPorNroEpisodio(id, nroTemporada, nroEpisodio);
        if (episodio == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(episodio);

    }

    @PutMapping("/series/{id}")
    public ResponseEntity<GenericResponse> modificarSerie(@PathVariable ObjectId id, SerieRequest modifSerie) {
        Serie serie = sS.buscarPorId(id);
        if (serie == null) {
            return ResponseEntity.notFound().build();
        }

        else {

            Serie serieModificada = sS.modificarPelicula(serie, modifSerie);
            if (serieModificada == null) {
                return ResponseEntity.badRequest().build();
            } else {
                GenericResponse gR = new GenericResponse();
                gR.id = serieModificada.get_id();
                gR.isOk = true;
                gR.mensaje = "Los datos de la Serie fueron actualizados exitosamente";

                return ResponseEntity.ok(gR);

            }
        }
    }

    @PutMapping("/series/calificacion/{id}")
    ResponseEntity<GenericResponse> calificarLaSerie(@PathVariable ObjectId id, double calificacion) {

        Serie serie = sS.buscarPorId(id);
        if (serie == null) {
            return ResponseEntity.notFound().build();
        } else {
            Serie serieCalificada = sS.calificarSerie(serie, calificacion);
            GenericResponse gR = new GenericResponse();
            gR.id = serieCalificada.get_id();
            gR.isOk = true;
            gR.mensaje = "enviaste tu calificacion exitosamente, el puntaje de la Serie es: "
                    + serieCalificada.getCalificacion();
            return ResponseEntity.ok(gR);
        }

    }

}