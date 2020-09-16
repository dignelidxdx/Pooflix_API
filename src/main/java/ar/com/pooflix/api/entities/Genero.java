package ar.com.pooflix.api.entities;

import org.bson.types.ObjectId;

public class Genero {
    
    private ObjectId _id;
    private String nombre;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
