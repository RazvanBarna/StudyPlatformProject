package ro.platformaStudiu.admins;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ro.platformaStudiu.admins.Super.SuperAdminPage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.serviceClass.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CautaUtilizatorNumePage {
    private SqlConexiune conex;

    public CautaUtilizatorNumePage(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int rolID,int userID){
        this.conex=sql;

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        Label userSearchLabel = new Label("Căutare utilizatori");
        userSearchLabel.setFont(new Font("Times New Roman", 14));
        TextField userSearchField = new TextField();
        userSearchField.setPromptText("Introdu numele utilizatorului");
        TextField userPrenumeField = new TextField();
        userPrenumeField.setPromptText("Introdu prenumele utilizatorului");

        Button searchUsersButton = new Button("Caută");
        searchUsersButton.setFont(new Font("Times New Roman", 14));
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);


        searchUsersButton.setOnAction(event -> {
            String searchNume = userSearchField.getText();
            String searchPrenume = userPrenumeField.getText();
            try {
                List<User> users = cautaUtilizatori(searchNume, searchPrenume);
                if (users.isEmpty()) {
                    resultArea.setText("Nu au fost găsiți utilizatori care să corespundă criteriilor.");
                } else {
                    resultArea.setText(users.stream().map(User::toString).collect(Collectors.joining("\n")));
                }
            } catch (SQLException e) {
                resultArea.setText("Eroare la interogarea bazei de date: " + e.getMessage());
            }
        });

        Button inapoi=new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e->{
            primaryStage.close();
            if(rolID==0) {
                new SuperAdminPage(primaryStage, sql, nume, prenume, rolID, userID);
            }
            else {
                new AdminPage(primaryStage, sql, nume, prenume, rolID, userID);
            }
        });

        layout.getChildren().addAll(userSearchLabel, userSearchField, userPrenumeField, searchUsersButton, resultArea,inapoi);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Căutare Utilizatori");
        primaryStage.show();
    }

    private List<User> cautaUtilizatori(String nume, String prenume) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT user_id, CNP, nume, prenume, NumarTelefon, Adresa, IBAN, email, Nr_contract, Rol_id, parola " +
                "FROM utilizatori WHERE nume = ? AND prenume = ?";

        try (PreparedStatement statement = SqlConexiune.con.prepareStatement(query)) {
            statement.setString(1, nume); // Se compară exact numele
            statement.setString(2, prenume); // Se compară exact prenumele

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("Rol_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("CNP"),
                            resultSet.getString("NumarTelefon"),
                            resultSet.getString("Adresa"),
                            resultSet.getString("IBAN"),
                            resultSet.getString("email"),
                            resultSet.getInt("Nr_contract"),
                            resultSet.getString("parola")
                    );
                    users.add(user);
                }
            }
        }
        return users;
    }


}
