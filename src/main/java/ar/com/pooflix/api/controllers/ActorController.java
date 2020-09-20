package ar.com.pooflix.api.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pooflix.api.entities.Actor;
import ar.com.pooflix.api.entities.Serie;
import ar.com.pooflix.api.models.request.ActorRequest;
import ar.com.pooflix.api.models.response.GenericResponse;
import ar.com.pooflix.api.service.ActorService;
import ar.com.pooflix.api.service.SerieService;

@RestController
public class ActorController {
    @Autowired
    ActorService aS;

    @Autowired
    SerieService serieService;

    @PostMapping("/actores")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody ActorRequest aR) {

        Actor actor = aS.crearActor(aR.nombre, aR.nivel);
        if (actor == null) {
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk = true;
        gR.id = actor.get_id();
        gR.mensaje = "El actor fue creado exitosamente";
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/actores")
    public ResponseEntity<List<Actor>> traerTodosLosActores() {

        return ResponseEntity.ok(aS.obtenerActores());
    }

    @PutMapping("/actores/{id}")
    ResponseEntity<GenericResponse> modificarActor(@PathVariable ObjectId id, @RequestBody ActorRequest modifActor) {
        GenericResponse gR = new GenericResponse();
        Actor actor = aS.buscarPorId(id);
        if (actor == null) {
            return ResponseEntity.badRequest().build();
        }

        else {

            actor.setNombre(modifActor.nombre);
            actor.setNivel(modifActor.nivel);
            aS.crearNuevoActor(actor);
            gR.id = actor.get_id();
            gR.isOk = true;
            gR.mensaje = "El actor fue encontrado y modificado con exito";

        }
        return ResponseEntity.ok(gR);

    }

    @GetMapping("/actores/{id}/series")
    ResponseEntity<List<Serie>> seriesActor(@PathVariable ObjectId id) {

        return ResponseEntity.ok(serieService.obtenerSeriesByActor(id));

    }
}