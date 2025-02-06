package ro.platformaStudiu.student.grupStudiu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagementGrupuri {
    private SqlConexiune conex;

    public ManagementGrupuri(Stage primaryStage, SqlConexiune sql, String nume, String prenume, int userID, int studentID){
        this.conex=sql;

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        ComboBox<GrupStudiu> grupuriComboBox = new ComboBox<>();
        grupuriComboBox.setPromptText("Selectează un grup");
        List<GrupStudiu> grupuri=loadGrupuri(studentID);
        grupuriComboBox.getItems().addAll(grupuri);

        grupuriComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                statusLabel.setText("Ai selectat grupul la materia: " + newValue.getMaterie());
                statusLabel.setTextFill(Color.BLUE);
            }
        });
        Button inapoi=new Button("inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e->{
            primaryStage.close();
            new GrupStudiuPage(primaryStage,sql,nume,prenume,userID,studentID);
        });

        Button veziStudenti = new Button("Vezi Studenti!");
        veziStudenti.setFont(new Font("Times New Roman", 14));
        veziStudenti.setOnAction(e -> {
            GrupStudiu grup = getGrupNullEx(statusLabel, grupuriComboBox);
            if (grup != null) {
                primaryStage.close();
                new TableStudentiPage(primaryStage, sql, nume, prenume, userID, studentID, grup);
            }
        });

        Button mesaj = new Button("Trimite un mesaj pe grup!");
        mesaj.setFont(new Font("Times New Roman", 14));
        mesaj.setOnAction(e -> {
            GrupStudiu grup = getGrupNullEx(statusLabel, grupuriComboBox);
            if (grup != null) {
                primaryStage.close();
                new TrimiteMesajPage(primaryStage, sql, nume, prenume, userID, studentID, grup);
            }
        });
        Button chat=new Button("Chat grup ");
        chat.setFont(new Font("Times New Roman", 14));
        chat.setOnAction(e->{
            GrupStudiu grup = getGrupNullEx(statusLabel, grupuriComboBox);
            if (grup != null) {
                primaryStage.close();
                new ChatPage(primaryStage, sql, nume, prenume, userID, studentID, grup);
            }
        });
        Button adaugaActivitate=new Button("Adauga o activitate la grup ");
        adaugaActivitate.setFont(new Font("Times New Roman", 14));
        adaugaActivitate.setOnAction(e->{
            GrupStudiu grup = getGrupNullEx(statusLabel, grupuriComboBox);
            if (grup != null) {
                primaryStage.close();
                new ActivitateGrupPage(primaryStage, sql, nume, prenume, userID, studentID, grup);
            }
        });
        Button inscriereActivitate=new Button("Inscriere la o activitate a grupului");
        inscriereActivitate.setFont(new Font("Times New Roman", 14));
        inscriereActivitate.setOnAction(e->{
            GrupStudiu grup = getGrupNullEx(statusLabel, grupuriComboBox);
            if (grup != null) {
                primaryStage.close();
                new InscriereActivitateGrupPage(primaryStage,sql,nume,prenume,userID,studentID,grup);
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(statusLabel,grupuriComboBox,veziStudenti,mesaj,chat,adaugaActivitate,inscriereActivitate,inapoi);

        Scene scene = new Scene(layout, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Grupuri de Studiu");
        primaryStage.show();

    }

    private GrupStudiu getGrupNullEx(Label statusLabel, ComboBox<GrupStudiu> combo) {
        GrupStudiu grup = combo.getValue();
        if (grup == null) {
            statusLabel.setText("Alege un grup!");
            statusLabel.setTextFill(Color.RED);
        }
        return grup;
    }

    private List<GrupStudiu> loadGrupuri(int studentID){
        String query = "{CALL AfisareGrupuriDeStudiu(?)}";
        List<GrupStudiu> grupuri = new ArrayList<>();

        try(PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)){
            stmt.setInt(1,studentID);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                int idGrup = rs.getInt("ID_grup_studiu");
                String materie = rs.getString("Materie");
                grupuri.add(new GrupStudiu(idGrup, materie));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return grupuri;
    }
}
