package ro.platformaStudiu.admins.Super;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.admins.*;
import ro.platformaStudiu.serviceClass.LoginPage;
import ro.platformaStudiu.serviceClass.SqlConexiune;
import ro.platformaStudiu.serviceClass.VizualizareDate;

public class SuperAdminPage {
    private SqlConexiune conex;

    public SuperAdminPage(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int rol_id,int user_id) {
        this.conex = sql;

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10); //spatiu intre coloane

        Label welcomeLabel = new Label("Bine ai venit "+ prenume+" " + nume+" !");
        welcomeLabel.setFont(new Font("Times New Roman", 20));
        welcomeLabel.setTextFill(Color.BLUE);

        Button vizualizareDateButon = new Button("Vizualizare Date Utilizatori");
        Button modificaUtilizator = new Button("Modifică informații despre utilizatori");
        Button cautaNumeButon = new Button("Caută utilizator după nume");
        Button cautaTipButon = new Button("Caută utilizatori după tip");
        Button adaugaUtilizatorButon = new Button("Adaugă utilizator nou");
        Button cautaCursuriButon = new Button("Caută cursuri");
        Button deautentificareButton = new Button("Deautentificare");
        Label messageLabel = new Label();

        messageLabel.setFont(new Font("Times New Roman", 14));
        vizualizareDateButon.setFont(new Font("Times New Roman", 14));
        modificaUtilizator.setFont(new Font("Times New Roman", 14));
        cautaCursuriButon.setFont(new Font("Times New Roman", 14));
        cautaNumeButon.setFont(new Font("Times New Roman", 14));
        cautaTipButon.setFont(new Font("Times New Roman", 14));
        adaugaUtilizatorButon.setFont(new Font("Times New Roman", 14));
        deautentificareButton.setFont(new Font("Times New Roman", 14));

        // Adăugarea la interfață
        grid.add(welcomeLabel, 0, 0, 2, 1);

        grid.add(vizualizareDateButon, 0, 1);
        grid.add(modificaUtilizator, 1, 1);

        grid.add(cautaTipButon, 0, 3);
        grid.add(adaugaUtilizatorButon, 1, 3);

        grid.add(cautaCursuriButon, 1, 4);
        grid.add(cautaNumeButon,0,4);

        grid.add(deautentificareButton, 1, 5);

        grid.add(messageLabel, 0, 5);


        // Configurare evenimente
        deautentificareButton.setOnAction(e -> {
            primaryStage.close();
            new LoginPage(primaryStage, conex);
        });

        vizualizareDateButon.setOnAction(e ->{
            primaryStage.close();
            new VizualizareDate(primaryStage,conex,nume,prenume,rol_id,user_id);
        });
        cautaNumeButon.setOnAction(e->{
            primaryStage.close();
            new CautaUtilizatorNumePage(primaryStage,sql,nume,prenume,rol_id,user_id);
        });

        cautaTipButon.setOnAction(e->{
            primaryStage.close();
            new CautaUtilizatorTipPage(primaryStage,sql,nume,prenume,rol_id,user_id);
        });
        cautaCursuriButon.setOnAction(e->{
            primaryStage.close();
            new CautaCursuriPage(primaryStage,sql,nume,prenume,rol_id,user_id);
        });

        modificaUtilizator.setOnAction(e->{
            primaryStage.close();
            new ModificaUtilizatorPage(primaryStage,sql,nume,prenume,rol_id,user_id);
        });
        adaugaUtilizatorButon.setOnAction(e->{
            primaryStage.close();
            new AdaugaUtilizatorPage(primaryStage,sql,nume,prenume,rol_id,user_id);
        });

        Scene scene = new Scene(grid, 800, 500);
        primaryStage.setTitle("Admin Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
