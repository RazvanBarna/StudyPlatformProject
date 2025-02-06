package ro.platformaStudiu.student;

import ro.platformaStudiu.serviceClass.User;

public class Student extends User {
    private int student_id;
    private int an_de_studiu;
    private int nr_ore;

    public Student(int rolId, int userId, String nume, String prenume, String CNP, String nrTelefon, String adresa, String IBAN, String email, int nrContract, int nr_ore,
                   int student_id, int an_de_studiu) {
        super(rolId, userId, nume, prenume, CNP, nrTelefon, adresa, IBAN, email, nrContract);
        this.nr_ore = nr_ore;
        this.student_id=student_id;
        this.an_de_studiu=an_de_studiu;
    }

    public Student(String nume, String prenume){
        super(nume,prenume);
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getAn_de_studiu() {
        return an_de_studiu;
    }

    public void setAn_de_studiu(int an_de_studiu) {
        this.an_de_studiu = an_de_studiu;
    }

    public int getNr_ore() {
        return nr_ore;
    }

    public void setNr_ore(int nr_ore) {
        this.nr_ore = nr_ore;
    }
}