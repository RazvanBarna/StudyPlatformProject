package ro.platformaStudiu.student.catalog;

import javafx.beans.property.SimpleStringProperty;

public class Catalog {

    private final SimpleStringProperty materie;
    private final SimpleStringProperty nota;

    public Catalog(String materie, String nota) {
        this.materie = new SimpleStringProperty(materie);
        this.nota = new SimpleStringProperty(nota);
    }

    public SimpleStringProperty materieProperty() {
        return materie;
    }

    public SimpleStringProperty notaProperty() {
        return nota;
    }
}
