package ro.platformaStudiu.student.grupStudiu;

public class GrupStudiu {
    private final int idGrup;
    private final String materie;

    public GrupStudiu(int idGrup, String materie) {
        this.idGrup = idGrup;
        this.materie = materie;
    }

    public int getIdGrup() {
        return idGrup;
    }

    public String getMaterie() {
        return materie;
    }

    @Override
    public String toString() {
        return "ID: " + idGrup + " - Materie: " + materie;
    }
}
