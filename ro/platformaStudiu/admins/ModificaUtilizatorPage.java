package ro.platformaStudiu.admins;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.platformaStudiu.admins.Super.SuperAdminPage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.serviceClass.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModificaUtilizatorPage {
    private SqlConexiune conex;

    public ModificaUtilizatorPage(Stage primaryStage, SqlConexiune sql, String nume, String prenume,int rolID, int userID) {
        this.conex = sql;

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        Button inapoi = new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            if(rolID==0) {
                new SuperAdminPage(primaryStage, sql, nume, prenume, rolID, userID);
            }
            else {
                new AdminPage(primaryStage, sql, nume, prenume, rolID, userID);
            }
        });


        TableView<User> tableView = initializeTableView();
        loadUsersInTable(tableView,rolID);
        Label statusLabel=new Label();
        statusLabel.setTextFill(Color.BLUE);
        statusLabel.setFont(new Font("Times New Roman", 14));

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
                statusLabel.setText("Datele s-au salvat cu succes!");
                statusLabel.setTextFill(Color.GREEN);
                saveAllChanges(tableView);
        });

        layout.getChildren().addAll(tableView, saveButton,statusLabel, inapoi);

        Scene scene = new Scene(layout, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Modificare Utilizatori");
        primaryStage.show();
    }

    private TableView<User> initializeTableView() {
        TableView<User> tableView = new TableView<>();

        TableColumn<User, String> cnpColumn = new TableColumn<>("CNP");
        cnpColumn.setCellValueFactory(new PropertyValueFactory<>("CNP"));
        cnpColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        cnpColumn.setOnEditCommit(e -> e.getRowValue().setCNP(e.getNewValue()));

        TableColumn<User, String> numeColumn = new TableColumn<>("Nume");
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        numeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numeColumn.setOnEditCommit(e -> e.getRowValue().setNume(e.getNewValue()));

        TableColumn<User, String> prenumeColumn = new TableColumn<>("Prenume");
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        prenumeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        prenumeColumn.setOnEditCommit(e -> e.getRowValue().setPrenume(e.getNewValue()));

        TableColumn<User, String> numarTelefonColumn = new TableColumn<>("Numar Telefon");
        numarTelefonColumn.setCellValueFactory(new PropertyValueFactory<>("NumarTelefon"));
        numarTelefonColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numarTelefonColumn.setOnEditCommit(e -> e.getRowValue().setNumarTelefon(e.getNewValue()));

        TableColumn<User, String> adresaColumn = new TableColumn<>("Adresa");
        adresaColumn.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        adresaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        adresaColumn.setOnEditCommit(e -> e.getRowValue().setAdresa(e.getNewValue()));

        TableColumn<User, String> ibanColumn = new TableColumn<>("IBAN");
        ibanColumn.setCellValueFactory(new PropertyValueFactory<>("IBAN"));
        ibanColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ibanColumn.setOnEditCommit(e -> e.getRowValue().setIBAN(e.getNewValue()));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(e -> e.getRowValue().setEmail(e.getNewValue()));

        TableColumn<User, String> parolaColumn = new TableColumn<>("Parola");
        parolaColumn.setCellValueFactory(new PropertyValueFactory<>("parola"));
        parolaColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        parolaColumn.setOnEditCommit(e -> e.getRowValue().setParola(e.getNewValue()));


        tableView.getColumns().addAll(cnpColumn, numeColumn, prenumeColumn, numarTelefonColumn, adresaColumn, ibanColumn, emailColumn, parolaColumn);
        tableView.setEditable(true);

        return tableView;
    }

    private void loadUsersInTable(TableView<User> tableView, int rol_id) {
        List<User> users = new ArrayList<>();
        String query;
        if(rol_id==1){
         query = "SELECT user_id, CNP, nume, prenume, numarTelefon, adresa, IBAN, email, parola, rol_id " +
                "FROM utilizatori WHERE rol_id IN (2, 3)";}
        else {
            query = "SELECT user_id, CNP, nume, prenume, numarTelefon, adresa, IBAN, email, parola, rol_id " +
                    "FROM utilizatori WHERE rol_id IN (0,1, 2, 3)";
        }

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getString("CNP"),
                        rs.getString("numarTelefon"),
                        rs.getString("adresa"),
                        rs.getString("IBAN"),
                        rs.getString("email"),
                        rs.getString("parola"))
                );
            }
            tableView.getItems().setAll(users);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveAllChanges(TableView<User> tableView) {

        for (User user : tableView.getItems()) {
            updateUserInDatabase(user);
        }
    }

    private void updateUserInDatabase(User user) {
        String updateQuery = "UPDATE utilizatori SET nume = ?, prenume = ?, CNP = ?, numarTelefon = ?, adresa = ?, IBAN = ?, email = ?, parola = ? WHERE user_id = ?";

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(updateQuery)) {
            stmt.setString(1, user.getNume());
            stmt.setString(2, user.getPrenume());
            stmt.setString(3, user.getCNP());
            stmt.setString(4, user.getNumarTelefon());
            stmt.setString(5, user.getAdresa());
            stmt.setString(6, user.getIBAN());
            stmt.setString(7, user.getEmail());
            stmt.setString(8, user.getParola());
            stmt.setInt(9, user.getUserId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
