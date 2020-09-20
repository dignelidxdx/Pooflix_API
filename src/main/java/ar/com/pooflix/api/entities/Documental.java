package ar.com.pooflix.api.entities;

public class Documental {

    private String tipoDocumental;
    //Los tipos de documental son: 
    //Centrado en un acontecimiento.
    //Definitorio de un proceso. 
    //De viaje.
    //Ciudad amurallada.
    //Histórico.    

    public String getTipoDocumental() {
        return tipoDocumental;
    }

    public void setTipoDocumental(String tipoDocumental) {
        this.tipoDocumental = tipoDocumental;
    }
}
