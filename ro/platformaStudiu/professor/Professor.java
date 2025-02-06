package ro.platformaStudiu.professor;

import ro.platformaStudiu.serviceClass.User;

public class Professor extends User {
    private int profesor_id;
    private int nr_max_ore;
    private int nr_min_ore;
    private String departament;
    private int nr_max_studenti;
    private int Numar_curent_studenti;
    private int nr_curent_ore;

    public Professor(int rolId, int userId, String nume, String prenume, String CNP, String nrTelefon, String adresa, String IBAN,
                     String email, int nrContract, int profesor_id, int nr_max_ore, int nr_min_ore, String departament,
                     int nr_max_studenti, int numar_curent_studenti, int nr_curent_ore) {
        super(rolId, userId, nume, prenume, CNP, nrTelefon, adresa, IBAN, email, nrContract);
        this.profesor_id = profesor_id;
        this.nr_max_ore = nr_max_ore;
        this.nr_min_ore = nr_min_ore;
        this.departament = departament;
        this.nr_max_studenti = nr_max_studenti;
        this.Numar_curent_studenti = numar_curent_studenti;
        this.nr_curent_ore = nr_curent_ore;
    }

    public int getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }

    public int getNr_max_ore() {
        return nr_max_ore;
    }

    public void setNr_max_ore(int nr_max_ore) {
        this.nr_max_ore = nr_max_ore;
    }

    public int getNr_min_ore() {
        return nr_min_ore;
    }

    public void setNr_min_ore(int nr_min_ore) {
        this.nr_min_ore = nr_min_ore;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public int getNr_max_studenti() {
        return nr_max_studenti;
    }

    public void setNr_max_studenti(int nr_max_studenti) {
        this.nr_max_studenti = nr_max_studenti;
    }

    public int getNumar_curent_studenti() {
        return Numar_curent_studenti;
    }

    public void setNumar_curent_studenti(int numar_curent_studenti) {
        Numar_curent_studenti = numar_curent_studenti;
    }

    public int getNr_curent_ore() {
        return nr_curent_ore;
    }

    public void setNr_curent_ore(int nr_curent_ore) {
        this.nr_curent_ore = nr_curent_ore;
    }
}
