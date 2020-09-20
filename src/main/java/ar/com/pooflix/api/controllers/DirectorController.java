package ar.com.pooflix.api.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.com.pooflix.api.entities.Director;
import ar.com.pooflix.api.models.request.DirectorRequest;
import ar.com.pooflix.api.models.response.GenericResponse;
import ar.com.pooflix.api.service.DirectorService;

@RestController
public class DirectorController {

    @Autowired
    DirectorService dS;

    
    @PostMapping("/director")
    public ResponseEntity<GenericResponse> crearActor(@RequestBody DirectorRequest aR) {
         
        Director director = dS.crearDirector(aR.name, aR.estilo);
        if(director == null){
            return ResponseEntity.badRequest().build();
        }
        GenericResponse gR = new GenericResponse();
        gR.isOk= true;
        gR.id = director.get_id(); //.toHexString();
        gR.mensaje = "El Director fue creado exitosamente";
        return ResponseEntity.ok(gR);
    
    }

    @GetMapping("/director")
    public ResponseEntity<List<Director>> traerTodosLosDirectores(){
        

        return ResponseEntity.ok(dS.obtenerDirectores());
    }
    @GetMapping("/director/{id}")
    public ResponseEntity<Director> traerDirectorPorId(@PathVariable ObjectId id){
        

        return ResponseEntity.ok(dS.buscarPorId(id));
    }

    @PutMapping("/director/{id}")
    ResponseEntity<GenericResponse> modificarDirector(@PathVariable ObjectId id, @RequestBody DirectorRequest modifDirec) {
        GenericResponse gR = new GenericResponse();
        Director director = dS.buscarPorId(id);
        if (director == null) {
            return ResponseEntity.badRequest().build();
        }

        else {

            director.setNombre(modifDirec.name);
            director.setEstilo(modifDirec.estilo);
            dS.crearNuevoDirector(director);
            gR.id = director.get_id(); //.toHexString();
            gR.isOk = true;
            gR.mensaje = "El Director fue encontrado y modificado con exito";

        }
        return ResponseEntity.ok(gR);

    }
}