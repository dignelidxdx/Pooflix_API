package ar.com.pooflix.api.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "peliculas")
public class Pelicula {
    
    private ObjectId _id;
    private boolean filmadaEnImax;

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public boolean isFilmadaEnImax() {
        return filmadaEnImax;
    }

    public void setFilmadaEnImax(boolean filmadaEnImax) {
        this.filmadaEnImax = filmadaEnImax;
    }
    
  
}
