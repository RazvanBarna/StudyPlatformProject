package ro.platformaStudiu.serviceClass;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Time;

public class Activitate {
    private final StringProperty descriere;
    private final StringProperty ora;
    private final StringProperty materie;
    private int id_activitate;
    private String ziua;

    public Activitate( String descriere, String ora,String materie) {
        this.descriere = new SimpleStringProperty(descriere);
        this.ora = new SimpleStringProperty(ora);
        this.materie=new SimpleStringProperty(materie);
    }
    public Activitate(String ziua,int activitateID){
        this.descriere=null;
        this.ora=null;
        this.materie=null;
        this.ziua=ziua;
        this.id_activitate=activitateID;
    }

    public StringProperty materieProperty() {return materie; }
    public StringProperty descriereProperty() { return descriere; }
    public StringProperty oraProperty() { return ora; }

    public String getZiua() {
        return ziua;
    }

    public void setZiua(String ziua) {
        this.ziua = ziua;
    }

    public int getId_activitate() {
        return id_activitate;
    }

    public void setId_activitate(int id_activitate) {
        this.id_activitate = id_activitate;
    }
    @Override
    public String toString() {
        return "Activitate{" +
                "idActivitate:" + id_activitate +
                ", ziua:'" + ziua + '\'';
    }
}
