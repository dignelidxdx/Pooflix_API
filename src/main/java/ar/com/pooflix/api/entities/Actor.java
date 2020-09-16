package ar.com.pooflix.api.entities;

public class Actor extends Persona {
    
    private String nivel;
    private Integer posicion;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }
}
