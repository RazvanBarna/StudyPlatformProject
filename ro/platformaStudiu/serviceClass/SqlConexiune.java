package ro.platformaStudiu.serviceClass;

import java.sql.*;

public class SqlConexiune {

    private static final String url = "jdbc:mysql://localhost/proiect";
    private	static final String user= "root";
    private	static final String password = "root";
    public static Connection con;

    public void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.con=DriverManager.getConnection(url,user,password);
            System.out.println("Conexiunea la baza de date a fost realizată cu succes!");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Eroare la conectare: " + e.getMessage());
        }
    }
/*
    public User validateUser(String email, String password) {
        try {
            String sql = "SELECT rol_id FROM utilizatori WHERE email = ? AND parola = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("rol_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Dacă nu există utilizator sau autentificarea nu reușește
    }


 */

    public User validateUser(String email, String password) {
        try {
            String sql = "SELECT CNP,NumarTelefon,Adresa,IBAN,email,Nr_contract,user_id,rol_id, nume,prenume FROM utilizatori WHERE email = ? AND parola = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("rol_id"),rs.getInt("user_id"),rs.getString("nume"),rs.getString("prenume"),
                                rs.getString("CNP"),rs.getString("NumarTelefon"),rs.getString("Adresa"),
                                rs.getString("IBAN"),rs.getString("email"),rs.getInt("Nr_contract"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


        public void closeConnection() {
            try {
                if (con != null && !con.isClosed()) {
                    con.close(); // Închide conexiunea la baza de date
                }
            } catch (SQLException e) {
                System.err.println("Eroare la închiderea conexiunii: " + e);
            }
        }



}
