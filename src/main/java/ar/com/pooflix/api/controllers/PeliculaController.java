package ar.com.pooflix.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.pooflix.api.entities.Pelicula;
import ar.com.pooflix.api.models.request.PeliculaRequest;
import ar.com.pooflix.api.models.response.GenericResponse;
import ar.com.pooflix.api.service.PeliculaService;

@RestController
public class PeliculaController {

    @Autowired
    PeliculaService peliService;

    @PostMapping("/peliculas")
    public ResponseEntity<GenericResponse> crearPelicula(@RequestBody PeliculaRequest peliReq) {
        Pelicula pelicula = new Pelicula();
        pelicula.setTitulo(peliReq.titulo);
        pelicula.setDuracion(peliReq.duracion);
        peliService.crearPelicula(pelicula);
        GenericResponse gR = new GenericResponse();
        gR.id = pelicula.get_id(); //.toHexString();
        gR.mensaje = "Pelicula creada con exito!!";
        gR.isOk = true;

        return ResponseEntity.ok(gR);
    }

    @GetMapping("/peliculas")
    public ResponseEntity<List<Pelicula>> listarPeliculas() {
        return ResponseEntity.ok(peliService.listarPeliculas());
    }
    
}
