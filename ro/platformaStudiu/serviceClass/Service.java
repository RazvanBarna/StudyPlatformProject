package ro.platformaStudiu.serviceClass;

import ro.platformaStudiu.serviceClass.Exceptii.InvalidDayException;
import ro.platformaStudiu.serviceClass.Exceptii.InvalidDescriptionException;
import ro.platformaStudiu.serviceClass.Exceptii.InvalidParticipantNumberException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Service {

    public static List<Materie> getMaterii() {
        List<Materie> materii = new ArrayList<>();
        String query = "SELECT id_materii, nume_materii FROM materii";

        try (
                PreparedStatement stmt = SqlConexiune.con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                materii.add(new Materie(rs.getInt("id_materii"), rs.getString("nume_materii")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materii;
    }

    public  static List<Materie> getMateriiStudent(int studentId) {
        List<Materie> materii = new ArrayList<>();
        String query = "SELECT m.id_materii, m.nume_materii " +
                "FROM catalog c " +
                "INNER JOIN materii m ON c.id_materie_catalog = m.id_materii " +
                "WHERE c.id_student_catalog = ?";

        try (PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idMaterie = rs.getInt("id_materii");
                String numeMaterie = rs.getString("nume_materii");
                materii.add(new Materie(idMaterie, numeMaterie));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materii;
    }


    public static void valideazaNrParticipanti(int nrParticipanti) throws InvalidParticipantNumberException {
        if (nrParticipanti <= 0) {
            throw new InvalidParticipantNumberException("Numărul de participanți trebuie să fie pozitiv.");
        }
    }

    public static void valideazaZiua(String ziua) throws InvalidDayException {
        String[] zileValide = {"luni", "marti", "miercuri", "joi", "vineri"};
        boolean esteValida = false;
        for (String zi : zileValide) {
            if (zi.equalsIgnoreCase(ziua)) {
                esteValida = true;
                break;
            }
        }
        if (!esteValida) {
            throw new InvalidDayException("Ziua trebuie să fie una dintre: luni, marti, miercuri, joi, vineri.");
        }
    }

    public static Time valideazaOra(String oraText) throws IllegalArgumentException {
        if (!oraText.matches("^([01]?\\d|2[0-3]):[0-5]\\d(:[0-5]\\d)?$")) {
            throw new IllegalArgumentException("Formatul orei trebuie să fie HH:mm sau HH:mm:ss.");
        }
        java.time.LocalTime ora = java.time.LocalTime.parse(oraText);
        return java.sql.Time.valueOf(ora);
    }

    public  static int getProfIdMaterie(int student_id,int materie_id){
        String query = "{CALL SelecteazaProfesor(?,?)}";
        int id_prof=0;

        try (
                PreparedStatement stmt = SqlConexiune.con.prepareStatement(query)
        ) {
            stmt.setInt(1, materie_id);
            stmt.setInt(2,student_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id_prof = rs.getInt("ProfesorID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id_prof;
    }

    public static  List<Roluri> getRoluriInscris() {
        List<Roluri> roluri = new ArrayList<>();
        String query = "SELECT DISTINCT Rol_id, Rol_nume FROM rol";

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

    public static void valideazaDescriere(String descriere) throws InvalidDescriptionException {
        if (!(descriere.equalsIgnoreCase("curs") ||
                descriere.equalsIgnoreCase("seminar") ||
                descriere.equalsIgnoreCase("laborator"))) {
            throw new InvalidDescriptionException("Descrierea poate fi doar 'curs', 'seminar' sau 'laborator'.");
        }
    }

}
