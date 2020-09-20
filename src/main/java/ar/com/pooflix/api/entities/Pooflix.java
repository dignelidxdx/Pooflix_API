package ar.com.pooflix.api.entities;

import java.util.ArrayList;
import java.util.List;

public class Pooflix {

    private List<Pelicula> peliculas = new ArrayList<>();

    private List<Serie> series = new ArrayList<>();

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
