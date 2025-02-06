package ro.platformaStudiu.professor;

import ro.platformaStudiu.serviceClass.Exceptii.InvalidDayException;
import ro.platformaStudiu.serviceClass.Exceptii.InvalidDescriptionException;
import ro.platformaStudiu.serviceClass.Exceptii.InvalidParticipantNumberException;
import ro.platformaStudiu.serviceClass.Exceptii.InvalidPercentageException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.Service;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class ProgramareActivitatiProfesorPage {
    private SqlConexiune conex;

    public ProgramareActivitatiProfesorPage(Stage primaryStage, SqlConexiune sql, String nume, String prenume, int user_id, int profesorID) {
        this.conex = sql;
        primaryStage.setTitle("Adaugă Activitate");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        // Nr Participanți
        Label nrParticipantiLabel = new Label("Nr Participanți:");
        nrParticipantiLabel.setFont(new Font("Times New Roman", 14));
        nrParticipantiLabel.setTextFill(Color.BLUE);
        TextField nrParticipantiField = new TextField();
        grid.add(nrParticipantiLabel, 0, 0);
        grid.add(nrParticipantiField, 1, 0);

        // Descriere
        Label descriereLabel = new Label("Descriere:");
        descriereLabel.setFont(new Font("Times New Roman", 14));
        descriereLabel.setTextFill(Color.BLUE);
        TextField descriereField = new TextField();
        grid.add(descriereLabel, 0, 1);
        grid.add(descriereField, 1, 1);

        // Ziua
        Label ziuaLabel = new Label("Ziua:");
        ziuaLabel.setFont(new Font("Times New Roman", 14));
        ziuaLabel.setTextFill(Color.BLUE);
        TextField ziuaField = new TextField();
        grid.add(ziuaLabel, 0, 2);
        grid.add(ziuaField, 1, 2);

        // Ora
        Label oraLabel = new Label("Ora:");
        oraLabel.setFont(new Font("Times New Roman", 14));
        oraLabel.setTextFill(Color.BLUE);
        TextField oraField = new TextField();
        grid.add(oraLabel, 0, 3);
        grid.add(oraField, 1, 3);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);

        Button saveButton = new Button("Adaugă Activitate");
        saveButton.setFont(new Font("Times New Roman", 14));
        saveButton.setOnAction(e -> {
            try {
                int nrParticipanti = Integer.parseInt(nrParticipantiField.getText());
                String descriere = descriereField.getText();
                String ziua = ziuaField.getText();

                String oraText = oraField.getText().trim();
                Time oraSQL;


                Service.valideazaNrParticipanti(nrParticipanti);
                Service.valideazaDescriere(descriere);
                Service.valideazaZiua(ziua);
                oraSQL = Service.valideazaOra(oraText);


                adaugaActivitateInDB(statusLabel, profesorID, nrParticipanti, descriere, ziua, oraSQL);
                statusLabel.setText("Succes! Activitatea a fost adăugată cu succes!");
                statusLabel.setTextFill(Color.GREEN);

            } catch (InvalidParticipantNumberException | InvalidDescriptionException | InvalidPercentageException | InvalidDayException ex) {
                statusLabel.setText(ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            }catch (NumberFormatException ex) {

                    statusLabel.setText("Eroare! Toate câmpurile trebuie completate corect!");
                    statusLabel.setTextFill(Color.RED);
                   // ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                statusLabel.setText("Eroare la ora: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
               // ex.printStackTrace();
            } catch (SQLException ex) {
                statusLabel.setText("Eroare la salvarea activității: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
                //ex.printStackTrace();
            }
        });


        grid.add(statusLabel, 0, 6);
        grid.add(saveButton, 0, 7, 2, 1);

        Button inapoi = new Button("Înapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new ProfessorPage(primaryStage, conex, nume, prenume, 2, user_id);
        });
        grid.add(inapoi, 0, 8);

        Scene scene = new Scene(grid, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Boolean adaugaActivitateInDB(Label statusLabel, int profesorID, int var_nr_max_participanti, String descriere, String ziua, Time ora) throws SQLException {
        String query = "call ProfesorProgramareActivitate(?,?,?,?,?)";

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, profesorID);
            stmt.setString(2, descriere);
            stmt.setString(3, ziua);
            stmt.setTime(4, ora);
            stmt.setInt(5, var_nr_max_participanti);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
}
