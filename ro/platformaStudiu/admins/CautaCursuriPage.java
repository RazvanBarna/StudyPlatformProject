package ro.platformaStudiu.admins;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import ro.platformaStudiu.admins.Super.SuperAdminPage;
import ro.platformaStudiu.serviceClass.Materie;
import ro.platformaStudiu.serviceClass.Service;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.serviceClass.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CautaCursuriPage {
    private SqlConexiune conex;

    public CautaCursuriPage(Stage primaryStage,SqlConexiune sql, String nume,String prenume,int rolID, int userID){
        this.conex=sql;
        Label statusLabel= new Label();
        statusLabel.setFont(new Font(14));
        statusLabel.setTextFill(Color.BLUE);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

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
        List<Materie> lista= Service.getMaterii();
        ComboBox<Materie> comboBoxMaterii = new ComboBox<>();
        comboBoxMaterii.getItems().addAll(lista);

        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        //spatierea sa fie ok
        resultArea.setFont(new Font("Times New Roman", 14));
        //resultArea.setStyle("-fx-font-size: 14px; -fx-font-family: Times New Roman;");
        //resultArea.setStyle(resultArea.getStyle() + "-fx-line-spacing: 10px;");

        Button cautaProfi=new Button("Afiseaza Profesorii Cursului: ");
        cautaProfi.setFont(new Font("Times New Roman", 14));
        cautaProfi.setOnAction(e->{
            Materie materie=comboBoxMaterii.getValue();
            try {
                List<User> profi=cautaProfi(materie.getIdMaterie());
                if(profi.isEmpty()){
                    resultArea.setText("Nu au fost găsiți utilizatori care să corespundă criteriilor.");
                } else {
                resultArea.setText(profi.stream().map(User::toString).collect(Collectors.joining("\n")));
            }
            } catch (SQLException ex) {
                resultArea.setText("Eroare la interogarea bazei de date: " + ex.getMessage());
            }
        });

        Button cautaStudenti=new Button("Afiseza Studentii de la Curs");
        cautaStudenti.setFont(new Font("Times New Roman", 14));
        cautaStudenti.setOnAction(e->{
            resultArea.clear();
            Materie materie=comboBoxMaterii.getValue();
            try {
                List<User> studenti =cautaStudenti(materie.getIdMaterie());
                if(studenti.isEmpty()){
                    resultArea.setText("Nu au fost găsiți utilizatori care să corespundă criteriilor.");
                } else {
                    resultArea.setText(studenti.stream().map(User::toString).collect(Collectors.joining("\n")));
                }
            } catch (SQLException ex) {
                resultArea.setText("Eroare la interogarea bazei de date: " + ex.getMessage());
            }

        });



        layout.getChildren().addAll(comboBoxMaterii, statusLabel, cautaProfi, resultArea,cautaStudenti,inapoi);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Căutare Utilizatori");
        primaryStage.show();
    }

    private List<User> cautaProfi(int materie_id) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "{CALL VeziProfesoriMaterie(?)}";

        try (PreparedStatement statement = SqlConexiune.con.prepareStatement(query)) {
            statement.setInt(1,materie_id );

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("email")
                    );
                    users.add(user);
                }
            }
        }
        return users;
    }

    private List<User> cautaStudenti(int materie_id) throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "{CALL VeziStudentiMaterie(?)}";

        try (PreparedStatement statement = SqlConexiune.con.prepareStatement(query)) {
            statement.setInt(1,materie_id );

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User(
                            resultSet.getString("nume"),
                            resultSet.getString("prenume"),
                            resultSet.getString("email")
                    );
                    users.add(user);
                }
            }
        }
        return users;
    }

}
