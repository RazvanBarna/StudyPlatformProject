package ro.platformaStudiu.admins;

import ro.platformaStudiu.admins.Super.SuperAdminPage;
import ro.platformaStudiu.serviceClass.Exceptii.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.Roluri;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdaugaUtilizatorPage {

    private SqlConexiune conex;

    public AdaugaUtilizatorPage(Stage primaryStage, SqlConexiune sql, String nume, String prenume,int rolID, int userID) {
        this.conex = sql;

        // Creare layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Label statusLabel= new Label();
        statusLabel.setFont(new Font(14));
        statusLabel.setTextFill(Color.BLUE);


        // ComboBox pentru rol
        Label rolLabel = new Label("Rol:");
        ComboBox<Roluri> comboBoxRoluri = new ComboBox<>();
        List<Roluri> roluri = getRoluriInscris();
        comboBoxRoluri.getItems().addAll(roluri);

        // Câmpuri pentru introducerea datelor
        Label cnpLabel = new Label("CNP:");
        TextField cnpField = new TextField();

        Label numeLabel = new Label("Nume:");
        TextField numeField = new TextField();

        Label prenumeLabel = new Label("Prenume:");
        TextField prenumeField = new TextField();

        Label numarTelefonLabel = new Label("Numar Telefon:");
        TextField numarTelefonField = new TextField();

        Label adresaLabel = new Label("Adresa:");
        TextField adresaField = new TextField();

        Label ibanLabel = new Label("IBAN:");
        TextField ibanField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label nrContractLabel = new Label("Numar Contract:");
        TextField nrContractField = new TextField();

        Label parolaLabel = new Label("Parola:");
        PasswordField parolaField = new PasswordField();

        // Campuri pentru student
        Label anDeStudiuLabel = new Label("An de Studii:");
        TextField anDeStudiuField = new TextField();

        Label numarOreLabel = new Label("Numar Ore Sustinute:");
        TextField numarOreField = new TextField();

        // Câmpuri pentru profesor
        Label nr_max_oreLabel = new Label("Nr_max_ore");
        TextField nr_max_oreField = new TextField();

        Label nr_min_oreLabel = new Label("Nr_min_ore");
        TextField nr_min_oreField = new TextField();

        Label departamentLabel = new Label("Departament");
        TextField departamentField = new TextField();

        Label materieLabel = new Label("Materie");
        TextField materieField = new TextField();

        Label nr_max_studentiLabel = new Label("Nr_max_studenti");
        TextField nr_max_studentiField = new TextField();

        // Inițial, câmpurile pentru student sunt ascunse
        anDeStudiuLabel.setVisible(false);
        anDeStudiuField.setVisible(false);
        numarOreLabel.setVisible(false);
        numarOreField.setVisible(false);

        // Inițial, campurile pentru profesori sunt ascunse
        nr_max_oreLabel.setVisible(false);
        nr_max_oreField.setVisible(false);
        nr_min_oreLabel.setVisible(false);
        nr_min_oreField.setVisible(false);
        departamentLabel.setVisible(false);
        departamentField.setVisible(false);
        nr_max_studentiLabel.setVisible(false);
        nr_max_studentiField.setVisible(false);
        materieLabel.setVisible(false);
        materieField.setVisible(false);

        Button inapoi = new Button("Inapoi");
        inapoi.setOnAction(e -> {
            primaryStage.close();
            if(rolID==0) {
                new SuperAdminPage(primaryStage, sql, nume, prenume, rolID, userID);
            }
            else {
                new AdminPage(primaryStage, sql, nume, prenume, rolID, userID);
            }
        });

        Button adaugaButton = new Button("Adaugă");
        adaugaButton.setOnAction(e -> {

                int selectedRoleId = comboBoxRoluri.getValue().getIdRol();
                if ((selectedRoleId == 0 || selectedRoleId == 1) && rolID != 0) {
                    statusLabel.setText("Nu aveți dreptul să adăugați Admini sau SuperAdmini!");
                    statusLabel.setTextFill(Color.RED);
                }

            try {
                if (comboBoxRoluri.getValue().getIdRol() == 3) {
                    // Adaugă un student
                    String CNP = cnpField.getText();
                    String numeUtilizator = numeField.getText();
                    String prenumeUtilizator = prenumeField.getText();
                    String numarTelefon = numarTelefonField.getText();
                    String adresa = adresaField.getText();
                    String IBAN = ibanField.getText();
                    String email = emailField.getText();
                    int nrContract = Integer.parseInt(nrContractField.getText());
                    String parola = parolaField.getText();
                    int anDeStudiu = Integer.parseInt(anDeStudiuField.getText());
                    int numarOreSustinute = Integer.parseInt(numarOreField.getText());
                    adaugaStudent(statusLabel, CNP, numeUtilizator, prenumeUtilizator, numarTelefon, adresa, IBAN, email, nrContract, parola, anDeStudiu, numarOreSustinute);

                } else if (comboBoxRoluri.getValue().getIdRol() == 2) {
                    String CNP = cnpField.getText();
                    String numeUtilizator = numeField.getText();
                    String prenumeUtilizator = prenumeField.getText();
                    String numarTelefon = numarTelefonField.getText();
                    String adresa = adresaField.getText();
                    String IBAN = ibanField.getText();
                    String email = emailField.getText();
                    int nrContract = Integer.parseInt(nrContractField.getText());
                    String parola = parolaField.getText();
                    int nr_ore_max = Integer.parseInt(nr_max_oreField.getText());
                    int nr_ore_min = Integer.parseInt(nr_min_oreField.getText());
                    String departament = departamentField.getText();
                    int nr_max_studenti = Integer.parseInt(nr_max_studentiField.getText());
                    String materie = materieField.getText();
                    adaugaProfesor(statusLabel, materie, CNP, nume, prenume, numarTelefon, adresa,
                            IBAN, email, nrContract, parola, nr_ore_max, nr_ore_min, departament, nr_max_studenti);

                }
                if (comboBoxRoluri.getValue().getIdRol() == 1 && rolID == 0) {
                    String CNP = cnpField.getText();
                    String numeUtilizator = numeField.getText();
                    String prenumeUtilizator = prenumeField.getText();
                    String numarTelefon = numarTelefonField.getText();
                    String adresa = adresaField.getText();
                    String IBAN = ibanField.getText();
                    String email = emailField.getText();
                    int nrContract = Integer.parseInt(nrContractField.getText());
                    String parola = parolaField.getText();
                    adaugaAdmin(statusLabel, CNP, numeUtilizator, prenumeUtilizator, numarTelefon, adresa, IBAN, email, nrContract, parola);
                }
            }

            catch (InvalidYearException | InvalidDurationException | InvalidCNPException |
                    InvalidPhoneNumberException | InvalidIBANException | InvalidEmailException ex) {
                statusLabel.setText(ex.getMessage());
                statusLabel.setTextFill(Color.RED);

            } catch (NumberFormatException ex) {
                statusLabel.setText("Numărul contractului, anul de studiu și numărul de ore susținute trebuie să fie cifre!");
                statusLabel.setTextFill(Color.RED);

            } catch (NullPointerException ex) {
                statusLabel.setText("Nu adaugati litere unde trebuie adaugate doar cifre!");
                statusLabel.setTextFill(Color.RED);
                ex.printStackTrace();

            } catch (Exception ex) {
                statusLabel.setText("Eroare neașteptată: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
                ex.printStackTrace();
            }
        });

        comboBoxRoluri.setOnAction(e -> {
            if (comboBoxRoluri.getValue().getIdRol() == 3) {  // Student
                anDeStudiuLabel.setVisible(true);
                anDeStudiuField.setVisible(true);
                numarOreLabel.setVisible(true);
                numarOreField.setVisible(true);
            } else {
                anDeStudiuLabel.setVisible(false);
                anDeStudiuField.setVisible(false);
                numarOreLabel.setVisible(false);
                numarOreField.setVisible(false);
            }
            if (comboBoxRoluri.getValue().getIdRol() == 2) {  // profesor
                nr_max_oreLabel.setVisible(true);
                nr_max_oreField.setVisible(true);
                nr_min_oreLabel.setVisible(true);
                nr_min_oreField.setVisible(true);
                departamentLabel.setVisible(true);
                departamentField.setVisible(true);
                nr_max_studentiLabel.setVisible(true);
                nr_max_studentiField.setVisible(true);
                materieLabel.setVisible(true);
                materieField.setVisible(true);

            } else {
                nr_max_oreLabel.setVisible(false);
                nr_max_oreField.setVisible(false);
                nr_min_oreLabel.setVisible(false);
                nr_min_oreField.setVisible(false);
                departamentLabel.setVisible(false);
                departamentField.setVisible(false);
                nr_max_studentiLabel.setVisible(false);
                nr_max_studentiField.setVisible(false);
                materieLabel.setVisible(false);
                materieField.setVisible(false);
            }
        });

        // Adăugare elemente în layout
        layout.getChildren().addAll(
                cnpLabel, cnpField, numeLabel, numeField, prenumeLabel, prenumeField,
                numarTelefonLabel, numarTelefonField, adresaLabel, adresaField,
                ibanLabel, ibanField, emailLabel, emailField, nrContractLabel, nrContractField,
                parolaLabel, parolaField, anDeStudiuLabel, anDeStudiuField,numarOreLabel,numarOreField,nr_max_oreLabel,nr_max_oreField,nr_min_oreLabel,nr_min_oreField,departamentLabel,departamentField,
                nr_max_studentiLabel,nr_max_studentiField,materieLabel,materieField, comboBoxRoluri,statusLabel, adaugaButton, inapoi
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true); // Permite redimensionarea pe latine
        scrollPane.setPadding(new Insets(10));

        Scene scene = new Scene(scrollPane, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Căutare Utilizatori");
        primaryStage.show();
    }

    private void adaugaStudent(Label statusLabel, String CNP, String nume, String prenume, String numarTelefon, String adresa,
                               String IBAN, String email, int nrContract, String parola, int anDeStudiu,
                               int numarOreSustinute) throws InvalidYearException, InvalidDurationException,
            InvalidCNPException, InvalidPhoneNumberException,
            InvalidIBANException, InvalidEmailException,NullPointerException {


        try {
            if (anDeStudiu < 1 || anDeStudiu > 4) {
                throw new InvalidYearException("An de studiu invalid!");
            }
            if (numarOreSustinute <= 0 || nrContract < 0) {
                throw new InvalidDurationException("Numărul de ore și contractul trebuie să fie pozitive!");
            }
            if (CNP.length() != 13 || !(CNP.matches("\\d+"))) { //verific daca contine doar cifre
                throw new InvalidCNPException("CNP-ul trebuie să conțină exact 13 cifre!");
            }
            if (numarTelefon.length() != 10 || !(numarTelefon.matches("\\d+"))) {
                throw new InvalidPhoneNumberException("Numărul de telefon trebuie să aibă exact 10 cifre !");
            }
            if (IBAN.length() != 24) {
                throw new InvalidIBANException("IBAN-ul trebuie să conțină exact 24 de caractere!");
            }
            if (email.indexOf('@') == -1) {
                throw new InvalidEmailException("Adresa de email este invalidă!");
            }

            String query = "CALL AdaugaStudent(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
                stmt.setString(1, CNP);
                stmt.setString(2, nume);
                stmt.setString(3, prenume);
                stmt.setString(4, numarTelefon);
                stmt.setString(5, adresa);
                stmt.setString(6, IBAN);
                stmt.setString(7, email);
                stmt.setInt(8, nrContract);
                stmt.setString(9, parola);
                stmt.setInt(10, anDeStudiu);
                stmt.setInt(11, numarOreSustinute);

                stmt.executeUpdate();
                statusLabel.setText("Student adăugat cu succes!");
                statusLabel.setTextFill(Color.GREEN);
            }
        }
        catch (SQLException e){
            statusLabel.setText("Eroare "+e.getMessage());
        }
    }

    private void adaugaAdmin(Label statusLabel, String CNP, String nume, String prenume, String numarTelefon, String adresa,
                               String IBAN, String email, int nrContract, String parola) throws InvalidYearException, InvalidDurationException,
            InvalidCNPException, InvalidPhoneNumberException,
            InvalidIBANException, InvalidEmailException,NullPointerException {

        try {
            if (nrContract < 0) {
                throw new InvalidDurationException("Numărul de contract trebuie să fie pozitiv!");
            }
            if (CNP.length() != 13 || !(CNP.matches("\\d+"))) {
                throw new InvalidCNPException("CNP-ul trebuie să conțină exact 13 cifre!");
            }
            if (numarTelefon.length() != 10 || !(numarTelefon.matches("\\d+"))) {
                throw new InvalidPhoneNumberException("Numărul de telefon trebuie să aibă exact 10 cifre (07...)!");
            }
            if (IBAN.length() != 24) {
                throw new InvalidIBANException("IBAN-ul trebuie să conțină exact 24 de caractere!");
            }
            if (email.indexOf('@') == -1) {
                throw new InvalidEmailException("Adresa de email este invalidă!");
            }

            String query = "CALL AdaugaAdmin(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
                stmt.setString(1, CNP);
                stmt.setString(2, nume);
                stmt.setString(3, prenume);
                stmt.setString(4, numarTelefon);
                stmt.setString(5, adresa);
                stmt.setString(6, IBAN);
                stmt.setString(7, email);
                stmt.setInt(8, nrContract);
                stmt.setString(9, parola);

                stmt.executeUpdate();
                statusLabel.setText("Admin adăugat cu succes!");
                statusLabel.setTextFill(Color.GREEN);
        }
        } catch (SQLException e) {
            statusLabel.setText("Eroare " + e.getMessage());
        }
    }


    private void adaugaProfesor(Label statusLabel, String materie, String CNP, String nume, String prenume, String numarTelefon, String adresa,
                                String IBAN, String email, int nrContract, String parola, int nr_max_ore,
                                int nr_min_ore, String departament, int nr_max_studenti) throws InvalidYearException, InvalidDurationException,
            InvalidCNPException, InvalidPhoneNumberException,
            InvalidIBANException, InvalidEmailException,NullPointerException{

        try {
            if (nr_max_studenti <= 0 || nr_max_ore <= 0 || nr_min_ore <= 0 || nrContract < 0) {
                throw new InvalidDurationException("Numărul de studenți, ore și contractul trebuie să fie pozitive!");
            }
            if (CNP.length() != 13|| !(CNP.matches("\\d+"))) {
                throw new InvalidCNPException("CNP-ul trebuie să conțină exact 13 cifre!");
            }
            if (numarTelefon.length() != 10 || !(numarTelefon.matches("\\d+"))) {
                throw new InvalidPhoneNumberException("Numărul de telefon trebuie să aibă exact 10 cifre (07...)!");
            }
            if (IBAN.length() != 24) {
                throw new InvalidIBANException("IBAN-ul trebuie să conțină exact 24 de caractere!");
            }
            if (email.indexOf('@') == -1) {
                throw new InvalidEmailException("Adresa de email este invalidă!");
            }
            String query = "CALL AdaugaProfesor(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
                stmt.setString(1, materie);
                stmt.setString(2, CNP);
                stmt.setString(3, nume);
                stmt.setString(4, prenume);
                stmt.setString(5, numarTelefon);
                stmt.setString(6, adresa);
                stmt.setString(7, IBAN);
                stmt.setString(8, email);
                stmt.setInt(9, nrContract);
                stmt.setString(10, parola);
                stmt.setInt(11, nr_max_ore);
                stmt.setInt(12, nr_min_ore);
                stmt.setString(13, departament);
                stmt.setInt(14, nr_max_studenti);

                stmt.executeUpdate();
                statusLabel.setText("Profesor adăugat cu succes!");
                statusLabel.setTextFill(Color.GREEN);
            }

        } catch (SQLException e) {
            statusLabel.setText("Eroare " + e.getMessage());
        }
    }


    private List<Roluri> getRoluriInscris() {
        List<Roluri> roluri = new ArrayList<>();
        // Modificăm interogarea pentru a exclude Rol_id == 0
        String query = "SELECT DISTINCT Rol_id, Rol_nume FROM rol WHERE Rol_id != 0";

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                roluri.add(new Roluri(
                        rs.getInt("Rol_id"),
                        rs.getString("Rol_nume")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roluri;
    }

}
