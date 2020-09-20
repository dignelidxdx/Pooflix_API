package ar.com.pooflix.api.entities;

import java.util.ArrayList;
import java.util.List;

public class Contenido {
    
    private String titulo;
    private ArrayList<String> premios = new ArrayList<>();
    private Director director;
    private Double tiempoVisto;
    private List<Genero> generos = new ArrayList<>();
    private Double calificacion;
    private List<Actor> actores = new ArrayList<>();

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<String> getPremios() {
        return premios;
    }

    public void setPremios(ArrayList<String> premios) {
        this.premios = premios;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Double getTiempoVisto() {
        return tiempoVisto;
    }

    public void setTiempoVisto(Double tiempoVisto) {
        this.tiempoVisto = tiempoVisto;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }
}
