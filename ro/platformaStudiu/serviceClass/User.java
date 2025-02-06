package ro.platformaStudiu.serviceClass;

public class User {
    private int Rol_id;
    private int user_id;
    private String nume;
    private String prenume;
    private String CNP;
    private String NumarTelefon;
    private String adresa;
    private String IBAN;
    private String email;
    private int Nr_contract;
    private String parola;

    public User(int rolId, int userId, String nume, String prenume, String CNP, String nrTelefon, String adresa, String IBAN, String email, int nrContract) {
        this.Rol_id = rolId;
        this.user_id = userId;
        this.nume = nume;
        this.prenume = prenume;
        this.CNP = CNP;
        this.NumarTelefon = nrTelefon;
        this.adresa = adresa;
        this.IBAN = IBAN;
        this.email = email;
        this.Nr_contract = nrContract;
    }

    public User(int user_id, String nume, String prenume, String CNP, String NumarTelefon, String adresa, String IBAN, String email, String parola){
        this.user_id=user_id;
        this.nume = nume;
        this.prenume = prenume;
        this.CNP = CNP;
        this.NumarTelefon = NumarTelefon;
        this.adresa = adresa;
        this.IBAN = IBAN;
        this.email = email;
        this.parola=parola;
    }
    public User(int rolId, int userId, String nume, String prenume, String CNP, String nrTelefon, String adresa, String IBAN, String email, int nrContract, String parola) {
        this.Rol_id = rolId;
        this.user_id = userId;
        this.nume = nume;
        this.prenume = prenume;
        this.CNP = CNP;
        this.NumarTelefon = nrTelefon;
        this.adresa = adresa;
        this.IBAN = IBAN;
        this.email = email;
        this.Nr_contract = nrContract;
        this.parola=parola;
    }
    public User(String nume, String prenume){
        this.nume=nume;
        this.prenume=prenume;
    }
    public User(String nume, String prenume, String email){
        this.nume=nume;
        this.prenume=prenume;
        this.email=email;
    }
    public User(String nume, String prenume, int rol_id){
        this.nume=nume;
        this.prenume=prenume;
        this.Rol_id=rol_id;
    }

    public int getRolId() {
        return Rol_id;
    }

    public void setRolId(int rolId) {
        this.Rol_id = rolId;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }


    public void setNrTelefon(String nrTelefon) {
        this.NumarTelefon = nrTelefon;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNrContract() {
        return Nr_contract;
    }

    public void setNrContract(int nrContract) {
        this.Nr_contract = nrContract;
    }


    public int getRol_id() {
        return Rol_id;
    }

    public void setRol_id(int rol_id) {
        Rol_id = rol_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setNumarTelefon(String numarTelefon) {
        NumarTelefon = numarTelefon;
    }

    public int getNr_contract() {
        return Nr_contract;
    }

    public void setNr_contract(int nr_contract) {
        Nr_contract = nr_contract;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getNumarTelefon() {
        return NumarTelefon;
    }

    public String getParola() {
        return parola;
    }


    @Override
    public String toString() {
        return String.format("ID: %d, CNP: %s, Nume: %s, Prenume: %s, Telefon: %s, Adresa: %s, IBAN: %s, Email: %s, Contract: %d, Rol: %d, Parola: %s",
                user_id, CNP, nume, prenume, NumarTelefon, adresa, IBAN, email, Nr_contract, Rol_id,parola);
    }
}