package ar.com.pooflix.api.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Genero {
    
    private String nombre;
    private List<Pelicula> peliculas = new ArrayList<>();
    private List<Serie> series = new ArrayList<>();


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

}
