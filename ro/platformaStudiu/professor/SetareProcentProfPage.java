package ro.platformaStudiu.professor;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;
import java.util.stream.IntStream;

public class SetareProcentProfPage {

    private SqlConexiune conex;

    public SetareProcentProfPage(Stage primaryStage,SqlConexiune sql,String nume,String prenume,int user_id,int profesorID){
        this.conex=sql;

        Label titleLabel = new Label("Setati procentele pentrua activitatile dumneavoastra:");
        titleLabel.setFont(new Font("Times New Roman", 18));

        ComboBox<Integer> comboCurs = new ComboBox<>();
        ComboBox<Integer> comboSeminar = new ComboBox<>();
        ComboBox<Integer> comboLaborator = new ComboBox<>();

        comboCurs.getItems().addAll(IntStream.rangeClosed(0, 20).map(i -> i * 5).boxed().toList());
        comboSeminar.getItems().addAll(comboCurs.getItems());
        comboLaborator.getItems().addAll(comboCurs.getItems());

        comboCurs.setValue(0);
        comboSeminar.setValue(0);
        comboLaborator.setValue(0);

        Button inapoi = new Button("Înapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new ProfessorPage(primaryStage, conex, nume, prenume, 2,user_id);
        });

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        HBox hboxCurs = new HBox(12, new Label("Curs:"), comboCurs);
        HBox hboxSeminar = new HBox(12, new Label("Seminar:"), comboSeminar);
        HBox hboxLaborator = new HBox(12, new Label("Laborator:"), comboLaborator);

        Button set = new Button("Trimite Procentele");
        set.setFont(new Font("Times New Roman", 14));
        set.setOnAction(e -> {
                    int curs = comboCurs.getValue();
                    int seminar = comboSeminar.getValue();
                    int laborator = comboLaborator.getValue();
                    int total = curs + seminar + laborator;

                    if (total != 100) {
                        statusLabel.setText("Eroare: Procentele nu dau 100%! Total: " + total + "%");
                        statusLabel.setTextFill(Color.RED);
                        return;
                    }

                    String query = "{CALL SeteazaProcenteActivitatiProfesor(?, ?, ?, ?)}";

                    try (CallableStatement stmt = SqlConexiune.con.prepareCall(query)) {
                        stmt.setInt(1, profesorID);
                        stmt.setInt(2, curs);
                        stmt.setInt(3, seminar);
                        stmt.setInt(4, laborator);

                        stmt.executeUpdate();

                        // Succes
                        statusLabel.setText("Procentele au fost salvate cu succes.");
                        statusLabel.setTextFill(Color.GREEN);
                    } catch (SQLException ex) {
                        statusLabel.setText("Eroare la salvarea datelor: " + ex.getMessage());
                        statusLabel.setTextFill(Color.RED);
                        ex.printStackTrace();
                    }
                }
                );

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleLabel,hboxCurs,comboCurs,hboxSeminar,comboSeminar,hboxLaborator,comboLaborator, statusLabel,set, inapoi);

        // Creează scena
        Scene scene = new Scene(layout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Impartire Procentuala");
        primaryStage.show();

    }

}
