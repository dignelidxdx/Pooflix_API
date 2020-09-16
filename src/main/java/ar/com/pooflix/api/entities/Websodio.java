package ar.com.pooflix.api.entities;

import org.bson.types.ObjectId;

public class Websodio {
    
    private ObjectId _id;
    private String url;

    public String get_id() {
        return _id.toHexString();
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
