package ar.com.pooflix.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.pooflix.api.entities.Genero;
import ar.com.pooflix.api.repos.PeliculaRepository;
import ar.com.pooflix.api.repos.SerieRepository;

@Service
public class GeneroService {
    
    @Autowired
    PeliculaRepository pRepo;
    @Autowired
    SerieRepository sRepo;
    @Autowired
    PeliculaService pS;

    public List<Genero> obtenerGeneros(){
        return pS.obtenerPeliculas().stream().map(p -> p.getGeneros()).flatMap(List::stream)
        .collect(Collectors.toList());
    }

}