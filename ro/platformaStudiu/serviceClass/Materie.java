package ro.platformaStudiu.serviceClass;

public class Materie {
    private  int idMaterie;
    private  String numeMaterie;

    public Materie(int idMaterie, String numeMaterie) {
        this.idMaterie = idMaterie;
        this.numeMaterie = numeMaterie;
    }

    public int getIdMaterie() {
        return idMaterie;
    }

    public String getNumeMaterie() {
        return numeMaterie;
    }

    @Override
    public String toString() {
        return idMaterie+" "+numeMaterie;
    }
}
