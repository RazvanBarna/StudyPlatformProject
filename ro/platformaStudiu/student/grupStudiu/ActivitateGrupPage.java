package ro.platformaStudiu.student.grupStudiu;

import ro.platformaStudiu.serviceClass.Exceptii.InvalidDurationException;
import ro.platformaStudiu.serviceClass.Exceptii.InvalidParticipantNumberException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ro.platformaStudiu.serviceClass.Exceptii.*;
import ro.platformaStudiu.serviceClass.SqlConexiune;

import java.sql.*;

public class ActivitateGrupPage {
    private SqlConexiune conex;

    public ActivitateGrupPage(Stage primaryStage, SqlConexiune sql, String nume, String prenume, int userID, int studentid, GrupStudiu grup) {
        this.conex = sql;
        int grupID = grup.getIdGrup();

        primaryStage.setTitle("Adaugă Activitate Grup");
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        // Adăugarea câmpurilor de input
        Label nrMinParticipantiLabel = new Label("Nr. Min. Participanți:");
        nrMinParticipantiLabel.setFont(new Font("Times New Roman", 14));
        nrMinParticipantiLabel.setTextFill(Color.BLUE);
        GridPane.setConstraints(nrMinParticipantiLabel, 0, 1);
        TextField nrMinParticipantiField = new TextField();
        GridPane.setConstraints(nrMinParticipantiField, 1, 1);

        Label ziuaCalendaristicaLabel = new Label("Ziua Calendaristică (YYYY-MM-DD):");
        ziuaCalendaristicaLabel.setFont(new Font("Times New Roman", 14));
        ziuaCalendaristicaLabel.setTextFill(Color.BLUE);
        GridPane.setConstraints(ziuaCalendaristicaLabel, 0, 2);
        TextField ziuaCalendaristicaField = new TextField();
        GridPane.setConstraints(ziuaCalendaristicaField, 1, 2);

        Label oraLabel = new Label("Ora (HH:MM:SS):");
        oraLabel.setFont(new Font("Times New Roman", 14));
        oraLabel.setTextFill(Color.BLUE);
        GridPane.setConstraints(oraLabel, 0, 3);
        TextField oraField = new TextField();
        GridPane.setConstraints(oraField, 1, 3);

        Label descriereLabel = new Label("Descriere:");
        descriereLabel.setTextFill(Color.BLUE);
        descriereLabel.setFont(new Font("Times New Roman", 14));
        GridPane.setConstraints(descriereLabel, 0, 4);
        TextField descriereField = new TextField();
        GridPane.setConstraints(descriereField, 1, 4);

        Label durataLabel = new Label("Durata (ore):");
        durataLabel.setTextFill(Color.BLUE);
        durataLabel.setFont(new Font("Times New Roman", 14));
        GridPane.setConstraints(durataLabel, 0, 5);
        TextField durataField = new TextField();
        GridPane.setConstraints(durataField, 1, 5);

        Label timpExpirareLabel = new Label("Timp Expirare (minute):");
        timpExpirareLabel.setTextFill(Color.BLUE);
        timpExpirareLabel.setFont(new Font("Times New Roman", 14));
        GridPane.setConstraints(timpExpirareLabel, 0, 6);
        TextField timpExpirareField = new TextField();
        GridPane.setConstraints(timpExpirareField, 1, 6);

        Button okButton = new Button("Planificare");
        okButton.setFont(new Font("Times New Roman", 14));
        GridPane.setConstraints(okButton, 1, 7);
        Button inapoi = new Button("Inapoi");
        inapoi.setFont(new Font("Times New Roman", 14));
        inapoi.setOnAction(e -> {
            primaryStage.close();
            new ManagementGrupuri(primaryStage, sql, nume, prenume, userID, studentid);
        });
        GridPane.setConstraints(okButton, 1, 9);

        Label statusLabel = new Label();
        statusLabel.setFont(new Font("Times New Roman", 14));
        statusLabel.setTextFill(Color.BLUE);
        GridPane.setConstraints(statusLabel, 1, 8);

        okButton.setOnAction(e -> {
            try {
                String oraText = oraField.getText();
                if (!oraText.matches("^([01]?\\d|2[0-3]):[0-5]\\d(:[0-5]\\d)?$")) {
                    throw new InvalidDateTimeException("Ora trebuie să fie în formatul HH:mm:ss.");
                }
                String oraCuSecunde = oraText + ":00";
                Time ora = Time.valueOf(oraCuSecunde);

                adaugaActivitate(
                        grupID,
                        Integer.parseInt(nrMinParticipantiField.getText().trim()),  //. trim elimina spatii
                        Date.valueOf(ziuaCalendaristicaField.getText().trim()),
                        ora,
                        descriereField.getText().trim(),
                        Integer.parseInt(durataField.getText().trim()),
                        Integer.parseInt(timpExpirareField.getText().trim())
                );

                int activitateID = getActivitateID(grupID, Date.valueOf(ziuaCalendaristicaField.getText()), ora);
                creazaEveniment(
                        Integer.parseInt(timpExpirareField.getText()),
                        activitateID
                );

                statusLabel.setText("Activitatea a fost adăugată cu succes!");
                statusLabel.setTextFill(Color.GREEN);
            } catch (InvalidParticipantNumberException ex) {
                statusLabel.setText("Eroare: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            } catch (InvalidDurationException ex) {
                statusLabel.setText("Eroare: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            } catch (InvalidExpirationTimeException ex) {
                statusLabel.setText("Eroare: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            } catch (InvalidDescriptionException ex) {
                statusLabel.setText("Eroare: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            } catch (InvalidDateTimeException ex) {
                statusLabel.setText("Eroare: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            } catch (SQLException ex) {
                statusLabel.setText("Eroare : " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Eroare : " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            }
        });

        grid.getChildren().addAll(
                nrMinParticipantiLabel, nrMinParticipantiField,
                ziuaCalendaristicaLabel, ziuaCalendaristicaField,
                oraLabel, oraField,
                descriereLabel, descriereField,
                durataLabel, durataField,
                timpExpirareLabel, timpExpirareField,
                okButton, inapoi, statusLabel
        );

        Scene scene = new Scene(grid, 1000, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void adaugaActivitate(int grupID, int nrMinParticipanti, Date ziuaCalendaristica, Time ora,
                                  String descriere, int durata, int timpExpirare) throws SQLException {
        String query = "{CALL AdaugaActivitateGrup(?,?,?,?,?,?,?)}";
        if (nrMinParticipanti <= 0) {
            throw new InvalidParticipantNumberException("Numărul minim de participanți trebuie să fie pozitiv.");
        }
        if (durata <= 0) {
            throw new InvalidDurationException("Durata trebuie să fie un număr pozitiv.");
        }
        if (timpExpirare <= 0) {
            throw new InvalidExpirationTimeException("Timpul de expirare trebuie să fie un număr pozitiv.");
        }
        if (descriere == null || descriere.isEmpty()) {
            throw new InvalidDescriptionException("Descrierea nu poate fi goală.");
        }
        if (ziuaCalendaristica == null || ora == null) {
            throw new InvalidDateTimeException("Data și ora nu pot fi nule.");
        }
        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, grupID);
            stmt.setInt(2, nrMinParticipanti);
            stmt.setDate(3, ziuaCalendaristica);
            stmt.setTime(4, ora);
            stmt.setString(5, descriere);
            stmt.setInt(6, durata);
            stmt.setInt(7, timpExpirare);
            stmt.execute();
        }
    }

    private int getActivitateID(int grupID, Date ziuaCalendaristica, Time ora) throws SQLException {
        String query = "SELECT id_activitate_grup FROM grup_activitati WHERE ID_grup = ? AND ziua_calendaristica = ? AND Ora = ?";
        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, grupID);
            stmt.setDate(2, ziuaCalendaristica);
            stmt.setTime(3, ora);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_activitate_grup");
                }
            }
        }
        return -1;
    }

    public void creazaEveniment(int timpExpirare, int activitateID) {
        try {
            String eventName = "VerificaActivitate_" + activitateID;

            String sql = "CREATE EVENT IF NOT EXISTS " + eventName +
                    " ON SCHEDULE AT TIMESTAMPADD(MINUTE, ?, CURRENT_TIMESTAMP) " +
                    " DO BEGIN " +
                    "   IF (SELECT nr_curent_participanti FROM grup_activitati WHERE id_activitate_grup = ?) < " +
                    "   (SELECT nr_min_participanti FROM grup_activitati WHERE id_activitate_grup = ?) THEN " +
                    "     UPDATE grup_activitati SET status_activitate = 0 WHERE id_activitate_grup = ?; " +
                    "   ELSE " +
                    "     UPDATE grup_activitati SET status_activitate = 2 WHERE id_activitate_grup = ?; " +
                    "   END IF; " + "END;";



            try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(sql)) {
                stmt.setInt(1, timpExpirare);
                stmt.setInt(2, activitateID);
                stmt.setInt(3, activitateID);
                stmt.setInt(4, activitateID);
                stmt.setInt(5, activitateID);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
