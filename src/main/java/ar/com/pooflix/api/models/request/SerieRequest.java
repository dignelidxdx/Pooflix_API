package ar.com.pooflix.api.models.request;

import java.util.ArrayList;
import java.util.List;

import ar.com.pooflix.api.entities.Actor;
import ar.com.pooflix.api.entities.Director;
import ar.com.pooflix.api.entities.Genero;
import ar.com.pooflix.api.entities.Temporada;

public class SerieRequest {
    public String titulo;
    public List<String> premios = new ArrayList<>();
    public Director director;
    public List<Genero> generos = new ArrayList<>();
    public List<Actor> actores = new ArrayList<>();
    public List<Temporada> temporadas = new ArrayList<>();
}
